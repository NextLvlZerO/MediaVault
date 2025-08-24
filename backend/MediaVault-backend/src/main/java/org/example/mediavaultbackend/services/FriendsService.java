package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.UserFriendsRequest;
import org.example.mediavaultbackend.models.UserFriendsWith;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.UserFriendsRequestRepository;
import org.example.mediavaultbackend.repositories.UserFriendsWithRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FriendsService {

    private final AccountRepository accountRepository;
    private final UserFriendsRequestRepository userFriendsRequestRepository;
    private final UserFriendsWithRepository userFriendsWithRepository;


    public UserFriendsRequest sendFriendRequest(Long account1Id, Long account2Id) {

        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        UserFriendsRequest userFriendsRequest = UserFriendsRequest.builder()
                .account1(account1)
                .account2(account2)
                .build();

        userFriendsRequestRepository.save(userFriendsRequest);

        return userFriendsRequest;
    }

    public UserFriendsWith acceptFriendRequest(Long account1Id, Long account2Id) {

        Account account1 = accountRepository.findById(account1Id).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(account2Id).orElseThrow(() -> new NoSuchElementException("Account not found"));

        UserFriendsRequest userFriendsRequest = userFriendsRequestRepository.findByAccounts(account1Id, account2Id).orElseThrow(() -> new NoSuchElementException("Request not found"));

        userFriendsRequestRepository.delete(userFriendsRequest);

        UserFriendsWith userFriendsWith = UserFriendsWith.builder()
                .account1(account1)
                .account2(account2)
                .build();
        userFriendsWithRepository.save(userFriendsWith);

        return userFriendsWith;
    }

    public List<AccountResponseDto> searchAccounts(String searchQuery) {
        Page<Account> accountPage = accountRepository.findByQueryUsername(searchQuery, PageRequest.of(0, 5));

        return accountPage.getContent().stream().map(a -> AccountResponseDto.builder()
                .username(a.getUsername())
                .id(a.getAccountId())
                .build()).collect(Collectors.toList());
    }

    public List<AccountResponseDto> getFriends(Long accountId) {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        List<Account> accounts = userFriendsWithRepository.findFriendsOfAccount(account);
        return accounts.stream().map(a -> AccountResponseDto.builder()
                .username(a.getUsername())
                .id(a.getAccountId())
                .build()).collect(Collectors.toList());
    }
}
