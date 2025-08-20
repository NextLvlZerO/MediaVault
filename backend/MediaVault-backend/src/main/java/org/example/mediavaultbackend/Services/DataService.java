package org.example.mediavaultbackend.Services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.Repositories.MediaRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.Year;
import java.util.Arrays;
import java.util.Optional;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
public class DataService {

    private final MediaRepository mediaRepository;

    public void importTitleBasics() {

        String fileUrl = "https://datasets.imdbws.com/title.basics.tsv.gz";

        try (GZIPInputStream gzip = new GZIPInputStream(
                URI.create(fileUrl).toURL().openStream());
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(gzip, StandardCharsets.UTF_8))) {

            Iterable<CSVRecord> records = CSVFormat.TDF
                    .builder()
                    .setHeader()              // nimmt die erste Zeile als Header
                    .setSkipHeaderRecord(true) // Headerzeile wird nicht als Datenrecord gelesen
                    .setQuote(null)            // kein Quotesymbol
                    .get()
                    .parse(reader);

            int count = 0;
            for (CSVRecord record : records) {



                String type = record.get("titleType");
                String title = record.get("primaryTitle");
                boolean isAdult = "1".equals(record.get("isAdult"));

                Year year = Optional.ofNullable(record.get("startYear"))
                        .filter(s -> !s.isBlank())
                        .filter(s -> !s.equals("\\N"))
                        .map(Integer::parseInt)
                        .map(Year::of)
                        .orElse(null);

                Integer runtime = Optional.ofNullable(record.get("runtimeMinutes"))
                        .filter(s -> !s.isBlank())
                        .filter(s -> !s.equals("\\N"))
                        .map(Integer::parseInt)
                        .orElse(null);

                String[] genres = record.get("genres").split(",");

            mediaRepository.save(Media.builder()
                    .type(type)
                    .title(title)
                    .isAdult(isAdult)
                    .startYear(year)
                    .runtimeMinutes(runtime)
                    .genres(Arrays.stream(genres).toList())
                    .build());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
