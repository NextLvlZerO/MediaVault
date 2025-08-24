package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.CurrentlyLending;
import org.example.mediavaultbackend.models.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.account.accountId = :accountId AND h.media.mediaId = :mediaId")
    public Optional<History> findByMediaAccount(Long accountId, Long mediaId);

    public List<History> findByAccount(Account account);

    public List<History> findByAccount_AccountId(Long accountId);
}
