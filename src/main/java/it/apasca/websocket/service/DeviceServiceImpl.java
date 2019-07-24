/**
 * 
 */
package it.apasca.websocket.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import it.apasca.websocket.dao.DeviceDao;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.model.Device;

/**
 * @author a.pasca
 *
 */
@Service
public class DeviceServiceImpl implements DeviceService{
	
	private static final Logger logger = LoggerFactory.getLogger(DeviceServiceImpl.class);

	@Autowired
	private DeviceDao deviceDao;
	
	@Override
	public String registerToken(DeviceRegistration deviceRegistration) throws Exception {
		Device device = new Device();
		device.setToken(deviceRegistration.getToken());
		device.setRegisterDate(new Date());
		
		Optional<Device> deviceExample = deviceDao.findOne(Example.of(device));
		
		if(!deviceExample.isPresent()) {
			deviceDao.save(device);
			return device.getId();
		}
		else {
			logger.error("Dispositivo con token: " + deviceRegistration.getToken() + " già presente");
			throw new Exception("Errore nella registrazione del token");
		}
	}
	
}
