/**
 * 
 */
package it.apasca.websocket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.apasca.websocket.model.User;

/**
 * @author a.pasca
 *
 */
public interface UserDao extends MongoRepository<User, String> {
}
