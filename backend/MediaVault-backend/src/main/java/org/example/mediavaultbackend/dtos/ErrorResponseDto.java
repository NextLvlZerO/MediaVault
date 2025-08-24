package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {

    private String error;

}
