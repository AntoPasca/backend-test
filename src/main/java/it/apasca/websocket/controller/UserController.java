package it.apasca.websocket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.service.UserService;

@RestController
@RequestMapping("/utente")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("Registra utente")
    @PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
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
