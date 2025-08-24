package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.LendingRequestDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.LendingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class LendingController {

    private final LendingService lendingService;

    @PostMapping("/{account-id}/lend/media/{media-id}")
    public ResponseEntity<Media> lendMedia(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId, @RequestBody LendingRequestDto lendingRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lendingService.lendMedia(accountId, mediaId, lendingRequestDto.getDays()));
    }

    @PutMapping("/{account-id}/expand/media/{media-id}")
    public ResponseEntity<Media> expandMedia(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId, @RequestBody LendingRequestDto lendingRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(lendingService.expandMedia(accountId, mediaId, lendingRequestDto.getDays()));
    }


}
