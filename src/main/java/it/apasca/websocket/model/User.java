/**
 * 
 */
package it.apasca.websocket.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

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
public class User {
	@Id
	private String id;
	private String nome;
	private String cognome;
	private String email;
	private String username;
	private String password;
	private Boolean isOnline;
	private Date registerDate;
	private Role role;
	
	public enum Role {
        ADMIN,
        USER
    }
}