package org.example.mediavaultbackend.repositories;

import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.UserFriendsWith;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFriendsWithRepository extends JpaRepository<UserFriendsWith, Long> {

    @Query("SELECT uf.account2 FROM UserFriendsWith uf WHERE uf.account1 = :account")
    List<Account> findFriendsOfAccount(Account account);

}
