package it.apasca.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import it.apasca.websocket.model.ChatMessage;

@Controller
public class ChatController {

	/*
	 * Tutti i messaggi mandati dai client al path /app verranno reindirizzati ai metodi handling annotati 
	 * con MessageMapping
	 * 
	 * For example, a message with destination /app/chat.sendMessage 
	 * will be routed to the sendMessage() method, 
	 * and a message with destination /app/chat.addUser 
	 * will be routed to the addUser() method.
	 * */
		
    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage, 
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

}