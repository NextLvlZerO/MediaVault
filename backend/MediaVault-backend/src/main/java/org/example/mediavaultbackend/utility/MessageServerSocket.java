package org.example.mediavaultbackend.utility;

import org.example.mediavaultbackend.services.AccountService;
import org.example.mediavaultbackend.services.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


enum method {
    REGISTER,
    MESSAGE
}



@Component
public class MessageServerSocket extends TextWebSocketHandler {

    private final MessageService messageService;
    private static final Logger log = LoggerFactory.getLogger(MessageServerSocket.class);
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public MessageServerSocket(MessageService messageService) {
        this.messageService = messageService;
    }

    private static ConcurrentHashMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("Connected: " + session.getId());
    }


    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        super.afterConnectionClosed(session, status);
        System.out.println("Disconnected: " + session.getId());
    }


    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception{
        executorService.submit(() -> {
            try {

                String[] args = message.getPayload().toString().split(";");

                if (args.length == 1) {
                    System.out.println("Invalid arguments: " + session.getId());
                    return;
                }

                String currentStringMethod = args[0];
                if (Arrays.stream(method.values()).noneMatch(f -> f.name().equals(currentStringMethod))) {
                    System.out.println("Invalid method: " + currentStringMethod);
                    return;
                }

                method currentMethod = method.valueOf(currentStringMethod);

                switch (currentMethod) {
                    case REGISTER:
                        handleRegisterType(Arrays.copyOfRange(args, 1, args.length), session);
                        break;

                    case MESSAGE:
                        handleMessageType(Arrays.copyOfRange(args, 1, args.length), message);
                }
            }
            catch (Exception e) {
                log.info(e.getMessage());
            }
        });


        // just for testing, delete for prod

        /*
        for (WebSocketSession s: webSocketSessions) {
            if (s == session) continue;
            s.sendMessage(message);
        }
        */
    }


    public void handleRegisterType(String[] args, WebSocketSession session) {
        if (args.length != 1) {
            System.out.println("Invalid arguments: ");
            return;
        }

        try {
            Long id = Long.parseLong(args[0]);
            sessions.put(id, session);
            System.out.println("Registered: " + id);
            // TODO get all friends
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void handleMessageType(String[] args, WebSocketMessage<?> message) {
        if (args.length < 3) {
            System.out.println("Invalid arguments: ");
            return;
        }

        try {
            Long senderId = Long.parseLong(args[0]);
            Long receiverId = Long.parseLong(args[1]);


            if (sessions.getOrDefault(receiverId, null) == null) {
                System.out.println("Invalid receiver id " + receiverId);
                return;
            }

            String textMessage = String.join(";",Arrays.copyOfRange(args,2,args.length));

            messageService.sendUserMessage(senderId, receiverId, textMessage);
            System.out.println("trying to put message into table");

            sessions.get(receiverId).sendMessage(message);
        }
        catch(NumberFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
