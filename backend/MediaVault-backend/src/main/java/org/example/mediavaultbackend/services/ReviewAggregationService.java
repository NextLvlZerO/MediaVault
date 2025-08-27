package org.example.mediavaultbackend.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.models.Review;
import org.example.mediavaultbackend.repositories.MediaRepository;
import org.example.mediavaultbackend.repositories.ReviewRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class ReviewAggregationService {

    private final MediaRepository mediaRepository;
    private final ReviewRepository reviewRepository;
    private final Logger log = LoggerFactory.getLogger(ReviewAggregationService.class);



    @Scheduled(cron = "0 0 0 * * *") //
    public void aggregateReviewsDaily() {
        List<Media> allMedia = mediaRepository.findAll();

        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {

            List<CompletableFuture<Void>> futures = allMedia.stream()
                    .map(media -> CompletableFuture.runAsync(() -> {
                        List<Review> reviews = reviewRepository.findByMedia_MediaId(media.getMediaId());
                        if (!reviews.isEmpty()) {
                            Double average = reviews.stream()
                                    .mapToDouble(Review::getRating)
                                    .average()
                                    .orElse(0);

                            media.setAverageRating(average);

                            mediaRepository.save(media);
                        }
                    }, executor)
                    .exceptionally(ex -> {
                        log.error(ex.getMessage());
                        return null;
                    }))
                    .toList();

            CompletableFuture.allOf(futures.toArray(CompletableFuture[]::new)).join();

        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
