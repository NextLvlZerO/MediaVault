package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.HistoryResponseDto;
import org.example.mediavaultbackend.dtos.LendingRequestDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.LendingService;
import org.example.mediavaultbackend.services.PaymentSocketClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class LendingController {

    private final LendingService lendingService;

    @PostMapping("/{account-id}/lend/media/{media-id}")
    public ResponseEntity<String> lendMedia(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId, @RequestBody LendingRequestDto lendingRequestDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lendingService.lendMedia(accountId, mediaId, lendingRequestDto.getDays(), request));
    }

    @PutMapping("/{account-id}/expand/media/{media-id}")
    public ResponseEntity<String> expandMedia(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId, @RequestBody LendingRequestDto lendingRequestDto, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lendingService.expandMedia(accountId, mediaId, lendingRequestDto.getDays(), request));
    }

    @GetMapping("/{id}/currently-lending")
    public ResponseEntity<List<HistoryResponseDto>> getCurrentlyLending(@PathVariable("id") Long accountId) {
        return ResponseEntity.ok().body(lendingService.getCurrentlyLending(accountId));
    }


}
