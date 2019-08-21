package it.apasca.websocket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import it.apasca.websocket.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Controller
public class ChatController {
	
	@Autowired
	private ChatService chatService;
	

	/*
	 * Tutti i messaggi mandati dai client al path /app verranno reindirizzati ai metodi handling annotati 
	 * con MessageMapping
	 * 
	 * For example, a message with destination /app/chat.sendMessage 
	 * will be routed to the sendMessage() method, 
	 * and a message with destination /app/chat.addUser 
	 * will be routed to the addUser() method.
	 * */
		
    @MessageMapping("/chat.send")
    public void send(@Payload IncomingMessage incomingMessage) throws Exception {
    	chatService.send(incomingMessage);
    }
    
    @MessageMapping("/chat.join")
    public void join(@Payload IncomingMessage incomingMessage) throws Exception {
    	chatService.join(incomingMessage.getUserID(), incomingMessage.getRoomID());
    }
    
    @MessageMapping("/chat.leave")
    public void leave(@Payload IncomingMessage incomingMessage) throws Exception {
    	chatService.leave(incomingMessage.getUserID(), incomingMessage.getRoomID());
    }
    
    // messaggi provenienti da utente 
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IncomingMessage {
    	private String userID;
    	private String roomID;
    	private String content;
    }
}