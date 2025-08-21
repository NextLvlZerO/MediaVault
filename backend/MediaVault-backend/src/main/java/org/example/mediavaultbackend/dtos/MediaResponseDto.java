package org.example.mediavaultbackend.dtos;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class MediaResponseDto {

    private Long id;
    private String type;
    private String title;
    private String details;
    private String poster;
    private Double rating;
    private Integer amount;


}
