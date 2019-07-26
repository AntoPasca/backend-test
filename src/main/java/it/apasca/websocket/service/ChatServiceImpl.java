package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Room;

public class ChatServiceImpl  implements  ChatService{

    @Override
    public void join(UserDto user, Room room) {
    // setta status a online
        // manda messaggio su chat di tipo join
        // salva sul db
    }

    @Override
    public void leave(UserDto user, Room room) {

    }

    @Override
    public void send(ChatMessage chatMessage) {

    }
}
