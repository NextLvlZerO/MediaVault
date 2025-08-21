package org.example.mediavaultbackend.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.ReviewCreateRequestDto;
import org.example.mediavaultbackend.dtos.ReviewResponseDto;
import org.example.mediavaultbackend.models.Review;
import org.example.mediavaultbackend.services.ReviewService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/media/")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/{id}/reviews")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(@PathVariable Long id, @RequestParam("page") int page, @RequestParam("page-size") int pageSize) {
        return ResponseEntity.ok().body(reviewService.getReviews(id,page, pageSize));
    }

    @PostMapping("/{id}/review/create")
    public ResponseEntity<ReviewResponseDto> createReview(HttpServletRequest request, @PathVariable Long id, @RequestBody ReviewCreateRequestDto reviewCreateRequestDto) {
        return reviewService.createReview(request , id, reviewCreateRequestDto)
                .map(dto -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(dto))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
