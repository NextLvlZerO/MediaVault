package org.example.mediavaultbackend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "user_friends_request")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFriendsRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendRequestId;

    @ManyToOne
    @JoinColumn(name = "account1_id", referencedColumnName = "account_id")
    private Account account1;

    @ManyToOne
    @JoinColumn(name = "account2_id", referencedColumnName = "account_id")
    private Account account2;
}
