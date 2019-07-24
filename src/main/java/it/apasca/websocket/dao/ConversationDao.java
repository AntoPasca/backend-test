/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.Conversation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface ConversationDao extends MongoRepository<Conversation, String> {

}
