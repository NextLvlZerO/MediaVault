package org.example.mediavaultbackend.dtos;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PaymentSessionData {

    private final Long userId;
    private final String status;
    private final LocalDateTime createdAt;

}
