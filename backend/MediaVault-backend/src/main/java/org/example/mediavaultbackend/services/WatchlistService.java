package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.Watchlist;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.WatchlistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WatchlistService {

    private static final Logger log = LoggerFactory.getLogger(WatchlistService.class);
    private final WatchlistRepository watchlistRepository;
    private final MediaRepository mediaRepository;
    private final AccountRepository accountRepository;


    public Map<String, Boolean> getUserMediaWatchlist(Long accountId, Long mediaId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("Media not found"));

        Optional<Watchlist> watchlistItem = watchlistRepository.findByAccount_AccountIdAndMedia_MediaId(accountId, mediaId);

        Map<String, Boolean> result = new HashMap<>();
        result.put("exists", watchlistItem.isPresent());

        return result;
    }


    public List<MediaResponseDto> getUserWatchlist(Long id) throws NoSuchElementException {

        List<Media> watchlist =  watchlistRepository.findByAccount_AccountId(id)
                .orElseThrow(() -> new NoSuchElementException("no watchlist found"))
                .getMedia();

        return watchlist.stream()
                .map(m -> MediaResponseDto.builder()
                .id(m.getMediaId())
                .type(m.getType())
                .title(m.getTitle())
                .details(m.getDescription())
                .poster(m.getPoster())
                .rating(m.getAverageRating())
                .amount(m.getAmount()).build()).collect(Collectors.toList());

    }

    public MediaResponseDto updateWatchlist(Long accountId, Long mediaId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("media not found"));
        Watchlist watchlist = watchlistRepository.findByAccount_AccountId(accountId).orElseThrow(() -> new NoSuchElementException("watchlist not found"));
        List<Media> mediaList = watchlist.getMedia();

        if (mediaList.contains(media)) {
            mediaList.remove(media);
            log.info("Account: {} removed media: {} from watchlist", accountId, mediaId);
        } else {
            mediaList.add(media);
            log.info("Account: {} added media: {} to watchlist", accountId, mediaId);
        }

        watchlist.setMedia(mediaList);

        watchlistRepository.save(watchlist);

        return MediaResponseDto.builder()
                .id(media.getMediaId())
                .type(media.getType())
                .title(media.getTitle())
                .details(media.getDescription())
                .poster(media.getPoster())
                .rating(media.getAverageRating())
                .amount(media.getAmount())
                .build();
    }

    public void createWatchlist(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("user not found"));
        Watchlist watchlist = Watchlist.builder()
                .account(account)
                .build();
        watchlistRepository.save(watchlist);
        log.info("Created watchlist for user: {}", accountId);
    }

}
