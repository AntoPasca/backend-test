/**
 * 
 */
package it.apasca.websocket.service;

import it.apasca.websocket.dao.DeviceDao;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.model.Device;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @author a.pasca
 *
 */
@Slf4j
@Service
public class DeviceServiceImpl implements DeviceService{
	

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
			log.error("Dispositivo con token: " + deviceRegistration.getToken() + " già presente");
			throw new Exception("Errore nella registrazione del token");
		}
	}
	
}
