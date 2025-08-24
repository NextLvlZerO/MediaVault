package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MediaFilterRequestDto {

    private List<String> genre;
    private Double price;
    private Integer rating;

}
