package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Conversation;

public interface ChatService {

    public void join(UserDto user , Conversation conversation);
    public void leave(UserDto user , Conversation conversation);
    public void send(ChatMessage chatMessage);

}
