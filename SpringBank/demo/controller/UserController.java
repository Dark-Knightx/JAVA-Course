package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@PostMapping("/saveUser")
	public ResponseEntity<String> saveUser(@RequestBody User user){
		if (userService.saveUser(user)) {
			return new ResponseEntity<String>("User details saved", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("PanId already Exist..", HttpStatus.OK);
		}
	}
}
