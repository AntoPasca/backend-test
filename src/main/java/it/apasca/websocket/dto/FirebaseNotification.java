/**
 * 
 */
package it.apasca.websocket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author a.pasca
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FirebaseNotification {
	private List<String> registration_ids;
	private String priority;
	private Data data;
}
