package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ATMcontroller {

	@Autowired
	ATMservice atmService;
	
	@PostMapping("/addUser")
	public void add(@RequestBody ATMmodal user) {
		System.out.println("Adding user: " + user);
		atmService.addUser(user);
	}
	
	@PutMapping("/deposit/{accountNo},{amount}")
	public void deposit(@PathVariable int accountNo, @PathVariable int amount) {
		atmService.deposit(accountNo,amount);
	}

	@PutMapping("/withdraw/{accountNo},{pin},{amount}")
	public void withdraw(@PathVariable int accountNo,@PathVariable int pin, @PathVariable int amount) {
		atmService.withdraw(accountNo,pin,amount);
	}
}
