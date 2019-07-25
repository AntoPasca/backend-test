/**
 * 
 */
package it.apasca.websocket.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;
import it.apasca.websocket.model.User.Role;

/**
 * @author a.pasca
 *
 */

@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public String registraUtente(UserDto user) throws Exception {
		User userModel = new User();
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		userModel.setUsername(user.getUsername());
		userModel.setEmail(user.getEmail());
		Optional<User> userExample = userDao.findOne(Example.of(userModel));
		if(!userExample.isPresent()) {
			userModel.setNome(user.getNome());
			userModel.setCognome(user.getCognome());
			userModel.setPassword(encodedPassword);
			userModel.setIsOnline(Boolean.FALSE);
			userModel.setRole(Role.USER);
			userModel.setRegisterDate(new Date());
			userDao.save(userModel);
			return userModel.getId();
		}
		else {
			logger.error("Utente " + user.getUsername() + " già registrato");
			throw new Exception("Errore nella registrazione dell'utente, email o username già presenti");
		}
	}

	@Override
	public User createUser(User user) {
		User userToSave = new User();
		BeanUtils.copyProperties(user , userToSave , "id");
		User userSaved = userDao.save(userToSave);
		return userSaved;
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
