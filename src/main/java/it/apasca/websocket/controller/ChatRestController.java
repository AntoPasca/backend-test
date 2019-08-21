/**
 * 
 */
package it.apasca.websocket.controller;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.controller.ChatController.IncomingMessage;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.dto.OutgoingMessage;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.service.DeviceService;
import it.apasca.websocket.service.MessageService;
import it.apasca.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

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
	
	@ApiOperation("recupera  messaggi di una stanza")
    @DeleteMapping("/{messageId}")
    public String deleteMessage(@PathVariable() String messageId) throws Exception {
        return messageService.deleteMessage(messageId);
    }
	
    // TODO: implementare paginazione qui
    @ApiOperation("recupera  messaggi di una stanza")
    @GetMapping("/messaggio")
    public List<OutgoingMessage> getMessages(@RequestParam() String params) throws Exception {
    	IncomingMessage incomingMessage = objectMapper.readValue(params, IncomingMessage.class);
        return messageService.load(incomingMessage.getUserID(), incomingMessage.getRoomID());
    }
}
