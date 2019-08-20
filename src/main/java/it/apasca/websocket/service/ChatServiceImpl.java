package it.apasca.websocket.service;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.apasca.websocket.controller.ChatController.IncomingMessage;
import it.apasca.websocket.dao.DeviceDao;
import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.dao.RoomDao;
import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.dto.Data;
import it.apasca.websocket.dto.FirebaseNotification;
import it.apasca.websocket.dto.OutgoingMessage;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.ChatMessage.MessageType;
import it.apasca.websocket.model.Device;
import it.apasca.websocket.model.Room;
import it.apasca.websocket.model.User;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private UserDao userDao;
	@Autowired
	private RoomDao roomDao;
	@Autowired
	private MessageDao messageDao;
	@Autowired
	private DeviceDao deviceDao;
	
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
	@Value("${firebase.server.key}")
	private String serverKey;
	@Value("${firebase.api.url}")
	private String apiUrl;
	
	
	@Override
	public void join(String userID, String roomID) throws Exception {
		
		Optional<User> userOpt = userDao.findById(userID);
		Optional<Room> roomOpt = roomDao.findById(roomID);
		
		if (userOpt.isPresent() && roomOpt.isPresent()) {
			OutgoingMessage outgoingMessage = new OutgoingMessage();
			outgoingMessage.setRoomTitle(roomOpt.get().getTitle());
			outgoingMessage.setSenderUsername(userOpt.get().getUsername());
			outgoingMessage.setSendTime(new Date());
			outgoingMessage.setType(MessageType.JOIN);
			outgoingMessage.setContent(MessageFormat.format("{0} {1} join {2}", userOpt.get().getNome() ,userOpt.get().getCognome() , roomOpt.get().getTitle()));
	    	String urlSendTo = "/topic/".concat(roomOpt.get().getTitle());
	    	messagingTemplate.convertAndSend(urlSendTo, outgoingMessage);
	    	log.debug("user ".concat(outgoingMessage.getSenderUsername()).concat(" join room ").concat(outgoingMessage.getRoomTitle()));
		} else {
			log.error("impossibile trovare stanza o utente");
			throw new Exception("impossibile trovare stanza o utente");
		}
	}
	
	@Override
	public void leave(String userID, String roomID) throws Exception {
		Optional<User> userOpt = userDao.findById(userID);
		Optional<Room> roomOpt = roomDao.findById(roomID);
		
		if (userOpt.isPresent() && roomOpt.isPresent()) {
			OutgoingMessage outgoingMessage = new OutgoingMessage();
			outgoingMessage.setRoomTitle(roomOpt.get().getTitle());
			outgoingMessage.setSenderUsername(userOpt.get().getUsername());
			outgoingMessage.setSendTime(new Date());
			outgoingMessage.setType(MessageType.LEAVE);
			outgoingMessage.setContent(MessageFormat.format("{0} {1} leave {2}", userOpt.get().getNome() ,userOpt.get().getCognome() , roomOpt.get().getTitle()));
	    	String urlSendTo = "/topic/".concat(roomOpt.get().getTitle());
	    	messagingTemplate.convertAndSend(urlSendTo, outgoingMessage);
	    	log.debug("user ".concat(outgoingMessage.getSenderUsername()).concat(" leave room ").concat(outgoingMessage.getRoomTitle()));
		} else {
			log.error("impossibile trovare stanza o utente");
			throw new Exception("impossibile trovare stanza o utente");
		}
	}
	
	@Override
	public void send(IncomingMessage incomingMessage) throws Exception {
		OutgoingMessage outgoingMessage = new OutgoingMessage();
		
		Optional<Room> roomOpt = roomDao.findById(incomingMessage.getRoomID());
		Optional<User> userOpt = userDao.findById(incomingMessage.getUserID());
		// valida messaggio
		if (incomingMessage.getContent().isEmpty()) {
			throw new IllegalArgumentException("nessun contenuto trovato per il messaggio");
		} else if (!userOpt.isPresent()) {
			throw new IllegalArgumentException(MessageFormat.format("nessun utente trovato con id {0}" , incomingMessage.getUserID()));
		} else if (!roomOpt.isPresent()) {
			throw new IllegalArgumentException(MessageFormat.format("nessuna stanza trovata con id {0}" , incomingMessage.getRoomID()));
		}
		
		// invia messaggio
		outgoingMessage.setContent(incomingMessage.getContent());
		outgoingMessage.setRoomTitle(roomOpt.get().getTitle());
		outgoingMessage.setSenderUsername(userOpt.get().getUsername());
		outgoingMessage.setSendTime(new Date());
		outgoingMessage.setType(MessageType.CHAT);
		String urlSendTo = "/topic/".concat(roomOpt.get().getTitle());
		
		// salva e notifica messaggio
		try {
			// utilizza il model
			ChatMessage chatMessage = new ChatMessage();
			BeanUtils.copyProperties(outgoingMessage, chatMessage);
			chatMessage.setRoomID(roomOpt.get().getId());
			chatMessage.setSenderID(userOpt.get().getId());
			chatMessage = messageDao.save(chatMessage);
			messagingTemplate.convertAndSend(urlSendTo, outgoingMessage);
	    	log.debug("user ".concat(outgoingMessage.getSenderUsername().concat(" send a message in room ").concat(outgoingMessage.getRoomTitle())));
		} catch (Exception e) {
			log.error("errore durante il salvataggio del messaggio" , e);
			throw new Exception("errore durante il salvataggio del messaggio - messaggio non inviato");
		}
	}

	
	// notifica tramite firebase
	private void notify(ChatMessage chatMessage) throws Exception { 
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
			String fbNotification = objectMapper.writeValueAsString(firebaseNotification);
			
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
	
}
