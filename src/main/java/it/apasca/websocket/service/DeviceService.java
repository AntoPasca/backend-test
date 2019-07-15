package it.apasca.websocket.service;

import it.apasca.websocket.dto.DeviceRegistration;

/**
 * @author a.pasca
 *	
 */
public interface DeviceService {
	
	public String registerToken(DeviceRegistration deviceRegistration) throws Exception;

}
