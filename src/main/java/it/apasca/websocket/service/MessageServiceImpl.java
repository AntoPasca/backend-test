/**
 * 
 */
package it.apasca.websocket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.apasca.websocket.dao.DeviceDao;
import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.dao.RoomDao;
import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.dto.Data;
import it.apasca.websocket.dto.FirebaseNotification;
import it.apasca.websocket.dto.OutgoingMessage;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Device;
import it.apasca.websocket.model.Room;
import it.apasca.websocket.model.User;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author B.Conetta
 *
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
	
	@Value("${firebase.server.key}")
	private String serverKey;
	@Value("${firebase.api.url}")
	private String apiUrl;
	
	@Autowired
	private MessageDao messageDao;
	
	@Autowired
	private DeviceDao deviceDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoomDao roomDao;
	
	@Autowired
	ObjectMapper objectmapper;
	
	@Override
	public String save(ChatMessage chatMessage) {
		chatMessage.setSendTime(new Date());
		if(chatMessage.getType() != ChatMessage.MessageType.LEAVE) {
			chatMessage = messageDao.save(chatMessage);
		}
		return chatMessage.getId();
	}

	@Override
	public void notify(ChatMessage chatMessage) throws Exception {
		if(chatMessage.getType() == ChatMessage.MessageType.CHAT) {
			FirebaseNotification firebaseNotification = new FirebaseNotification();
			Data data = new Data();
			Optional<User> senderOpt = userDao.findById(chatMessage.getSenderID());
			if (!senderOpt.isPresent()) {
				log.error("utente non trovato mentre notifico messaggio");
				throw new NotFoundException("utente non trovato costruendo notifica messaggio");
			}
			data.setTitle(senderOpt.get().getUsername());
			data.setMessage(chatMessage.getContent());
			firebaseNotification.setData(data);
			List<Device> devices = deviceDao.findAll();
			List<String> tokens = devices.stream().map(Device::getToken).collect(Collectors.toList());
			firebaseNotification.setRegistration_ids(tokens);
			firebaseNotification.setPriority("high");
			String fbNotification = objectmapper.writeValueAsString(firebaseNotification);
			
			//Chiamata servizio firebase
			try {
				//Chiamata post al servizio cloud messaging firebase
				HttpEntity<String> request = new HttpEntity<>(fbNotification);
				RestTemplate restTemplate = new RestTemplate();
				ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
				interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + serverKey));
				interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
				restTemplate.setInterceptors(interceptors);
				String firebaseResponse = restTemplate.postForObject(apiUrl, request, String.class);		
				log.info(firebaseResponse);
			} catch (Exception e) {
				log.error("Errore nell'invio della notifica", e);
				throw e;
			} 
		}
	}
	
	@Override
	public String delete(String messageId) throws Exception {
		Optional<ChatMessage> messageOpt = messageDao.findById(messageId);
		if(!messageOpt.isPresent()){
			throw new NotFoundException("Messaggio non presente");
		}
		messageDao.deleteById(messageId);
		return messageId;
	}
	
	public List<OutgoingMessage> load(String userID, String roomID) throws Exception {
		// recupera tutti i messaggi precedenti di una stanza e mandali
		Optional<Room> roomOpt = roomDao.findById(roomID);
		Optional<User> userOpt = userDao.findById(userID);
		
		if (!userOpt.isPresent() || !roomOpt.isPresent()) {
			log.error("impossibile trovare stanza o utente ".concat(roomID).concat(" ".concat(userID)));
			throw new Exception("impossibile trovare stanza o utente ".concat(roomID).concat(" ".concat(userID)));
		}
		ChatMessage chatMessage = new ChatMessage();
		chatMessage.setRoomID(roomID);
		List<ChatMessage> messages = messageDao.findAll(Example.of(chatMessage));
		
		return messages.stream()
		.map(message -> {
			OutgoingMessage outgoingMessage = new OutgoingMessage();
			BeanUtils.copyProperties(message, outgoingMessage);
			outgoingMessage.setSenderUsername(userDao.findById(message.getSenderID()).get().getUsername());
			outgoingMessage.setRoomTitle(roomOpt.get().getTitle());
			return outgoingMessage;
		}).collect(Collectors.toList());
	}

}
