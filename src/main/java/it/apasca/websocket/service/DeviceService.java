package it.apasca.websocket.service;

import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.dto.UserDto;

/**
 * @author a.pasca
 *	
 */
public interface DeviceService {
	
	public String registerToken(DeviceRegistration deviceRegistration) throws Exception;


}
