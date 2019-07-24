/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author B.Conetta
 *
 */
@Repository
public interface UserDao extends MongoRepository<User, String> {

}
