/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.Conversation;
import it.apasca.websocket.model.Partecipants;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface PartecipantsDao extends MongoRepository<Partecipants, String> {

}
