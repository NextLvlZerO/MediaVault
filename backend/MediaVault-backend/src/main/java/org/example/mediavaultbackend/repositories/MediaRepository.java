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

    @Query("SELECT m FROM Media m WHERE m.type = :type ORDER BY m.averageRating DESC")
    public Page<Media> getBestRatedMedia(String type, Pageable pageable);

    @Query(value = "SELECT m FROM Media m WHERE m.type = :type")
    public Page<Media> getAllMedia(String type, Pageable pageable);

    @Query("SELECT m FROM Media m WHERE LOWER(m.title) LIKE LOWER(CONCAT('%', :title, '%'))")
    public Page<Media> findByTitleSubstring(String title, Pageable pageable);

    @Query(value = """
            SELECT *
            FROM media m
            WHERE m.type = :type
            AND m.price <= :price
            AND m.average_rating >= :rating
            AND (
                SELECT COUNT(DISTINCT mg.genre_id)
                FROM media_genre mg
                JOIN genre g ON g.genre_id = mg.genre_id
                WHERE mg.media_id = m.media_id
                AND g.genre_name IN (:genres)
            ) = :genreCount
          """, nativeQuery = true)
    public Page<Media> getFilteredMedia(String type, Pageable pageable, List<String> genres, int genreCount, Double price, Integer rating);

    public Optional<Media> findByTitle(String title);


}
