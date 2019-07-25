package it.apasca.websocket.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Conversation;
import it.apasca.websocket.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stanza")
public class RoomController {

    @Autowired
    RoomService roomService;

    @Autowired
    ObjectMapper objectMapper;

    @ApiOperation("Crea una stanza")
    @PostMapping("")
    public Conversation createRoom(@RequestBody Conversation conversation) throws Exception {
        return roomService.createRoom(conversation);
    }

    @ApiOperation("Elimina una stanza")
    @DeleteMapping("/{roomId}")
    public void deleteRoom(@PathVariable String roomId) throws Exception {
        roomService.deleteRoom(roomId);
    }

    @ApiOperation("recupera informazioni di una stanza per id")
    @GetMapping("/{roomId}")
    public Conversation getRoom(@PathVariable() String roomId) throws Exception {
        return roomService.getRoom(roomId);
    }

    @ApiOperation("recupera informazioni stanze")
    @GetMapping("")
    public List<Conversation> getRooms(@RequestParam() String params) throws Exception {
        Conversation conversation = objectMapper.readValue(params, Conversation.class);
        return roomService.getRooms(conversation);
    }

    // TODO: implementare paginazione qui
    @ApiOperation("recupera  messaggi di una stanza")
    @GetMapping("/{roomId}/message")
    public List<ChatMessage> getMessages(@PathVariable() String roomId , @RequestParam() String params) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(params, ChatMessage.class);
        return roomService.getMessages(roomId, chatMessage);
    }
}
