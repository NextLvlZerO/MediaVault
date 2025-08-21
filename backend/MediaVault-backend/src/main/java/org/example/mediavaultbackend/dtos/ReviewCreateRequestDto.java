package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewCreateRequestDto {

    private String title;
    private String details;
    private Integer rating;

}
