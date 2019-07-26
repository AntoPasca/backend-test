package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Room;

public interface ChatService {

    public void join(UserDto user , Room room);
    public void leave(UserDto user , Room room);
    public void send(ChatMessage chatMessage);

}
