package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {

    private String username;
    private String title;
    private String details;
    private Integer rating;
    private Boolean verified;

}
