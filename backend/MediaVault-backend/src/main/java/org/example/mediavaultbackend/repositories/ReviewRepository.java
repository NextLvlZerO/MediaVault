package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    public List<Review> findByMedia_MediaId(Long mediaId);
}
