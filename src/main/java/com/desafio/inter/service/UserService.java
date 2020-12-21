package com.desafio.inter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.inter.errorHandler.DataBaseOperationException;
import com.desafio.inter.errorHandler.FieldValidationException;
import com.desafio.inter.errorHandler.UserNotFoundException;
import com.desafio.inter.model.User;
import com.desafio.inter.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EncryptionService encryptionService;
	
	public User saveUser(User user, String publicKey) {
		checkRequiredsFields(user);
		
		try {
			user.setName(encryptionService.encrypt(user.getName(), publicKey));
			user.setEmail(encryptionService.encrypt(user.getEmail(), publicKey));
			return userRepository.save(user);
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while saving user.");
		}
	}
	
	public User editUser(User user, String publicKey) {
		checksEditUser(user);
		checkRequiredsFields(user);
		try {
			user.setName(encryptionService.encrypt(user.getName(), publicKey));
			user.setEmail(encryptionService.encrypt(user.getEmail(), publicKey));
			return userRepository.save(user);
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while editing user.");
		}
	}
	
	public User findById(Long userId) {
		checkUserId(userId);
		try {
			return userRepository.findById(userId).get();
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred while getting user.");
		}
		
	}
	
	public List<User> findAll(){
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred getting all users.");
		}
	}
	
	public Boolean deleteUser(Long userId) {
		checkUserId(userId);
		try {
			userRepository.deleteById(userId);
			return true;
		} catch (Exception e) {
			throw new DataBaseOperationException("An error occurred deleting user.");
		}
	}
	
	public void checkUserId(Long userId) {
		if(userId != null && !userRepository.findById(userId).isPresent()) {
			throw new UserNotFoundException(userId);
		}
	}
	
	private void checksEditUser(User user) {
		if(user.getId() == null) {
			throw new FieldValidationException("Id user is a field required.");
		} else {
			checkUserId(user.getId());
		}
	}
	
	private void checkRequiredsFields(User user) {
		if(user.getName() == null || user.getName().length() == 0 ||
		   user.getEmail() == null || user.getEmail().length() == 0) {
			throw new FieldValidationException("Name and e-mail are requireds fields.");
		}
	}
}
