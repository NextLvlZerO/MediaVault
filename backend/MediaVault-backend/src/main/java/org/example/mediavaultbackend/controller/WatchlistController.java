package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.WatchlistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class WatchlistController {


    private final WatchlistService watchlistService;

    @GetMapping("/{id}/watchlist")
    public ResponseEntity<List<MediaResponseDto>> getUserWatchlist(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(watchlistService.getUserWatchlist(id));
    }

    @GetMapping("{account-id}/media/{media-id}/watchlist")
    public ResponseEntity<Map<String, Boolean>> getUserMediaWatchlist(@PathVariable("account-id") long accountId, @PathVariable("media-id") long mediaId) {
        return ResponseEntity.ok().body(watchlistService.getUserMediaWatchlist(accountId, mediaId));
    }

    @PutMapping("/{account-id}/watchlist/media/{media-id}")
    public ResponseEntity<MediaResponseDto> updateWatchlist(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId) {
        return ResponseEntity.ok().body(watchlistService.updateWatchlist(accountId, mediaId));
    }

}
