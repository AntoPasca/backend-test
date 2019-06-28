package it.apasca.websocket.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker //Permette si abilitare il server WebSocket
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override //In questo metodo registriamo un endpoint per i client
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws").setAllowedOrigins("*")
        .withSockJS();
    }

    @Override //In questo metodo configuriamo un message broker che verrà usato per reindirizzare i messaggi tra i client
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.setApplicationDestinationPrefixes("/app");
        registry.enableSimpleBroker("/topic");
    }
}