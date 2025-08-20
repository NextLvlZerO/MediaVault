package org.example.mediavaultbackend.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.Repositories.MediaRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.Year;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
public class DataService {

    private final MediaRepository mediaRepository;

    private final String API_KEY = "a80a453e831efa922a44cb8a54281aee";
    private final String BASE_URL = "https://api.themoviedb.org/3";

    public void importMovies() {

        try (HttpClient client = HttpClient.newHttpClient();) {

            HttpRequest genreRequest = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/genre/movie/list?api_key=" + API_KEY + "&language=de-DE"))
                    .GET()
                    .build();

            HttpResponse<String> genreResponse = client.send(genreRequest, HttpResponse.BodyHandlers.ofString());

            Map<Integer, String> genreMap = new HashMap<>();

            if (genreResponse.statusCode() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(genreResponse.body());
                for (JsonNode genre : root.get("genres")) {
                    genreMap.put(genre.get("id").asInt(), genre.get("name").asText());
                }
            } else {
                System.out.println(genreResponse.statusCode());
                return;
            }

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
                    List<String> genres = Arrays.stream(genreIds).mapToObj(genreMap::get).toList();

                    String title = movie.get("title").asText();
                    String description = movie.get("overview").asText();

                    LocalDate releaseDate = Optional.ofNullable(movie.get("release_date").asText())
                            .filter(s -> !s.isBlank())
                            .map(LocalDate::parse)
                            .orElse(null);



                    String poster = Optional.ofNullable(movie.get("poster_path").asText())
                            .filter(s -> !s.isBlank())
                            .orElse(null);


                    mediaRepository.save(Media.builder()
                            .title(title)
                            .description(description)
                            .isAdult(isAdult)
                            .releaseDate(releaseDate)
                            .genres(genres)
                            .poster(poster)
                            .averageRating(2)
                            .build());



                }
            } else {
                System.out.println("Fehler: " + movieResponse.statusCode());
                System.out.println(movieResponse.body());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }




    }


}
