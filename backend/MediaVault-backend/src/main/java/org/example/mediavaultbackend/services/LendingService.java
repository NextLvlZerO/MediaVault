package org.example.mediavaultbackend.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.HistoryResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.dtos.PaymentSessionData;
import org.example.mediavaultbackend.models.*;
import org.example.mediavaultbackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LendingService {

    private static final Logger log = LoggerFactory.getLogger(LendingService.class);
    private final AccountRepository accountRepository;
    private final MediaRepository mediaRepository;
    private final CurrentlyLendingRepository currentlyLendingRepository;
    private final HistoryRepository historyRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final PaymentSocketClient paymentSocketClient;


    public String lendMedia(Long accountId, Long mediaId, int days, HttpServletRequest request) {



        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("Media not found"));
        Subscription subscription = subscriptionRepository.findByAccount(account).orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        if (getCurrentlyLending(accountId).size() >= subscription.getType().getQuantity()) {
            throw new IllegalArgumentException("User is not permitted to lend more media");
        }
        if (media.getAmount() <= 0) {
            throw new IllegalArgumentException("Media not available for lending");
        }

        String sessionId = paymentSocketClient.payForMedium(media.getPrice(), days, subscription.getType().getPriceReduction(), request);

        CompletableFuture.runAsync(() -> {
            PaymentSessionData paymentSessionData = paymentSocketClient.waitForStatus(sessionId);

            if ("SUCCESS".equals(paymentSessionData.getStatus())) {

                currentlyLendingRepository.save(CurrentlyLending.builder()
                        .account(account)
                        .media(media)
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(days - 1))
                        .build());

                historyRepository.save(History.builder()
                        .account(account)
                        .media(media)
                        .startDate(LocalDateTime.now())
                        .endDate(LocalDateTime.now().plusDays(days - 1))
                        .build());

                media.setAmount(media.getAmount() - 1);
                mediaRepository.save(media);
                MediaResponseDto mediaResponseDto = MediaResponseDto.builder()
                        .id(media.getMediaId())
                        .type(media.getType())
                        .title(media.getTitle())
                        .details(media.getDescription())
                        .poster(media.getPoster())
                        .rating(media.getAverageRating())
                        .amount(media.getAmount())
                        .build();

                log.info("User: {} Lending Media: {} for {} days", account.getUsername(), mediaResponseDto, days);

            } else {
                log.error("Payment failed for User: {} lending Media: {}", account.getUsername(), media.getMediaId());
            }

        });

        return sessionId;
    }

    public String expandMedia(Long accountId, Long mediaId, int days, HttpServletRequest request) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Media media = mediaRepository.findById(mediaId).orElseThrow(() -> new NoSuchElementException("Media not found"));
        Subscription subscription = subscriptionRepository.findByAccount(account).orElseThrow(() -> new NoSuchElementException("Subscription not found"));
        History history = historyRepository.findByMediaAccount(accountId, mediaId).orElseThrow(() -> new NoSuchElementException("History not found"));

        CurrentlyLending currentlyLending = currentlyLendingRepository.findByMediaUser(accountId, mediaId).orElseThrow(() -> new NoSuchElementException("CurrentlyLending not found"));

        String sessionId = paymentSocketClient.payForMedium(media.getPrice(), days, subscription.getType().getPriceReduction(), request);

        CompletableFuture.runAsync(() -> {

            PaymentSessionData paymentSessionData = paymentSocketClient.waitForStatus(sessionId);

            if ("SUCCESS".equals(paymentSessionData.getStatus())) {

                currentlyLending.setEndDate(LocalDateTime.now().plusDays(days));



                historyRepository.save(History.builder()
                        .account(account)
                        .media(media)
                        .startDate(history.getEndDate())
                        .endDate(history.getEndDate().plusDays(days))
                        .build());

                currentlyLendingRepository.save(currentlyLending);
                historyRepository.save(history);

                MediaResponseDto mediaResponseDto = MediaResponseDto.builder()
                        .id(media.getMediaId())
                        .type(media.getType())
                        .title(media.getTitle())
                        .details(media.getDescription())
                        .poster(media.getPoster())
                        .rating(media.getAverageRating())
                        .amount(media.getAmount())
                        .build();

                log.info("User: {} expanded Media: {} for {} days", account.getUsername(), mediaResponseDto, days);

            } else {
                log.error("Payment failed for User: {} expanding Media: {}", account.getUsername(), media.getMediaId());
            }


        });

        return sessionId;

    }

    public List<HistoryResponseDto> getCurrentlyLending(Long accountId) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        return currentlyLendingRepository.findByAccount_AccountId(accountId).stream().map(media -> HistoryResponseDto.builder()
                .id(media.getMedia().getMediaId())
                .type(media.getMedia().getType())
                .title(media.getMedia().getTitle())
                .details(media.getMedia().getDescription())
                .poster(media.getMedia().getPoster())
                .rating(media.getMedia().getAverageRating())
                .amount(media.getMedia().getAmount())
                .startDate(media.getStartDate())
                .endDate(media.getEndDate())
                .build()).collect(Collectors.toList());
    }

}
