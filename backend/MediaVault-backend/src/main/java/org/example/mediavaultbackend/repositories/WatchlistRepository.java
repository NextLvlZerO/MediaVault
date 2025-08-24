package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Watchlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {

    public Optional<Watchlist> findByAccount_AccountId(Long accountId);

    public Optional<Watchlist> findByAccount_AccountIdAndMedia_MediaId(Long accountId, Long mediaId);

}
