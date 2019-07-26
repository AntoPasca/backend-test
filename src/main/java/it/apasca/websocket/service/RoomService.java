package it.apasca.websocket.service;


import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Room;

import java.util.List;

public interface RoomService {
    public Room createRoom(Room room) throws Exception;
    public void deleteRoom(String roomId);
    public Room getRoom(String roomId) throws Exception;
    public List<Room> getRooms(Room room) throws Exception;

    public List<ChatMessage> getMessages(String roomId , ChatMessage chatMessage) throws Exception;
}
