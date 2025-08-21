package org.example.mediavaultbackend.controller;


import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/media")
public class MediaController {

    private final MediaService mediaService;

    @GetMapping("/movies")
    public ResponseEntity<List<Media>> getAllMedia() {
        return ResponseEntity.ok().build();
    }

}
