/**
 * 
 */
package it.apasca.websocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.service.DeviceService;
import it.apasca.websocket.service.MessageService;
import it.apasca.websocket.service.UserService;

/**
 * @author B.Conetta
 *	
 */
@RestController
@RequestMapping("")
public class ChatRestController {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	UserService userService;
	
	@ApiOperation("Restituisce tutti i messaggi")
	@GetMapping
	public List<ChatMessage> loadMessage() throws Exception{
		return messageService.getMessage();
	}
	
	@ApiOperation("Registra il token del device")
	@PostMapping("/register")
	public String registerToken(@RequestBody DeviceRegistration deviceRegistration) throws Exception{
		return deviceService.registerToken(deviceRegistration);
	}
	
	@ApiOperation("Registra utente")
	@PostMapping("/registrautente")
	public String registraUtente(@RequestBody UserDto user) throws Exception{
		return userService.registraUtente(user);
	}
	
	@ApiOperation("Login")
	@PostMapping("/login")
	public String login(@RequestBody UserDto user) throws Exception{
		return userService.registraUtente(user);
	}
}
