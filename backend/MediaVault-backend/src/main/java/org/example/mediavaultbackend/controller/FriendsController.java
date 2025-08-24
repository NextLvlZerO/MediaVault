package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.example.mediavaultbackend.dtos.AccountResponseDto;
import org.example.mediavaultbackend.dtos.UserFriendsRequestResponseDto;
import org.example.mediavaultbackend.dtos.UserFriendsWithResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.UserFriendsRequest;
import org.example.mediavaultbackend.models.UserFriendsWith;
import org.example.mediavaultbackend.services.AccountService;
import org.example.mediavaultbackend.services.FriendsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class FriendsController {

    private final FriendsService friendsService;
    private final AccountService accountService;

    @GetMapping("/find-user")
    public ResponseEntity<List<AccountResponseDto>> searchAccounts(@RequestParam("query") String searchQuery) {
        return ResponseEntity.ok().body(accountService.searchAccounts(searchQuery));
    }

    @GetMapping("{account-id}/friends")
    public ResponseEntity<List<AccountResponseDto>> getFriends(@PathVariable("account-id") Long accountId) {
        return ResponseEntity.ok().body(friendsService.getFriends(accountId));
    }

    @GetMapping("{account-id}/friend-requests")
    public ResponseEntity<List<AccountResponseDto>> getFriendRequests(@PathVariable("account-id") Long accountId) {
        return ResponseEntity.ok().body(friendsService.getFriendRequests(accountId));
    }

    @PostMapping("/{account1-id}/add-user/{account2-id}")
    public ResponseEntity<UserFriendsRequestResponseDto> sendFriendRequest(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(friendsService.sendFriendRequest(account1Id, account2Id));
    }

    @PostMapping("/{account1-id}/accept-user/{account2-id}")
    public ResponseEntity<List<UserFriendsWithResponseDto>> acceptFriendRequest(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(friendsService.acceptFriendRequest(account1Id, account2Id));
    }

    @PutMapping("/{account1-id}/deny-user/{account2-id}")
    public ResponseEntity<UserFriendsRequestResponseDto> denyFriendRequest(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(friendsService.denyFriendRequest(account1Id, account2Id));
    }


    @PutMapping("/{account1-id}/remove-user/{account2-id}")
    public ResponseEntity<List<UserFriendsWithResponseDto>> removeFriend(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(friendsService.removeFriend(account1Id, account2Id));
    }


}
