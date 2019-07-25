package it.apasca.websocket.service;

import it.apasca.websocket.dao.ConversationDao;
import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.model.ChatMessage;
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

    @Autowired
    private MessageDao messageDao;


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

    @Override
    public List<ChatMessage> getMessages(String roomId ,ChatMessage chatMessage) throws Exception{
        // i messaggi richiesti appartengono alla room
        Optional<Conversation> roomOpt = conversationDao.findById(roomId);
        ChatMessage messageToReturn = new ChatMessage();
        if (!roomOpt.isPresent()) {
            throw new NotFoundException("stanza non trovata!");
        }
        BeanUtils.copyProperties(chatMessage, messageToReturn , "conversationId");
        messageToReturn.setConversation(roomOpt.get());

        return messageDao.findAll(Example.of(messageToReturn));
    }
}
