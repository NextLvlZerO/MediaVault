package org.example.mediavaultbackend.controller;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MessageRequestDto;
import org.example.mediavaultbackend.dtos.MessageResponseDto;
import org.example.mediavaultbackend.models.Message;
import org.example.mediavaultbackend.services.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MessageController {
    private final MessageService messageService;

    @GetMapping("/user/{account1-id}/message/{account2-id}")
    public ResponseEntity<List<MessageResponseDto>> getUserMessages(@PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(messageService.getUserMessages(account1Id, account2Id));
    }

    @PostMapping("/user{account1-id}/message/{account2-id}")
    public ResponseEntity<MessageResponseDto> sendUserMessage(@RequestBody MessageRequestDto messageBody, @PathVariable("account1-id") Long account1Id, @PathVariable("account2-id") Long account2Id) {
        return ResponseEntity.ok().body(messageService.sendUserMessage(account1Id, account2Id, messageBody.getMessage()));
    }

}
