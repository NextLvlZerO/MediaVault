package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialException;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    public String register(AccountRequestDto accountRequestDto) {
        String hash = bCryptPasswordEncoder.encode(accountRequestDto.getPassword());

        accountRepository.save(Account.builder()
                .username(accountRequestDto.getUsername())
                .password(hash)
                .build());

        return "account successfully created";
    }


    public String login(AccountRequestDto accountRequestDto) {

        Account account = accountRepository.findByUsername(accountRequestDto.getUsername()).orElseThrow(() -> new RuntimeException(new CredentialException("bad credentials")));
        if (!bCryptPasswordEncoder.matches(accountRequestDto.getPassword(), account.getPassword())) {
            throw new RuntimeException(new CredentialException("bad credentials"));
        }

        return "account successfully logged in";
    }
}
