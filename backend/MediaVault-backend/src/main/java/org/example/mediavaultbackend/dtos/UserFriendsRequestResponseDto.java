package org.example.mediavaultbackend.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFriendsRequestResponseDto {

    private Long friendsRequestId;
    private Long account1Id;
    private Long account2Id;

}
