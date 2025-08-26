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
import java.util.stream.Collectors;

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
        log.info("Importied genres");
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
                .build());
        subscriptionTypeRepository.save(SubscriptionType.builder()
                .name("Premium")
                .priceReduction(0.85)
                .quantity(5)
                .price(8.0)
                .build());
        subscriptionTypeRepository.save(SubscriptionType.builder()
                .name("Deluxe")
                .priceReduction(0.75)
                .quantity(10)
                .price(15.0)
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
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }


    }


    public void importMovies() {

        try (HttpClient client = HttpClient.newHttpClient();) {



            HttpRequest movieRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/movie/popular?api_key=" + API_KEY + "&language=de-DE&page=1"))
                    .GET()
                    .build();

            HttpResponse<String> movieResponse = client.send(movieRequest, HttpResponse.BodyHandlers.ofString());

            if (movieResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(movieResponse.body());

                for (JsonNode movie : root.get("results")) {

                    Boolean isAdult = Boolean.parseBoolean(movie.get("adult").asText());

                    int[] genreIds = mapper.treeToValue(movie.get("genre_ids"), int[].class);
                    List<Genre> genres = Arrays.stream(genreIds)
                            .mapToObj(id -> genreRepository.findByTmdbId((long) id))
                            .flatMap(Optional::stream)
                            .toList();

                    String title = movie.get("title").asText();
                    String description = movie.get("overview").asText();

                    LocalDate releaseDate = Optional.ofNullable(movie.get("release_date").asText())
                            .filter(s -> !s.isBlank())
                            .map(LocalDate::parse)
                            .orElse(null);



                    String poster = Optional.ofNullable(movie.get("poster_path").asText())
                            .filter(s -> !s.isBlank())
                            .orElse(null);


                    Random r = new Random();


                    try {
                        mediaService.saveMedia("movie", title, description, isAdult, releaseDate, genres, poster, r.nextInt(50),Math.round(r.nextDouble() *100.0) /100.0 );
                    } catch (Exception e) {
                        log.error(e.getMessage());
                    }




                }
            } else {
                System.out.println("Fehler: " + movieResponse.statusCode());
                System.out.println(movieResponse.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void importSeries() {
    }


}
