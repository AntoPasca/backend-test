/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author a.pasca
 *
 */
public interface UserDao extends MongoRepository<User, String> {
}
