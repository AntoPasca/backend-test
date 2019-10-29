/**
 * 
 */
package it.apasca.websocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.controller.ChatController.IncomingMessage;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.dto.OutgoingMessage;
import it.apasca.websocket.dto.UserDto;
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
	@Autowired
	ObjectMapper objectMapper;

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
	
    // TODO: implementare paginazione qui
    @ApiOperation("recupera  messaggi di una stanza")
    @GetMapping("/messaggio")
    public List<OutgoingMessage> getMessages(@RequestParam() String params) throws Exception {
    	IncomingMessage incomingMessage = objectMapper.readValue(params, IncomingMessage.class);
        return messageService.load(incomingMessage.getUserID(), incomingMessage.getRoomID());
    }
    
    @ApiOperation("cancella messaggio selezionato")
    @DeleteMapping("/messaggio/{id}")
    public String deleteMessage(@PathVariable() String id) throws Exception {
    	return messageService.delete(id);
    }
    
}
