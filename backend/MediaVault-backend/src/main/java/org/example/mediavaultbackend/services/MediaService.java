package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MediaService {

    private final MediaRepository mediaRepository;

    public List<MediaResponseDto> getBestRatedMovies(int amount) {
        return mediaRepository.getBestRatedMovies(amount).stream().map(s -> MediaResponseDto.builder()
                .id(s.getMediaId())
                .type(s.getType())
                .title(s.getTitle())
                .details(s.getDescription())
                .poster(s.getPoster())
                .rating(s.getAverageRating())
                .amount(s.getAmount())
                .build()).collect(Collectors.toList());
    }

}
