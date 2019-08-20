package it.apasca.websocket.service;

import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.dao.RoomDao;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Room;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomDao roomDao;

    @Autowired
    private MessageDao messageDao;


    @Override
    public Room createRoom(Room room) throws Exception {
        Room roomToSave = new Room();
        BeanUtils.copyProperties(room , roomToSave , "id");
        room = roomDao.save(roomToSave);
        return room;
    }

    @Override
    public void deleteRoom(String roomId) {
        roomDao.deleteById(roomId);
    }

    @Override
    public Room getRoom(String roomId) throws Exception {
        Optional<Room> roomOtp = roomDao.findById(roomId);

        if (!roomOtp.isPresent()) {
            throw new NotFoundException("Stanza non trovata!");
        }
        return roomOtp.get();
    }

    @Override
    public List<Room> getRooms(Room room) throws Exception {
        return roomDao.findAll(Example.of(room));
    }

    @Override
    public List<ChatMessage> getMessages(String roomId ,ChatMessage chatMessage) throws Exception {
        // i messaggi richiesti appartengono alla room
        Optional<Room> roomOpt = roomDao.findById(roomId);
        ChatMessage messageToReturn = new ChatMessage();
        if (!roomOpt.isPresent()) {
            throw new NotFoundException("stanza non trovata!");
        }
        BeanUtils.copyProperties(chatMessage, messageToReturn , "roomId");
        messageToReturn.setRoomID(roomOpt.get().getId());

        return messageDao.findAll(Example.of(messageToReturn));
    }
}
