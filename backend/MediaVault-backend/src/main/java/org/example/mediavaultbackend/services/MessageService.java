package org.example.mediavaultbackend.services;

import lombok.RequiredArgsConstructor;
import org.example.mediavaultbackend.dtos.MessageResponseDto;
import org.example.mediavaultbackend.models.Account;
import org.example.mediavaultbackend.models.Message;
import org.example.mediavaultbackend.repositories.AccountRepository;
import org.example.mediavaultbackend.repositories.MessageRepository;
import org.example.mediavaultbackend.repositories.UserFriendsWithRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MessageService {

    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final AccountRepository accountRepository;
    private final MessageRepository messageRepository;
    private final UserFriendsWithRepository userFriendsWithRepository;


    public List<MessageResponseDto> getUserMessages(Long a1, Long a2) {

        Account account1 = accountRepository.findById(a1).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(a2).orElseThrow(() -> new NoSuchElementException("Account not found"));


        if (userFriendsWithRepository.findByAccounts(a1, a2).isEmpty()) {
            throw new NoSuchElementException("Account is no friend with receiver");
        }

        List<Message> messagesSent =  messageRepository.findByAccount1_AccountIdAndAccount2_AccountId(a1, a2);
        List<Message> messagesReceived =  messageRepository.findByAccount1_AccountIdAndAccount2_AccountId(a2, a1);

        List<MessageResponseDto> messagesResponseSent = messagesSent.stream().map(m -> {
            return MessageResponseDto.builder()
                    .text(m.getMessage())
                    .date(m.getDate())
                    .type("sent").build();
        }).toList();


        List<MessageResponseDto> messagesResponseReceived = messagesReceived.stream().map(m -> {
            return MessageResponseDto.builder()
                    .text(m.getMessage())
                    .date(m.getDate())
                    .type("received").build();
        }).toList();

        return Stream.concat(messagesResponseReceived.stream(), messagesResponseSent.stream())
                .sorted(Comparator.comparing(MessageResponseDto::getDate))
                .toList();
    };


    public MessageResponseDto sendUserMessage(Long a1, Long a2, String message) {

        Account account1 = accountRepository.findById(a1).orElseThrow(() -> new NoSuchElementException("Account not found"));
        Account account2 = accountRepository.findById(a2).orElseThrow(() -> new NoSuchElementException("Account not found"));

        if (userFriendsWithRepository.findByAccounts(a1, a2).isEmpty()) {
            throw new NoSuchElementException("Account is no friend with receiver");
        }

        Message savedMessage = messageRepository.save(Message.builder()
                .message(message)
                .account1(account1)
                .account2(account2)
            .date(LocalDateTime.now()).build());

        return MessageResponseDto.builder()
                .text(savedMessage.getMessage())
                .date(savedMessage.getDate())
                .type("sent").build();
    }
}
