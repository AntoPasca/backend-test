/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface RoomDao extends MongoRepository<Room, String> {

}
