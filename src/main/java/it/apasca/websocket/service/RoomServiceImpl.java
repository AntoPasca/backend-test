package it.apasca.websocket.service;

import it.apasca.websocket.dao.ConversationDao;
import it.apasca.websocket.model.Conversation;
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
    private ConversationDao conversationDao;


    @Override
    public Conversation createRoom(Conversation conversation) throws Exception {
        Conversation conversationToSave = new Conversation();
        BeanUtils.copyProperties(conversation , conversationToSave , "id");
        conversation = conversationDao.save(conversationToSave);
        return conversation;
    }

    @Override
    public void deleteRoom(String roomId) {
        conversationDao.deleteById(roomId);
    }

    @Override
    public Conversation getRoom(String roomId) throws Exception {
        Optional<Conversation> conversationOtp = conversationDao.findById(roomId);

        if (!conversationOtp.isPresent()) {
            throw new NotFoundException("Stanza non trovata!");
        }
        return conversationOtp.get();
    }

    @Override
    public List<Conversation> getRooms(Conversation conversation) throws Exception {
        return conversationDao.findAll(Example.of(conversation));
    }
}
