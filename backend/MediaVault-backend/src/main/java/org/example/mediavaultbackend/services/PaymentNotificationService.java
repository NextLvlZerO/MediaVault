package org.example.mediavaultbackend.services;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class PaymentNotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public PaymentNotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void notifyPaymentStatus(String sessionId, String status) {
        messagingTemplate.convertAndSend(
                "/topic/payment-status/" + sessionId,
                "Payment status: " + status
        );
    }

}
