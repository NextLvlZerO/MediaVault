package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountService.class);
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    private final WatchlistService watchlistService;


    public String register(AccountRequestDto accountRequestDto) {

        accountRepository.findByUsername(accountRequestDto.getUsername()).ifPresent(account -> {throw new RuntimeException(new CredentialException("Username already exists")); });

        String hash = bCryptPasswordEncoder.encode(accountRequestDto.getPassword());

        accountRepository.save(Account.builder()
                .username(accountRequestDto.getUsername())
                .password(hash)
                .build());

        log.info("Registered new account: {}", accountRequestDto.getUsername());

        Account account = accountRepository.findByUsername(accountRequestDto.getUsername()).orElseThrow(() -> new NoSuchElementException("User not found"));
        watchlistService.createWatchlist(account.getAccountId());

        return "account successfully created";
    }


    public String login(AccountRequestDto accountRequestDto) {

        Account account = accountRepository.findByUsername(accountRequestDto.getUsername()).orElseThrow(() -> new RuntimeException(new CredentialException("Bad credentials")));
        if (!bCryptPasswordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())) {
            throw new RuntimeException(new CredentialException("bad credentials"));
        }

        return "account successfully logged in";
    }



}
