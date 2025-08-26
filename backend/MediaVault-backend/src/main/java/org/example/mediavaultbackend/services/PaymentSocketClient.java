package org.example.mediavaultbackend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.example.mediavaultbackend.dtos.PaymentSessionData;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentSocketClient {

    private final PaymentNotificationService notificationService;
    private final AccountRepository accountRepository;
    private static final Logger log = LoggerFactory.getLogger(PaymentSocketClient.class);
    private final Map<String, CompletableFuture<PaymentSessionData>> pendingPayments = new ConcurrentHashMap<>();


    public PaymentSocketClient(PaymentNotificationService notificationService, AccountRepository accountRepository) {
        this.notificationService = notificationService;
        this.accountRepository = accountRepository;
    }

    public String sendPaymentRequest(Double price, HttpServletRequest request) {

        String username = Arrays.stream(request.getCookies())
                .filter(c -> "username".equals(c.getName()))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Missing username in cookie"));


        String sessionId = UUID.randomUUID().toString();
        Account account = accountRepository.findByUsername(username).orElseThrow(() -> new NoSuchElementException("Account not found"));

        CompletableFuture<PaymentSessionData> future = new CompletableFuture<>();
        pendingPayments.put(sessionId, future);

        CompletableFuture.runAsync(() -> {

            try (Socket socket = new Socket("mediavault-paymentservice", 9090);
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

                out.println("PAYMENT_REQUEST: "+ sessionId + ";" + price);
                log.info("Payment started for user: {}" , username);
                String response = in.readLine();
                String status = response.split(":")[0];

                PaymentSessionData sessionData = new PaymentSessionData(account.getAccountId(), status, LocalDateTime.now());
                future.complete(sessionData);

                notificationService.notifyPaymentStatus(sessionId, response);


            } catch (IOException e) {
                log.error("Error during payment: " + e.getMessage());

                PaymentSessionData sessionData = new PaymentSessionData(account.getAccountId() , "FAILED", LocalDateTime.now());
                future.complete(sessionData);

                notificationService.notifyPaymentStatus(sessionId, "ERROR: " + e.getMessage());
            }

        });

        return sessionId;
    }



    public String payForMedium(Double price, Integer days, Double priceReduction,  HttpServletRequest request) {
        return sendPaymentRequest(price * days * priceReduction, request);
    }

    public PaymentSessionData waitForStatus(String sessionId) {
        try {
            return pendingPayments.get(sessionId).get(); // blockiert bis Antwort kommt
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
