/**
 * 
 */
package it.apasca.websocket.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.model.ChatMessage;

/**
 * @author B.Conetta
 *
 */
@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageDao messageDao;

	@Override
	public String save(ChatMessage chatMessage) {
		chatMessage.setSendTime(new Date());
		chatMessage = messageDao.save(chatMessage);
		return chatMessage.getId();
	}

	@Override
	public List<ChatMessage> getMessage() {
		List<ChatMessage> messages = messageDao.findAll();
		return messages;
	}

}
