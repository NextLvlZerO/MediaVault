package org.example.mediavaultbackend.controller;


import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaFilterRequestDto;
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

    @GetMapping("/{type}/best-rated")
    public ResponseEntity<List<MediaResponseDto>> getBestRatedMedia(@PathVariable("type") String type , @RequestParam("page") int page, @RequestParam("page-size") int pageSize) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getBestRatedMedia(type, page, pageSize));
    }

    @GetMapping("/{type}/all")
    public ResponseEntity<List<MediaResponseDto>> getAllMedia(@PathVariable("type") String type , @RequestParam("page") int page, @RequestParam("page-size") int pageSize) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getAllMedia(type, page, pageSize));
    }

    @PostMapping("/{type}/filter")
    public ResponseEntity<List<MediaResponseDto>> getFilteredMedia(@PathVariable("type") String type , @RequestParam("page") int page, @RequestParam("page-size") int pageSize, @RequestBody MediaFilterRequestDto mediaFilterRequestDto) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getFilteredMedia(type, page, pageSize, mediaFilterRequestDto));
    }

    @GetMapping("/search")
    public ResponseEntity<List<MediaResponseDto>> searchMedia(@RequestParam("query") String mediaTitle) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.searchMovies(mediaTitle));
    }



    @GetMapping("/item/{id}")
    public ResponseEntity<MediaItemResponseDto> getMediaItem(@PathVariable("id") Long id) {
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(mediaService.getMediaItem(id));
    }

}
