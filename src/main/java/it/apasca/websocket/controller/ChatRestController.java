/**
 * 
 */
package it.apasca.websocket.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import it.apasca.websocket.model.Conversation;
import it.apasca.websocket.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.DeviceRegistration;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.service.DeviceService;
import it.apasca.websocket.service.MessageService;

import javax.websocket.server.PathParam;

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


}
