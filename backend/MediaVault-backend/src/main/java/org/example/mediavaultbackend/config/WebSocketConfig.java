package org.example.mediavaultbackend.config;

import org.example.mediavaultbackend.utility.MessageServerSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketConfigurer, WebSocketMessageBrokerConfigurer {

    private final MessageServerSocket messageServerSocket;

    public WebSocketConfig(MessageServerSocket messageServerSocket) {
        this.messageServerSocket = messageServerSocket;
    }

    // STOMP Config
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/payment").setAllowedOriginPatterns("*").withSockJS();
    }

    // Simple WebSocket Handler Config
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(messageServerSocket, "/message")
                .setAllowedOrigins("*");
    }
}
