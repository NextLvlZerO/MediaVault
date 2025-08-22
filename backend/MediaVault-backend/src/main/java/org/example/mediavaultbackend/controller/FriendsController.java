package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.UserFriendsRequest;
import org.example.mediavaultbackend.models.UserFriendsWith;
import org.example.mediavaultbackend.services.FriendsService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class FriendsController {

    private final FriendsService friendsService;

    @PostMapping("/{account1-id}/add-user/{account2-id}")
    public ResponseEntity<UserFriendsRequest> sendFriendRequest(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(friendsService.sendFriendRequest(account1Id, account2Id));
    }

    @PostMapping("/{account1-id}/accept-user/{account2-id}")
    public ResponseEntity<UserFriendsWith> acceptFriendRequest(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(friendsService.acceptFriendRequest(account1Id, account2Id));
    }

}
