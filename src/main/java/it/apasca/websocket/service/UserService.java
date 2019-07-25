/**
 * 
 */
package it.apasca.websocket.service;

import it.apasca.websocket.dto.UserDto;

import java.util.List;

/**
 * @author a.pasca
 *
 */
public interface UserService {

	public String registraUtente(UserDto user) throws Exception;

	List<UserDto> getUsers(UserDto userExample) throws Exception;

	public UserDto login(UserDto user) throws Exception ;

}
