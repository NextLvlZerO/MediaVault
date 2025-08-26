package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.HistoryResponseDto;
import org.example.mediavaultbackend.dtos.MediaResponseDto;
import org.example.mediavaultbackend.models.History;
import org.example.mediavaultbackend.models.Media;
import org.example.mediavaultbackend.services.HistoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/user/{id}")
    public ResponseEntity<List<HistoryResponseDto>> getUserHistory(@PathVariable("id") Long accountId, @RequestParam("page") int page, @RequestParam("page-size") int pageSize) {
        return ResponseEntity.ok().body(historyService.getUserHistory(accountId, page, pageSize));
    }

}
