/**
 * 
 */
package it.apasca.websocket.service;

import java.util.List;

import it.apasca.websocket.dto.OutgoingMessage;
import it.apasca.websocket.model.ChatMessage;

/**
 * @author B.Conetta
 *	classe usata per gestire i messaggi della chat
 */
public interface MessageService {

	/*
	 * Salva un messaggio sul db
	 * 
	 */
	public String save(ChatMessage chatMessage);
	public void notify(ChatMessage chatMessage) throws Exception;
	public List<OutgoingMessage> load(String userID, String roomID) throws Exception;
	public String delete(String id) throws Exception;
}
