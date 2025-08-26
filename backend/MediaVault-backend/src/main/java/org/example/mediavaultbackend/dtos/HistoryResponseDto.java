package org.example.mediavaultbackend.dtos;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class HistoryResponseDto {

    private Long id;
    private String type;
    private String title;
    private String details;
    private String poster;
    private Double rating;
    private Integer amount;
    private Boolean available;
    private Double price;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
