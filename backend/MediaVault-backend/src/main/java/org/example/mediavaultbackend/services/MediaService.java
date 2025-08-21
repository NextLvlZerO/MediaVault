package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaItemResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public List<MediaResponseDto> getBestRatedMovies(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Media> mediaPage = mediaRepository.getBestRatedMovies(pageable);
        return mediaPage.getContent().stream().map(m -> MediaResponseDto.builder()
                .id(m.getMediaId())
                .type(m.getType())
                .title(m.getTitle())
                .details(m.getDescription())
                .poster(m.getPoster())
                .rating(m.getAverageRating())
                .amount(m.getAmount())
                .build()).collect(Collectors.toList());
    }

    public List<MediaResponseDto> getMovies(int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Media> mediaPage = mediaRepository.getMovies(pageable);
        return mediaPage.getContent().stream().map(m -> MediaResponseDto.builder()
                .id(m.getMediaId())
                .type(m.getType())
                .title(m.getTitle())
                .details(m.getDescription())
                .poster(m.getPoster())
                .rating(m.getAverageRating())
                .amount(m.getAmount())
                .build()).collect(Collectors.toList());

    }

    public Optional<MediaResponseDto> searchMovies(String mediaTitle) {
        return mediaRepository.findByTitle(mediaTitle)
                .map(m -> MediaResponseDto.builder()
                        .id(m.getMediaId())
                        .type(m.getType())
                        .title(m.getTitle())
                        .details(m.getDescription())
                        .poster(m.getPoster())
                        .rating(m.getAverageRating())
                        .amount(m.getAmount())
                        .build()
                );
    }


    public Optional<MediaItemResponseDto> getMediaItem(Long id) {
        return mediaRepository.findById(id)
                .map(m -> MediaItemResponseDto.builder()
                        .type(m.getType())
                        .title(m.getTitle())
                        .details(m.getDescription())
                        .poster(m.getPoster())
                        .rating(m.getAverageRating())
                        .amount(m.getAmount())
                        .available(m.getAmount()>0)
                        .price(m.getPrice())
                        .build()
                );
    }
}
