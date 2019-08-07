/**
 * 
 */
package it.apasca.websocket.controller;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.service.DeviceService;
import it.apasca.websocket.service.MessageService;
import it.apasca.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@ApiOperation("Registra il token del device")
	@PostMapping("/register")
	public String registerToken(@RequestBody DeviceRegistration deviceRegistration) throws Exception{
		return deviceService.registerToken(deviceRegistration);
	}

	@ApiOperation("Login")
	@PostMapping("/login")
	public String login(@RequestBody UserDto user) throws Exception{
		return userService.registraUtente(user);
	}
	
	@ApiOperation("recupera  messaggi di una stanza")
    @DeleteMapping("/{messageId}")
    public String deleteMessage(@PathVariable() String messageId) throws Exception {
        return messageService.deleteMessage(messageId);
    }
}
