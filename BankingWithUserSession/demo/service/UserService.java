package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository userRepository;
	
	public boolean saveUser(User user) {
		User userExist = userRepository.findByPanId(user.getPanId());
		
		if (userExist == null) {
			userRepository.save(user);
			return true;
		}else {
			return false;
		}
	}
}
