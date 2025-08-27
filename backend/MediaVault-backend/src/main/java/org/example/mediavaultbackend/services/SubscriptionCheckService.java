package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.PaymentSessionData;
import org.example.mediavaultbackend.dtos.SubscriptionResponseDto;
import org.example.mediavaultbackend.models.Subscription;
import org.example.mediavaultbackend.models.SubscriptionType;
import org.example.mediavaultbackend.repositories.SubscriptionRepository;
import org.example.mediavaultbackend.repositories.SubscriptionTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SubscriptionCheckService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionTypeRepository subscriptionTypeRepository;
    private final PaymentSocketClient paymentSocketClient;
    private static final Logger log = LoggerFactory.getLogger(SubscriptionCheckService.class);

    @Scheduled(cron = "0 30 17 * * *")
    public void checkSubscriptions() {

        log.info("Scheduled Task: Checking subscriptions");

        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        SubscriptionType free = subscriptionTypeRepository.findByName("Free").orElseThrow(() -> new RuntimeException("Subscription type not found"));

        allSubscriptions.parallelStream().forEach(s -> {

            if (Optional.ofNullable(s.getExpireDate()).isEmpty()) {
                return;
            }

            if (s.getActive() == false && s.getExpireDate().isBefore(LocalDate.now())) {
                s.setType(free);
                s.setActive(true);
                s.setExpireDate(null);
                subscriptionRepository.save(s);

                log.info("Subscription: {} expired", s.getSubscriptionId());
            } else if (s.getActive() == true && s.getExpireDate().isBefore(LocalDate.now())) {
                String sessionId = paymentSocketClient.sendPaymentRequest(s.getType().getPrice(),s.getAccount().getUsername(), "SUBSCRIPTION");
                PaymentSessionData paymentSessionData = paymentSocketClient.waitForStatus(sessionId);
                if ("SUCCESS".equals(paymentSessionData.getStatus())) {
                    s.setExpireDate(s.getExpireDate().plusMonths(1));
                    subscriptionRepository.save(s);
                    log.info("Subscription: {} expanded", s.getSubscriptionId());
                } else {
                    s.setType(free);
                    s.setActive(true);
                    s.setExpireDate(null);
                    subscriptionRepository.save(s);
                    log.info("Payment failed: Subscription: {} expired", s.getSubscriptionId());
                }
            }

        });

    }

}
