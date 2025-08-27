package org.example.mediavaultbackend.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Genre;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.SubscriptionType;
import org.example.mediavaultbackend.repositories.GenreRepository;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.example.mediavaultbackend.repositories.SubscriptionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class DataService {

    private static final Logger log = LoggerFactory.getLogger(DataService.class);
    private final MediaService mediaService;
    private final GenreRepository genreRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;

    private final String API_KEY = "a80a453e831efa922a44cb8a54281aee"; //TODO: Make environmental var
    private final String BASE_URL = "https://api.themoviedb.org/3";
    private final GenreService genreService;

    public void importData() {
        log.info("Importing data...");
        importSubscriptionTypes();
        log.info("Imported SubscriptionTypes");
        importGenres();
        log.info("Imported genres");
        importMovies();
        log.info("Imported movies");
        importSeries();
        log.info("Imported series");
    }

    private void importSubscriptionTypes() {
        subscriptionTypeRepository.save(SubscriptionType.builder()
                .name("Free")
                .priceReduction(1.0)
                .quantity(1)
                .price(0.0)
                .extensions(0)
                .build());
        subscriptionTypeRepository.save(SubscriptionType.builder()
                .name("Premium")
                .priceReduction(0.85)
                .quantity(5)
                .price(8.0)
                .extensions(1)
                .build());
        subscriptionTypeRepository.save(SubscriptionType.builder()
                .name("Deluxe")
                .priceReduction(0.75)
                .quantity(10)
                .price(15.0)
                .extensions(3)
                .build());
    }


    public void importGenres() {

        try (HttpClient client = HttpClient.newHttpClient();) {

            HttpRequest genreRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/genre/movie/list?api_key=" + API_KEY + "&language=en-US"))
                    .GET()
                    .build();

            HttpResponse<String> genreResponse = client.send(genreRequest, HttpResponse.BodyHandlers.ofString());


            if (genreResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(genreResponse.body());
                for (JsonNode genre : root.get("genres")) {

                    Long tmdbId = Optional.ofNullable(genre.get("id").asText())
                            .filter(s -> !s.isBlank())
                            .map(Long::parseLong)
                            .orElseThrow(() -> new IllegalArgumentException("GenreId not found"));

                    String genreName = genre.get("name").asText().strip();
                    if (genreName.isBlank()) {
                        continue;
                    }

                    try {
                        genreService.saveGenre(Genre.builder()
                                .tmdbId(tmdbId)
                                .genreName(genreName)
                                .build());
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }

                }
            } else {
                System.out.println(genreResponse.statusCode());
                return;
            }


        } catch (Exception e) {
            log.error(e.getMessage());
        }


    }


    public void importMovies() {
        importMediums(BASE_URL + "/movie/popular?api_key=" + API_KEY + "&language=en-US&page=", "movie");
    }

    private void importSeries() {
        importMediums(BASE_URL + "/tv/popular?api_key=" + API_KEY + "&language=en-US&page=", "series");
    }


    private void importMediums(String uri, String type) {

        try (HttpClient client = HttpClient.newHttpClient();) {


            List<CompletableFuture<Void>> futures = IntStream.range(1, 10)
                    .mapToObj(page -> {
                        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(uri + page))
                                .GET()
                                .build();
                        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                                .thenAccept(response -> {

                                    if (response.statusCode() != 200) {
                                        log.error("API-Error (Page {}): {}", page, response.statusCode());
                                        return;
                                    }


                                    try {

                                        ObjectMapper mapper = new ObjectMapper();
                                        JsonNode root = mapper.readTree(response.body());
                                        JsonNode results = root.get("results");

                                        if (!results.isArray()) {
                                            log.error("No results found on page {}: {}", page, response.body());
                                            return;
                                        }

                                        for (JsonNode media : results) {
                                            try {


                                                String title = Optional.ofNullable(media.get("title"))
                                                        .or(() -> Optional.ofNullable(media.get("name")))
                                                        .map(JsonNode::asText)
                                                        .orElseThrow(() -> new IllegalArgumentException("Title not found"));


                                                Boolean isAdult = Optional.ofNullable(media.get("adult"))
                                                        .map(JsonNode::asText)
                                                        .map(Boolean::parseBoolean)
                                                        .orElse(null);

                                                int[] genreIds = mapper.treeToValue(media.get("genre_ids"), int[].class);
                                                List<Genre> genres = Arrays.stream(genreIds)
                                                        .mapToObj(id -> genreRepository.findByTmdbId((long) id))
                                                        .flatMap(Optional::stream)
                                                        .toList();

                                                String description = Optional.ofNullable(media.get("overview"))
                                                        .map(JsonNode::asText)
                                                        .orElse("");

                                                LocalDate releaseDate = Optional.ofNullable(media.get("release_date"))
                                                        .or(() -> Optional.ofNullable(media.get("first_air_date")))
                                                        .map(JsonNode::asText)
                                                        .filter(s -> !s.isBlank())
                                                        .map(LocalDate::parse)
                                                        .orElseGet(() -> {
                                                            log.warn("No release date found for Media: {}", media);
                                                            return null;
                                                        });



                                                String poster = Optional.ofNullable(media.get("poster_path").asText())
                                                        .filter(s -> !s.isBlank())
                                                        .orElse(null);


                                                Random r = new Random();

                                                try {
                                                    mediaService.saveMedia(type, title, description, isAdult, releaseDate, genres, poster, r.nextInt(50),Math.round(r.nextDouble() * 100.0) /100.0 + 0.10 );
                                                } catch (Exception e) {
                                                    log.error("Problem during creation of Medium: {} : {}", media, e.getMessage());
                                                }



                                            } catch (Exception e) {
                                                log.error("Problem during parsing of Medium: {} : {}", media, e.getMessage());
                                            }


                                        }

                                    } catch (Exception e) {
                                        log.error(e.getMessage());
                                    }


                                });
                    }).toList();

            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();


        } catch (Exception e) {
            log.error(e.getMessage());
        }

    }




}
