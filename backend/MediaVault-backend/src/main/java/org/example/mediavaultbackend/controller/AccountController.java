package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.example.mediavaultbackend.services.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok().body(accountService.register(accountRequestDto));
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountRequestDto accountRequestDto) {
        return ResponseEntity.ok().body(accountService.login(accountRequestDto));
    }

}
