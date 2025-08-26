package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.SubscriptionResponseDto;
import org.example.mediavaultbackend.models.SubscriptionType;
import org.example.mediavaultbackend.services.SubscriptionService;
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
    public String updateSubscriptionType(@PathVariable("type") String type, @PathVariable("account-id") Long accountId, HttpServletRequest request) {
        return subscriptionService.updateSubscriptionType(type, accountId, request);
    }


}
