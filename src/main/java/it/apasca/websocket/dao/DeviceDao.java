/**
 * 
 */
package it.apasca.websocket.dao;

import it.apasca.websocket.model.Device;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author a.pasca
 *
 */
public interface DeviceDao extends MongoRepository<Device, String> {

}
