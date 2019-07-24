package it.apasca.websocket.service;

import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public String createUser(User user) {
        User userSaved = userDao.save(user);
        return userSaved.getId();
    }

    @Override
    public void deleteUser(String userID) {
        userDao.deleteById(userID);
    }

    @Override
    public List<User> getUsers(User user) {
        return userDao.findAll(Example.of(user));
    }
}
