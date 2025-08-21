package org.example.mediavaultbackend.controller;


import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaItemResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.StreamingHttpOutputMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/movies/best-rated")
    public ResponseEntity<List<MediaResponseDto>> getBestRatedMovies(@RequestParam("page") int page, @RequestParam("page-size") int pageSize) {

        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getBestRatedMovies(page, pageSize));
    }

    @GetMapping("/movies/all")
    public ResponseEntity<List<MediaResponseDto>> getMovies(@RequestParam("page") int page, @RequestParam("page-size") int pageSize) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getMovies(page, pageSize));
    }

    @GetMapping("/search")
    public ResponseEntity<MediaResponseDto> searchMedia(@RequestParam("query") String mediaTitle) {

        return mediaService.searchMovies(mediaTitle)
                .map(dto -> ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(dto))
                .orElse(ResponseEntity.notFound().build());

    }

    @GetMapping("/item/{id}")
    public ResponseEntity<MediaItemResponseDto> getMediaItem(@PathVariable("id") Long id) {
        return mediaService.getMediaItem(id)
                .map(dto -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto))
                .orElse(ResponseEntity.notFound().build());
    }

}
