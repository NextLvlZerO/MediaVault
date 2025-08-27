package org.example.mediavaultbackend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.PaymentSessionData;
import org.example.mediavaultbackend.dtos.SubscriptionResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Subscription;
import org.example.mediavaultbackend.models.SubscriptionType;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.SubscriptionRepository;
import org.example.mediavaultbackend.repositories.SubscriptionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final AccountRepository accountRepository;
    private final PaymentSocketClient paymentSocketClient;

    public List<SubscriptionType> getSubscriptionTypes() {
        return subscriptionTypeRepository.findAll();
    }

    public SubscriptionResponseDto createSubscription(Long accountId, String type, LocalDate expireDate) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        subscriptionRepository.save(Subscription.builder()
                .account(account)
                .type(subscriptionTypeRepository.findByName(type).orElseThrow(() -> new NoSuchElementException("Subscription type not found")))
                .active(true)
                .expireDate(expireDate)
                .build());
        Subscription subscription = subscriptionRepository.findByAccount(account).orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        SubscriptionResponseDto subscriptionResponseDto = SubscriptionResponseDto.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .accountId(subscription.getAccount().getAccountId())
                .type(subscription.getType())
                .active(subscription.getActive())
                .expireDate(subscription.getExpireDate())
                .build();

        log.info("Subscription created: " + subscriptionResponseDto);

        return subscriptionResponseDto;
    }


    public String updateSubscriptionType(String type, Long accountId, HttpServletRequest request) {

        String username = Arrays.stream(request.getCookies())
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing username in cookie"));

        Subscription subscription = subscriptionRepository.findByAccount_AccountId(accountId).orElseThrow(() -> new NoSuchElementException("Subscription not found"));
        SubscriptionType subscriptionType = subscriptionTypeRepository.findByName(type).orElseThrow(() -> new NoSuchElementException("Subscription type not found"));

        String sessionId = paymentSocketClient.sendPaymentRequest(subscriptionType.getPrice(), username, "SUBSCRIPTION");

        CompletableFuture.runAsync(() -> {

            PaymentSessionData paymentSessionData = paymentSocketClient.waitForStatus(sessionId);
            if ("SUCCESS".equals(paymentSessionData.getStatus())) {

                subscription.setType(subscriptionType);
                subscription.setExpireDate(LocalDate.now().plusMonths(1));
                subscription.setActive(true);

                subscriptionRepository.save(subscription);

                SubscriptionResponseDto subscriptionResponseDto = SubscriptionResponseDto.builder()
                        .subscriptionId(subscription.getSubscriptionId())
                        .accountId(subscription.getAccount().getAccountId())
                        .type(subscription.getType())
                        .active(subscription.getActive())
                        .expireDate(subscription.getExpireDate())
                        .build();

                log.info("Subscription updated: {}", subscriptionResponseDto);

            } else {
                log.error("Payment for Subscription: {} update failed", subscription.getSubscriptionId());
            }

        });

        return sessionId;
    }

    public SubscriptionResponseDto cancelSubscription(Long accountId) {
        Subscription subscription = subscriptionRepository.findByAccount_AccountId(accountId).orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        subscription.setActive(false);
        subscriptionRepository.save(subscription);

        SubscriptionResponseDto subscriptionResponseDto = SubscriptionResponseDto.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .accountId(subscription.getAccount().getAccountId())
                .type(subscription.getType())
                .active(subscription.getActive())
                .expireDate(subscription.getExpireDate())
                .build();

        log.info("Subscription canceled: {}", subscriptionResponseDto);


        return subscriptionResponseDto;
    }



    public SubscriptionResponseDto getSubscription(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        Subscription subscription = subscriptionRepository.findByAccount(account).orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        return SubscriptionResponseDto.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .accountId(subscription.getAccount().getAccountId())
                .type(subscription.getType())
                .active(subscription.getActive())
                .expireDate(subscription.getExpireDate())
                .build();
    }


}
