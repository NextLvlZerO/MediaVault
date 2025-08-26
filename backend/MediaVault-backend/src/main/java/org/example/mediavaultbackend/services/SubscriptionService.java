package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
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
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private static final Logger log = LoggerFactory.getLogger(SubscriptionService.class);
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final AccountRepository accountRepository;

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


    public SubscriptionResponseDto getSubscription(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new RuntimeException("Account not found"));

        Subscription subscription = subscriptionRepository.findByAccount(account).orElseThrow(() -> new NoSuchElementException("Subscription not found"));

        SubscriptionResponseDto subscriptionResponseDto = SubscriptionResponseDto.builder()
                .subscriptionId(subscription.getSubscriptionId())
                .accountId(subscription.getAccount().getAccountId())
                .type(subscription.getType())
                .active(subscription.getActive())
                .expireDate(subscription.getExpireDate())
                .build();

        return subscriptionResponseDto;
    }


}
