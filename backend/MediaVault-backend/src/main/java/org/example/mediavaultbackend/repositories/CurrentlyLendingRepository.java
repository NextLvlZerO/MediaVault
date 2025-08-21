package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.CurrentlyLending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CurrentlyLendingRepository extends JpaRepository<CurrentlyLending, Integer> {

    @Query("SELECT l FROM CurrentlyLending l WHERE l.media.mediaId = :mediaId AND l.user.userId = :userId")
    public Optional<CurrentlyLending> findByMediaUser(Long mediaId, Long userId);

}
