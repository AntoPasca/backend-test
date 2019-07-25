/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.ChatMessage;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface MessageDao  extends MongoRepository<ChatMessage, String> {

}
