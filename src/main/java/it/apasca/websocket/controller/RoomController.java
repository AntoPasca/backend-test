package it.apasca.websocket.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.model.ChatMessage;
import it.apasca.websocket.model.Room;
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
    public Room createRoom(@RequestBody Room room) throws Exception {
        return roomService.createRoom(room);
    }

    @ApiOperation("recupera informazioni di una stanza per id")
    @GetMapping("/{roomId}")
    public Room getRoom(@PathVariable() String roomId) throws Exception {
        return roomService.getRoom(roomId);
    }

    @ApiOperation("recupera informazioni stanze")
    @GetMapping("")
    public List<Room> getRooms(@RequestParam() String params) throws Exception {
        Room room = objectMapper.readValue(params, Room.class);
        return roomService.getRooms(room);
    }

    // TODO: implementare paginazione qui
    @ApiOperation("recupera  messaggi di una stanza")
    @GetMapping("/{roomId}/messaggio")
    public List<ChatMessage> getMessages(@PathVariable() String roomId , @RequestParam() String params) throws Exception {
        ChatMessage chatMessage = objectMapper.readValue(params, ChatMessage.class);
        return roomService.getMessages(roomId, chatMessage);
    }
}
