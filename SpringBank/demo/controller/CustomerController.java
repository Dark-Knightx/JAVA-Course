package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.CustomerAccount;
import com.example.demo.service.BankService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class CustomerController {

	@Autowired
	BankService bankService;
	
	@PostMapping("/addCustomer")
	public void saveCustomer(@RequestBody CustomerAccount customerAccount) {
		bankService.saveCustomer(customerAccount);
	}
	
}
