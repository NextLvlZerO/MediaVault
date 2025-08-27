package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.models.CurrentlyLending;
import org.example.mediavaultbackend.repositories.CurrentlyLendingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LendingRemoveService {

    private final CurrentlyLendingRepository currentlyLendingRepository;
    private static final Logger log = LoggerFactory.getLogger(LendingRemoveService.class);

    @Scheduled(cron = "0 0 0 * * *")
    public void removeLendings() {
        log.info("Removing expired Lendings");

        List<CurrentlyLending> lendings = currentlyLendingRepository.findAll();

        lendings = lendings.parallelStream().filter(l -> l.getEndDate().isBefore(LocalDateTime.now())).collect(Collectors.toList());
        currentlyLendingRepository.deleteAll(lendings);
        log.info("Lendings removed: {}", lendings);

    }

}
