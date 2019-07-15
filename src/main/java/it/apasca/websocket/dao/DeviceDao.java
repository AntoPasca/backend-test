/**
 * 
 */
package it.apasca.websocket.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.apasca.websocket.model.Device;

/**
 * @author a.pasca
 *
 */
public interface DeviceDao extends MongoRepository<Device, String> {

}
