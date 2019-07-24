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

	@Autowired
	RoomService roomService;

	@Autowired
	ObjectMapper objectMapper;
	
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

	// Servizi inerenti una stanza
	@ApiOperation("Crea una stanza")
	@PostMapping("/stanza")
	public String createRoom(@RequestBody Conversation conversation) throws Exception{
		return roomService.createRoom(conversation);
	}

	@ApiOperation("Elimina una stanza")
	@DeleteMapping("/stanza/{roomId}")
	public void deleteRoom(@PathVariable String roomId) throws Exception {
		roomService.deleteRoom(roomId);
	}

	@ApiOperation("recupera informazioni di una stanza per id")
	@GetMapping("/stanza/{roomId}")
	public Conversation getRoom(@PathVariable() String roomId) throws Exception{
		return roomService.getRoom(roomId);
	}

	@ApiOperation("recupera informazioni stanze")
	@GetMapping("/stanza")
	public List<Conversation> getRooms(@RequestParam() String params) throws Exception{
		Conversation conversation = objectMapper.readValue(params, Conversation.class);
		return roomService.getRooms(conversation);
	}

}
