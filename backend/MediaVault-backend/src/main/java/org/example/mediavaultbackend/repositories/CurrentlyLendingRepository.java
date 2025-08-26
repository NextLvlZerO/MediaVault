package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.CurrentlyLending;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrentlyLendingRepository extends JpaRepository<CurrentlyLending, Integer> {

    @Query("SELECT l FROM CurrentlyLending l WHERE l.account.accountId = :accountId AND l.media.mediaId = :mediaId")
    public Optional<CurrentlyLending> findByMediaAccount(Long accountId, Long mediaId);

    public List<CurrentlyLending> findByAccount_AccountId(Long accountId);

}
