package it.apasca.websocket.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.model.Room;
import it.apasca.websocket.service.RoomService;

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
}
