package org.example.mediavaultbackend.services;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.ReviewCreateRequestDto;
import org.example.mediavaultbackend.dtos.ReviewResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.Review;
import org.example.mediavaultbackend.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private static final Logger log = LoggerFactory.getLogger(ReviewService.class);
    private final ReviewRepository reviewRepository;
    private final AccountRepository accountRepository;
    private final MediaRepository mediaRepository;
    private final CurrentlyLendingRepository currentlyLendingRepository;
    private final HistoryRepository historyRepository;

    public List<ReviewResponseDto> getReviews(Long id, int page, int pageSize) {

        List<Review> review = reviewRepository.findByMedia_MediaId(id);

        return review.stream().map(s -> ReviewResponseDto.builder()
                .username(s.getAccount().getUsername())
                .title(s.getTitle())
                .details(s.getDetails())
                .rating(s.getRating())
                .verified(s.getVerified())
                .build()).collect(Collectors.toList());

    }

    public ReviewResponseDto createReview(HttpServletRequest request, Long id, ReviewCreateRequestDto reviewCreateRequestDto) {


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
            throw new IllegalArgumentException("Missing username in cookie.");
        } else {

            Account account = accountRepository.findByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            Media media = mediaRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Media not found"));

            Boolean verified = !historyRepository.findByMediaAccount(account.getAccountId(), media.getMediaId()).isEmpty();

            Review review = Review.builder()
                    .title(reviewCreateRequestDto.getTitle())
                    .details(reviewCreateRequestDto.getDetails())
                    .account(account)
                    .media(media)
                    .rating(reviewCreateRequestDto.getRating())
                    .date(LocalDateTime.now())
                    .verified(verified)
                    .build();

            reviewRepository.save(review);

            ReviewResponseDto reviewResponseDto = ReviewResponseDto.builder()
                    .username(username)
                    .title(reviewCreateRequestDto.getTitle())
                    .details(reviewCreateRequestDto.getDetails())
                    .rating(reviewCreateRequestDto.getRating())
                    .verified(verified)
                    .build();

            log.info("Review created: {}", reviewResponseDto);

            return reviewResponseDto;
        }











    }
}
