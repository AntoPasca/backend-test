/**
 * 
 */
package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;

import java.util.List;

/**
 * @author a.pasca
 *
 */
public interface UserService {
	
	public String registraUtente(UserDto user) throws Exception;
	User createUser(User user);

	void deleteUser(String roomId);

	List<User> getUsers(User user);

}
