package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Media;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    @Query("SELECT m FROM Media m WHERE m.type = 'movie' ORDER BY m.averageRating DESC")
    public Page<Media> getBestRatedMovies(Pageable pageable);

    @Query(value = "SELECT m FROM Media m WHERE m.type = 'movie'")
    public Page<Media> getMovies(Pageable pageable);

    @Query("SELECT m FROM Media m where m.title LIKE %:title%")
    public Optional<Media> findByTitle(String title);

}
