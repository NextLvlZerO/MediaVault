package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.Watchlist;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.WatchlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private final WatchlistRepository watchlistRepository;
    private final MediaRepository mediaRepository;
    private final AccountRepository accountRepository;


    public List<Media> getUserWatchlist(Long id) throws NoSuchElementException {

        return watchlistRepository.findByAccount_AccountId(id)
                .orElseThrow(() -> new NoSuchElementException("no watchlist found"))
                .getMedia();

    }

    public Media updateWatchlist(Long accountId, Long mediaId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("media not found"));
        Watchlist watchlist = watchlistRepository.findByAccount_AccountId(accountId).orElseThrow(() -> new NoSuchElementException("watchlist not found"));
        List<Media> mediaList = watchlist.getMedia();

        if (mediaList.contains(media)) {
            mediaList.remove(media);
        } else {
            mediaList.add(media);
        }

        watchlist.setMedia(mediaList);

        watchlistRepository.save(watchlist);

        return media;
    }
}
