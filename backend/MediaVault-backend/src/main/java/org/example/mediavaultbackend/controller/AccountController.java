package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.example.mediavaultbackend.dtos.AccountRequestDto;
import org.example.mediavaultbackend.services.AccountService;
import org.example.mediavaultbackend.utility.Cookies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AccountController {

    private final AccountService accountService;
    private final Cookies cookies;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AccountRequestDto accountRequestDto, HttpServletResponse response) {
        ResponseEntity<String> responseData = ResponseEntity.status(HttpStatus.CREATED).body(accountService.register(accountRequestDto));

        cookies.setUserCookies(responseData, accountRequestDto, response);

        return responseData;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AccountRequestDto accountRequestDto, HttpServletResponse response) {
        ResponseEntity<String> responseData = ResponseEntity.ok().body(accountService.login(accountRequestDto));

        cookies.setUserCookies(responseData, accountRequestDto, response);

        return responseData;
    }
}
