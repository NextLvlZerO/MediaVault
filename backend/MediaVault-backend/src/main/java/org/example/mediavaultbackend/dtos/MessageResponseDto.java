package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class MessageResponseDto {

    private String text;
    private LocalDateTime date;
    private String type;

}
