package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    public Optional<Genre> findByTmdbId(Long tmdbId);


}
