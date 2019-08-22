/**
 * 
 */
package it.apasca.websocket.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;
import it.apasca.websocket.model.User.Role;
import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author a.pasca
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
    private SimpMessageSendingOperations messagingTemplate;
	
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
			log.error("Utente " + user.getUsername() + " gia' registrato");
			throw new Exception("ERRREG");
		}
	}

	@Override
	public List<UserDto> getUsers(UserDto userExample) throws Exception {
		User userModel = new User();
		List<UserDto> users = new ArrayList<>();

		BeanUtils.copyProperties(userExample, userModel);
		List<User> usersToReturn = userDao.findAll(Example.of(userModel));
		usersToReturn.stream().forEach(user ->  {
			UserDto userDto = new UserDto();
			BeanUtils.copyProperties(user , userDto);
			users.add(userDto);
		});

		return users;
	}

	@Override
	public UserDto login(UserDto user) throws Exception {
		User userModel = new User();
		userModel.setUsername(user.getUsername());
		Optional<User> userExample = userDao.findOne(Example.of(userModel));
		if(userExample.isPresent()) {
			// se utente è già loggato. non permettere di entrare
			if (userExample.get().getIsOnline()) {
				log.error("Utente ".concat(user.getUsername()).concat(" gia' loggato"));
				throw new Exception("ERRLOGIN");
			}
			if(bCryptPasswordEncoder.matches(user.getPassword(), userExample.get().getPassword())) {
				UserDto userOut = new UserDto();
				BeanUtils.copyProperties(userExample.get(), userOut);
				return userOut;
			}
			else {
				log.error("Password " + user.getPassword() + " non corrispondente");
				throw new Exception("ERRPSW");
			}
		}
		else {
			log.error("Utente " + user.getUsername() + " non presente");
			throw new Exception("ERRUSR");
		}
	}

	@Override
	public void setOnline(String userID) throws Exception {
		Optional<User> userOpt = userDao.findById(userID);
		
		if(!userOpt.isPresent()) {
			log.error("utente id non trovato! ".concat(userID));
    		throw new NotFoundException("utente id non trovato! ".concat(userID));
		}
		User user = userOpt.get();
		user.setIsOnline(true);
		user.setLastAccess(null);
		userDao.save(user);
		// TODO: in realtà serve invitare molto meno 
		messagingTemplate.convertAndSend("/topic/online", user);
	}

	@Override
	public void setOffline(String userID) throws Exception {
		Optional<User> userOpt = userDao.findById(userID);
		
		if(!userOpt.isPresent()) {
			log.error("utente id non trovato! ".concat(userID));
    		throw new NotFoundException("utente id non trovato! ".concat(userID));
		}
		User user = userOpt.get();
		
		user.setIsOnline(false);
		user.setLastAccess(new Date());
		
		userDao.save(user);
		// TODO: in realtà serve invitare molto meno 
		messagingTemplate.convertAndSend("/topic/online", user);
	}


}
