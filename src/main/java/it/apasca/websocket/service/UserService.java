/**
 * 
 */
package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;

/**
 * @author a.pasca
 *
 */
public interface UserService {
	
	public String registraUtente(UserDto user) throws Exception;

}
