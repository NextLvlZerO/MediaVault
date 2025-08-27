package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.SubscriptionResponseDto;
import org.example.mediavaultbackend.models.SubscriptionType;
import org.example.mediavaultbackend.services.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/subscription")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @GetMapping("/types")
    public List<SubscriptionType> getSubscriptionTypes() {
        return subscriptionService.getSubscriptionTypes();
    }

    @PutMapping("/update/{type}/user/{account-id}")
    public ResponseEntity<String> updateSubscriptionType(@PathVariable("type") String type, @PathVariable("account-id") Long accountId, HttpServletRequest request) {
        return ResponseEntity.ok().body(subscriptionService.updateSubscriptionType(type, accountId, request));
    }

    @PutMapping("/cancel/user/{account-id}")
    public ResponseEntity<SubscriptionResponseDto> cancelSubscription(@PathVariable("account-id") Long accountId) {
        return ResponseEntity.ok().body(subscriptionService.cancelSubscription(accountId));
    }


    @GetMapping("/user/{account-id}")
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable("account-id") Long accountId) {
        return ResponseEntity.ok().body(subscriptionService.getSubscription(accountId));
    }


}
