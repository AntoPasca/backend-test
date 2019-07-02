/**
 * 
 */
package it.apasca.websocket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import it.apasca.websocket.model.ChatMessage;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface MessageDao  extends MongoRepository<ChatMessage, String> {

}
