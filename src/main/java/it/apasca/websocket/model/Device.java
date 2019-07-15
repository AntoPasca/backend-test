/**
 * 
 */
package it.apasca.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

import it.apasca.websocket.model.ChatMessage.MessageType;
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
public class Device {
	@Id
	private String id;
	private String token;
	private Date registerDate;
	
}
