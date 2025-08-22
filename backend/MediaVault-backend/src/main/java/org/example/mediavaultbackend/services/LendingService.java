package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.CurrentlyLending;
import org.example.mediavaultbackend.models.History;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.CurrentlyLendingRepository;
import org.example.mediavaultbackend.repositories.HistoryRepository;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class LendingService {

    private final AccountRepository accountRepository;
    private final MediaRepository mediaRepository;
    private final CurrentlyLendingRepository currentlyLendingRepository;
    private final HistoryRepository historyRepository;


    public Media lendMedia(Long accountId, Long mediaId, int days) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("Media not found"));

        currentlyLendingRepository.save(CurrentlyLending.builder()
                .account(account)
                .media(media)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(days))
                .build());

        historyRepository.save(History.builder()
                .account(account)
                .media(media)
                .startDate(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(days))
                .build());

        return media;
    }

    public Media expandMedia(Long accountId, Long mediaId, int days) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("Media not found"));

        CurrentlyLending currentlyLending = currentlyLendingRepository.findByMediaUser(accountId, mediaId).orElseThrow(() -> new NoSuchElementException("CurrentlyLending not found"));
        currentlyLending.setEndDate(LocalDateTime.now().plusDays(days));


        History history = historyRepository.findByMediaAccount(accountId, mediaId).orElseThrow(() -> new NoSuchElementException("History not found"));
        historyRepository.save(History.builder()
                .account(account)
                .media(media)
                .startDate(history.getEndDate())
                .endDate(history.getEndDate().plusDays(days))
                .build());

        currentlyLendingRepository.save(currentlyLending);
        historyRepository.save(history);

        return media;

    }
}
