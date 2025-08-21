package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

import java.awt.print.Pageable;

@Data
@Builder
public class MediaItemResponseDto {

    private String type;
    private String title;
    private String details;
    private String poster;
    private Double rating;
    private Integer amount;
    private Boolean available;
    private Double price;

}
