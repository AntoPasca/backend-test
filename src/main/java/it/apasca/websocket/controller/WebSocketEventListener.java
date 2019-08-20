package it.apasca.websocket.controller;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import lombok.extern.slf4j.Slf4j;


/*
 * Questa classe viene utilizzata per le connessioni e disconnessioni al socket.
 * In questo modo si tiene traccia degli eventi.
 */
@Slf4j
@Component
public class WebSocketEventListener {
    
    // login
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
    	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    	String username = headerAccessor.getNativeHeader("userId").get(0);
    	if (username == null || username.isEmpty()) {
    		log.error("utente non bene identificato");
    	}
        log.info("Received a new web socket connection: " + username);
    }
    // logout
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if(username != null) {
            log.info("User Disconnected : " + username);
        }
    }
}
