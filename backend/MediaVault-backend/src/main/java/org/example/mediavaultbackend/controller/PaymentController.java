package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.mediavaultbackend.dtos.PaymentSessionData;
import org.example.mediavaultbackend.services.PaymentSocketClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.imageio.IIOException;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentSocketClient paymentSocketClient;

    @Autowired
    public PaymentController(PaymentSocketClient paymentSocketClient) {
        this.paymentSocketClient = paymentSocketClient;
    }

    @PostMapping("/start")
    public ResponseEntity<String> startPayment(HttpServletRequest request) {
                return ResponseEntity.ok(paymentSocketClient.sendPaymentRequest(1.0, request));
    }


}
