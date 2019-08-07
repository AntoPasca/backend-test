package it.apasca.websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/utente")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("Registra utente")
    @PostMapping
    public String registraUtente(@RequestBody UserDto user) throws Exception{
    	return userService.registraUtente(user);
    }

    @ApiOperation("Recupera informazioni utenti")
    @GetMapping("/")
    public List<UserDto> getUsers(@RequestParam() String params) throws Exception {
        UserDto user = objectMapper.readValue(params, UserDto.class);
        return userService.getUsers(user);
    }
    
    @ApiOperation("Login")
    @PostMapping("/login")
    public UserDto login(@RequestBody UserDto user) throws Exception {
    	return userService.login(user);
    }

}
