package org.example.mediavaultbackend.Services;

import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.example.mediavaultbackend.Models.Media;
import org.example.mediavaultbackend.Repositories.MediaRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

@Service
@RequiredArgsConstructor
public class ImdbDataService {

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
                boolean isAdult =  record.get("isAdult").equals("1");
                int year = Integer.parseInt(record.get("startYear"));
                int runtime = Integer.parseInt(record.get("runtimeMinutes"));
                String[] genres = record.get("genres").split(",");

            mediaRepository.save(Media.builder()
                    .type(type)
                    .title(title)
                    .isAdult(isAdult)
                    .year(year)
                    .runtimeMinutes(runtime)
                    .genres(Arrays.stream(genres).toList())
                    .build());

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
