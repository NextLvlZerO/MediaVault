package org.example.mediavaultbackend.config;

import org.example.mediavaultbackend.utility.MessageServerSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final MessageServerSocket messageServerSocket;

    public WebSocketConfig(MessageServerSocket messageServerSocket) {
        this.messageServerSocket = messageServerSocket;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageServerSocket, "/message")
                .setAllowedOrigins("*");
    }
}
