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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.apasca.websocket.dao.UserDao;
import it.apasca.websocket.dto.UserDto;
import it.apasca.websocket.model.User;
import it.apasca.websocket.model.User.Role;
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
		UserDto userDto = new UserDto();

		BeanUtils.copyProperties(userExample, userModel);
		List<User> usersToReturn = userDao.findAll(Example.of(userModel));
		usersToReturn.stream().forEach(user ->  {
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
	public void changeStatus(String userId) {
		// TODO Auto-generated method stub
		
	}

}
