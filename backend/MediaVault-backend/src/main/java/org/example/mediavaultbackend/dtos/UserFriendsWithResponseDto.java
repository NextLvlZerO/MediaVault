package org.example.mediavaultbackend.dtos;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserFriendsWithResponseDto {

    private Long friendsWithId;
    private Long account1Id;
    private Long account2Id;

}
