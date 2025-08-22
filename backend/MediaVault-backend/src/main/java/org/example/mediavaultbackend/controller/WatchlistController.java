package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.WatchlistService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class WatchlistController {


    private final WatchlistService watchlistService;

    @GetMapping("/{id}/watchlist")
    public ResponseEntity<List<Media>> getUserWatchlist(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(watchlistService.getUserWatchlist(id));
    }

    @PutMapping("/{account-id}/watchlist/media/{media-id}")
    public ResponseEntity<Media> updateWatchlist(@PathVariable("account-id") Long accountId, @PathVariable("media-id") Long mediaId) {
        return ResponseEntity.ok().body(watchlistService.updateWatchlist(accountId, mediaId));
    }

}
