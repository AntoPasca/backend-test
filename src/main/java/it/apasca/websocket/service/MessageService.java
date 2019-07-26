/**
 * 
 */
package it.apasca.websocket.service;

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
}
