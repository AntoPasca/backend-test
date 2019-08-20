/**
 * 
 */
package it.apasca.websocket.dto;

import it.apasca.websocket.model.User.Role;
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
public class UserDto {
	private String id;
	private String nome;
	private String cognome;
	private String email;
	private String username;
	private String password;
	private Role role;
}
