package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Genre;
import org.example.mediavaultbackend.repositories.GenreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GenreService {

    private static final Logger log = LoggerFactory.getLogger(GenreService.class);
    final GenreRepository genreRepository;

    public Genre saveGenre(Genre genre) {

        if (genre.getGenreName().isBlank() || genre.getTmdbId() == null) {
            throw new IllegalArgumentException("Genre name or id cannot be empty");
        } else if (genreRepository.findByTmdbId(genre.getTmdbId()).isPresent() || genreRepository.findByGenreName(genre.getGenreName()).isPresent()) {
            throw new IllegalArgumentException("Genre name or id already exists");
        }
        genreRepository.save(genre);
        log.info("Genre saved: {}", genre);
        return genre;
    }

}
