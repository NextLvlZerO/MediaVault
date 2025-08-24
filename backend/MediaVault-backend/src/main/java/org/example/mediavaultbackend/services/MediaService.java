package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaFilterRequestDto;
import org.example.mediavaultbackend.dtos.MediaItemResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Genre;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

    private static final Logger log = LoggerFactory.getLogger(MediaService.class);
    private final MediaRepository mediaRepository;

    public List<MediaResponseDto> getBestRatedMedia(String type, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Media> mediaPage = mediaRepository.getBestRatedMedia(type, pageable);
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

    public List<MediaResponseDto> getAllMedia(String type, int page, int pageSize) {

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Media> mediaPage = mediaRepository.getAllMedia(type, pageable);
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

    public List<MediaResponseDto> getFilteredMedia(String type, int page, int pageSize, MediaFilterRequestDto mediaFilterRequestDto) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Media> mediaPage = mediaRepository.getFilteredMedia(type, pageable, mediaFilterRequestDto.getGenre(), mediaFilterRequestDto.getGenre().size(), mediaFilterRequestDto.getPrice(), mediaFilterRequestDto.getRating());
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

    public List<MediaResponseDto> searchMovies(String mediaTitle) {
        Page<Media> mediaPage = mediaRepository.findByTitleSubstring(mediaTitle, PageRequest.of(0,5));
        System.out.println(mediaPage.getTotalElements());
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


    public MediaItemResponseDto getMediaItem(Long id) {
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
                ).orElseThrow(() -> new NoSuchElementException("Media not found"));
    }

    public Media saveMedia(String type, String title, String description, Boolean isAdult, LocalDate releaseDate, List<Genre> genres, String poster, Integer amount, Double price) {

        if (title.isBlank() || type.isBlank()) {
            throw new IllegalArgumentException("Invalid Media parameters");
        } else if (mediaRepository.findByTitle(title).isPresent()) {
            throw new IllegalArgumentException("Media already exists");
        }

        Media media = Media.builder()
                .type(type)
                .title(title)
                .description(description)
                .isAdult(isAdult)
                .releaseDate(releaseDate)
                .genres(genres)
                .poster(poster)
                .amount(amount)
                .price(price)
                .averageRating(4.0)
                .build();

        mediaRepository.save(media);
        log.info("Media saved: {}", media);
        return media;
    }


}
