package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.HistoryResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.History;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.HistoryRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HistoryService {

    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;

    public List<HistoryResponseDto> getUserHistory(Long accountId, int page, int pageSize) {

        Account account = accountRepository.findById(accountId).orElseThrow(() -> new NoSuchElementException("Account not found"));

        Pageable pageable = PageRequest.of(page, pageSize, Sort.by("startDate").descending());

        return historyRepository.findByAccount(account, pageable).stream().map(media -> HistoryResponseDto.builder()
                .id(media.getMedia().getMediaId())
                .type(media.getMedia().getType())
                .title(media.getMedia().getTitle())
                .details(media.getMedia().getDescription())
                .poster(media.getMedia().getPoster())
                .rating(media.getMedia().getAverageRating())
                .amount(media.getMedia().getAmount())
                .startDate(media.getStartDate())
                .endDate(media.getEndDate())
                .build()).toList();
    }
}
