package org.example.mediavaultbackend.dtos;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class HistoryResponseDto {

    private Long historyId;
    private Long accountId;
    private Long mediaId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
