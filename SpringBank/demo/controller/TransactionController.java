package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.modal.Transaction;
import com.example.demo.service.TransactionService;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

	@Autowired
	TransactionService  transactionService;
	
	@PostMapping("sendAmount")
	public ResponseEntity<String> sendAmount(@RequestBody Transaction transaction){
		if (transactionService.sendAmount(transaction)) {
			return new ResponseEntity<String>("Transaction Successful", HttpStatus.OK);
		}else {
			return new ResponseEntity<String>("Transaction UnSuccessful", HttpStatus.OK);
		}
	}
}
