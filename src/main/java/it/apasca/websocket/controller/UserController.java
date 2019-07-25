package it.apasca.websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;
import it.apasca.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /*
    @ApiOperation("Crea un utente")
    @PostMapping("/")
    public User createUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }
    */

    @ApiOperation("recupera informazioni utenti")
    @GetMapping("/")
    public List<UserDto> getUsers(@RequestParam() String params) throws Exception {
        UserDto user = objectMapper.readValue(params, UserDto.class);
        return userService.getUsers(user);
    }

}