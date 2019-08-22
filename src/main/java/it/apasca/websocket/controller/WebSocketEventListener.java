package it.apasca.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import it.apasca.websocket.service.UserService;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;


/*
 * Questa classe viene utilizzata per le connessioni e disconnessioni al socket.
 * In questo modo si tiene traccia degli eventi.
 */
@Slf4j
@Component
public class WebSocketEventListener {
	
	@Autowired
	private UserService userService;
    
    // login
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) throws Exception {
    	StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
    	String userID = headerAccessor.getNativeHeader("userId").get(0);
    	if (userID == null || userID.isEmpty()) {
    		log.error("utente id non trovato! ");
    		throw new NotFoundException("utente id non trovato! ");
    	}
    	headerAccessor.getSessionAttributes().put("userId", userID);
        log.debug("User Connected : " + userID);
        userService.setOnline(userID);
    }
    // logout
    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) throws Exception {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

        String userID = (String) headerAccessor.getSessionAttributes().get("userId");
        if(userID == null) {
        	log.error("utente id non trovato! ");
        	throw new NotFoundException("utente id non trovato! ");
        }
        log.debug("User Disconnected : " + userID);
        userService.setOffline(userID);
    }
}
