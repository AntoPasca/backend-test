package it.apasca.websocket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import it.apasca.websocket.model.User;
import it.apasca.websocket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @ApiOperation("Crea un utente")
    @PostMapping("/utente")
    public String createUser(@RequestBody User user) throws Exception{
        return userService.createUser(user);
    }

    @ApiOperation("Elimina un utente")
    @DeleteMapping("/utente/{userId}")
    public void deleteUser(@PathVariable String userId) throws Exception {
        userService.deleteUser(userId);
    }

    @ApiOperation("recupera informazioni utenti")
    @GetMapping("/utente")
    public List<User> getUsers(@RequestParam() String params) throws Exception{
        User user = objectMapper.readValue(params, User.class);
        return userService.getUsers(user);
    }

}
