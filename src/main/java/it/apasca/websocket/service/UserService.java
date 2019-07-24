package it.apasca.websocket.service;

import it.apasca.websocket.model.Conversation;
import it.apasca.websocket.model.User;

import java.util.List;

public interface UserService {

    User createUser(User user);

    void deleteUser(String roomId);

    List<User> getUsers(User user);
}
