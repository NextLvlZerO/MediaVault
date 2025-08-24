package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountResponseDto {

    private long id;
    private String username;

}
