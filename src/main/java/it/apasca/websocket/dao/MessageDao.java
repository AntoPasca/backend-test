/**
 * 
 */
package it.apasca.websocket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.apasca.websocket.model.ChatMessage;

/**
 * @author B.Conetta
 *
 */
public interface MessageDao  extends MongoRepository<ChatMessage, String> {

}
