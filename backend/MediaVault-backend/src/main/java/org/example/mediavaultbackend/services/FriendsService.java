package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountResponseDto;
import org.example.mediavaultbackend.dtos.UserFriendsRequestResponseDto;
import org.example.mediavaultbackend.dtos.UserFriendsWithResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.UserFriendsRequest;
import org.example.mediavaultbackend.models.UserFriendsWith;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.UserFriendsRequestRepository;
import org.example.mediavaultbackend.repositories.UserFriendsWithRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsService {

    private static final Logger log = LoggerFactory.getLogger(FriendsService.class);
    private final AccountRepository accountRepository;
    private final UserFriendsRequestRepository userFriendsRequestRepository;
    private final UserFriendsWithRepository userFriendsWithRepository;


    public List<AccountResponseDto> getFriendRequests(Long accountId){
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));
        List<Account> accounts = userFriendsRequestRepository.findRequestsByAccount(accountId);

        return accounts.stream()
                .map(a -> AccountResponseDto.builder()
                .username(a.getUsername())
                        .id(a.getAccountId())
                        .build()).collect(Collectors.toList());
    }

    public UserFriendsRequestResponseDto sendFriendRequest(Long account1Id, Long account2Id) {

        if (account1Id.equals(account2Id)) {
            throw new IllegalArgumentException("account1Id and account2Id cannot be the same");
        }

        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        if (userFriendsWithRepository.findByAccounts(account1Id, account2Id).isPresent() || userFriendsWithRepository.findByAccounts(account2Id, account1Id).isPresent()) {
            throw new IllegalArgumentException("User are already friends");

        } else if (userFriendsRequestRepository.findByAccounts(account1Id, account2Id).isPresent()) {
            throw new IllegalArgumentException("Friend request already exists");

        } else if(userFriendsRequestRepository.findByAccounts(account2Id, account1Id).isPresent()) {
            acceptFriendRequest(account1Id, account2Id);
            UserFriendsRequest userFriendsRequest = userFriendsRequestRepository.findByAccounts(account1Id, account2Id).get();
            return UserFriendsRequestResponseDto.builder()
                    .friendsRequestId(userFriendsRequest.getFriendRequestId())
                    .account1Id(account1Id)
                    .account2Id(account2Id)
                    .build();
        }

        UserFriendsRequest userFriendsRequest = UserFriendsRequest.builder()
                .account1(account1)
                .account2(account2)
                .build();

        userFriendsRequestRepository.save(userFriendsRequest);
        userFriendsRequest = userFriendsRequestRepository.findByAccounts(account1Id, account2Id).get();
        log.info("Friend request sent from account1: {} to account2: {}", account1Id, account2Id);
        return UserFriendsRequestResponseDto.builder()
                .friendsRequestId(userFriendsRequest.getFriendRequestId())
                .account1Id(account1Id)
                .account2Id(account2Id)
                .build();
    }

    public List<UserFriendsWithResponseDto> acceptFriendRequest(Long account1Id, Long account2Id) {

        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        UserFriendsRequest userFriendsRequest = userFriendsRequestRepository.findByAccounts(account2Id, account1Id).orElseThrow(() -> new NoSuchElementException("Request not found"));

        userFriendsRequestRepository.delete(userFriendsRequest);

        UserFriendsWith userFriendsWith1 = UserFriendsWith.builder()
                .account1(account1)
                .account2(account2)
                .build();

        UserFriendsWith userFriendsWith2 = UserFriendsWith.builder()
                .account1(account2)
                .account2(account1)
                .build();
        userFriendsWithRepository.saveAll(List.of(userFriendsWith1, userFriendsWith2));
        log.info("Account1: {} accepted friend request from account2: {}", account2Id, account1Id);

        userFriendsWith1 = userFriendsWithRepository.findByAccounts(account1Id, account2Id).orElseThrow(() -> new NoSuchElementException("Friends with not saved"));
        userFriendsWith2 = userFriendsWithRepository.findByAccounts(account2Id, account1Id).orElseThrow(() -> new NoSuchElementException("Friends with not saved"));



        UserFriendsWithResponseDto userFriendsWithResponseDto1 = UserFriendsWithResponseDto.builder()
                .friendsWithId(userFriendsWith1.getFriendsWithId())
                .account1Id(userFriendsWith1.getAccount1().getAccountId())
                .account2Id(userFriendsWith1.getAccount2().getAccountId())
                .build();


        UserFriendsWithResponseDto userFriendsWithResponseDto2 = UserFriendsWithResponseDto.builder()
                .friendsWithId(userFriendsWith2.getFriendsWithId())
                .account1Id(userFriendsWith2.getAccount1().getAccountId())
                .account2Id(userFriendsWith2.getAccount2().getAccountId())
                .build();

        return List.of(userFriendsWithResponseDto1, userFriendsWithResponseDto2);
    }

    public List<AccountResponseDto> getFriends(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        List<Account> accounts = userFriendsWithRepository.findFriendsOfAccount(account);
        return accounts.stream().map(a -> AccountResponseDto.builder()
                .username(a.getUsername())
                .id(a.getAccountId())
                .build()).collect(Collectors.toList());
    }


    public UserFriendsRequestResponseDto denyFriendRequest(Long account1Id, Long account2Id) {
        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account1 not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account2 not found"));
        UserFriendsRequest userFriendsRequest = userFriendsRequestRepository.findByAccounts(account2Id, account1Id).orElseThrow(() -> new NoSuchElementException("Request not found"));
        userFriendsRequestRepository.delete(userFriendsRequest);
        return UserFriendsRequestResponseDto.builder()
                .friendsRequestId(userFriendsRequest.getFriendRequestId())
                .account1Id(userFriendsRequest.getAccount1().getAccountId())
                .account2Id(userFriendsRequest.getAccount2().getAccountId())
                .build();
    }

    public List<UserFriendsWithResponseDto> removeFriend(Long account1Id, Long account2Id) {

        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account1 not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account2 not found"));
        UserFriendsWith userFriendsWith1 = userFriendsWithRepository.findByAccounts(account1Id, account2Id).orElseThrow(() -> new NoSuchElementException("User not friends"));
        UserFriendsWith userFriendsWith2 = userFriendsWithRepository.findByAccounts(account2Id, account1Id).orElseThrow( () -> new NoSuchElementException("User not friends"));
        UserFriendsWithResponseDto userFriendsWithResponseDto1 = UserFriendsWithResponseDto.builder()
                .friendsWithId(userFriendsWith1.getFriendsWithId())
                .account1Id(userFriendsWith1.getAccount1().getAccountId())
                .account2Id(userFriendsWith1.getAccount2().getAccountId())
                .build();

        UserFriendsWithResponseDto userFriendsWithResponseDto2 = UserFriendsWithResponseDto.builder()
                .friendsWithId(userFriendsWith2.getFriendsWithId())
                .account1Id(userFriendsWith2.getAccount1().getAccountId())
                .account2Id(userFriendsWith2.getAccount2().getAccountId())
                .build();

        userFriendsWithRepository.delete(userFriendsWith1);
        userFriendsWithRepository.delete(userFriendsWith2);

        return List.of(userFriendsWithResponseDto1, userFriendsWithResponseDto2);
    }
}
