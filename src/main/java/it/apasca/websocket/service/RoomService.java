package it.apasca.websocket.service;


import it.apasca.websocket.model.Conversation;

import java.util.List;

public interface RoomService {
    public Conversation createRoom(Conversation conversation) throws Exception;
    public void deleteRoom(String roomId);
    public Conversation getRoom(String roomId) throws Exception;
    public List<Conversation> getRooms(Conversation conversation) throws Exception;
}
