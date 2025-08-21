package org.example.mediavaultbackend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.ReviewCreateRequestDto;
import org.example.mediavaultbackend.dtos.ReviewResponseDto;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.Review;
import org.example.mediavaultbackend.models.User;
import org.example.mediavaultbackend.repositories.CurrentlyLendingRepository;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.example.mediavaultbackend.repositories.ReviewRepository;
import org.example.mediavaultbackend.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.net.http.HttpRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final CurrentlyLendingRepository currentlyLendingRepository;

    public List<ReviewResponseDto> getReviews(Long id, int page, int pageSize) {

        List<Review> review = reviewRepository.findByMedia_MediaId(id);

        return review.stream().map(s -> ReviewResponseDto.builder()
                .username(s.getUser().getUsername())
                .title(s.getTitle())
                .details(s.getDetails())
                .rating(s.getRating())
                .verified(s.getVerified())
                .build()).collect(Collectors.toList());

    }

    public Optional<ReviewResponseDto> createReview(HttpServletRequest request, Long id, ReviewCreateRequestDto reviewCreateRequestDto) {

        Cookie[] cookies = request.getCookies();
        String username = null;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("username".equals(cookie.getName())) {
                    username = cookie.getValue();
                }
            }
        }
        if (username == null) {
            return Optional.empty();
        } else {

            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Media media = mediaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Media not found"));

            Boolean verified = currentlyLendingRepository.findByMediaUser(media.getMediaId(), user.getUserId()).isPresent();


            Review review = Review.builder()
                    .title(reviewCreateRequestDto.getTitle())
                    .details(reviewCreateRequestDto.getDetails())
                    .user(user)
                    .media(media)
                    .rating(reviewCreateRequestDto.getRating())
                    .date(LocalDateTime.now())
                    .verified(verified)
                    .build();

            reviewRepository.save(review);

            return Optional.of(ReviewResponseDto.builder()
                    .username(username)
                    .title(reviewCreateRequestDto.getTitle())
                    .details(reviewCreateRequestDto.getDetails())
                    .rating(reviewCreateRequestDto.getRating())
                    .verified(verified)
                    .build());
        }




    }
}
