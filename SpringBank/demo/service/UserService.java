package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;
	
	public boolean saveUser(User user) {
		User userExist = userRepo.findByPanId(user.getPanId());
		
		if (userExist == null) {
			userRepo.save(user);
			return true;
		}else {
			return false;
		}
	}
}
