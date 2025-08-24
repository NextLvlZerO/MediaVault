package org.example.mediavaultbackend.dtos;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.SubscriptionType;

import java.time.LocalDate;

@Data
@Builder
public class SubscriptionResponseDto {

    private Long subscriptionId;
    private Long accountId;
    private SubscriptionType type;
    private Boolean active;
    private LocalDate expireDate;

}
