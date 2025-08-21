package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Media;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends JpaRepository<Media, Long> {

    @Query("SELECT m FROM Media m ORDER BY m.averageRating DESC LIMIT :amount")
    public List<Media> getBestRatedMovies(@Param("amount") int amount);

}
