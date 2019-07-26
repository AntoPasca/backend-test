package it.apasca.websocket.controller;

import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import it.apasca.websocket.model.ChatMessage;
import lombok.extern.slf4j.Slf4j;


/*
 * Questa classe viene utilizzata per le connessioni e disconnessioni al socket.
 * In questo modo si tiene traccia degli eventi.
 */
@Slf4j
@Component
public class WebSocketEventListener {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        log.info("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            log.info("User Disconnected : " + username);

            ChatMessage chatMessage = new ChatMessage();
            chatMessage.setType(ChatMessage.MessageType.LEAVE);
            User user = new User();
            user.setUsername("tst");
            chatMessage.setSender(user); // TODO: setta utente completo - dto

            messagingTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
