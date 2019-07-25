/**
 * 
 */
package it.apasca.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author a.pasca
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "device")
public class Device {
	@Id
	private String id;
	private String token;
	private Date registerDate;
	
}
