/**
 * 
 */
package it.apasca.websocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.service.MessageService;

/**
 * @author B.Conetta
 *	
 */
@RestController
@RequestMapping("")
public class ChatRestController {
	
	@Autowired
	MessageService messageService;
	
	@ApiOperation("Restituisce tutti i messaggi")
	@GetMapping
	public List<ChatMessage> loadMessage() throws Exception{
		return messageService.getMessage();
	}
}
