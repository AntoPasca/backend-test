/**
 * 
 */
package it.apasca.websocket.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.apasca.websocket.dao.DeviceDao;
import it.apasca.websocket.dao.MessageDao;
import it.apasca.websocket.dto.Data;
import it.apasca.websocket.dto.FirebaseNotification;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
	ObjectMapper objectmapper;
	
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

	@Override
	public void notify(ChatMessage chatMessage) throws Exception {
		FirebaseNotification firebaseNotification = new FirebaseNotification();
		Data data = new Data();
		data.setTitle(chatMessage.getSender().getUsername());
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
