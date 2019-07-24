package it.apasca.websocket.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
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
    @PostMapping("/stanza")
    public Conversation createRoom(@RequestBody Conversation conversation) throws Exception{
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
