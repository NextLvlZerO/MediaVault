package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.UserFriendsRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFriendsRequestRepository  extends JpaRepository<UserFriendsRequest, Long> {

    @Query("SELECT u FROM UserFriendsRequest u WHERE u.account1.accountId = :account1 AND u.account2.accountId = :account2")
    Optional<UserFriendsRequest> findByAccounts(Long account1, Long account2);

    @Query("SELECT u.account1 FROM UserFriendsRequest u WHERE u.account2.accountId = :account")
    List<Account> findRequestsByAccount(Long account);

}
