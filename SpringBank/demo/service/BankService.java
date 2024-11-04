package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.Account;
import com.example.demo.modal.Customer;
import com.example.demo.modal.CustomerAccount;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.CustomerRepo;

@Service
public class BankService {

	@Autowired
	AccountRepo accountRepo;
	@Autowired
	CustomerRepo customerRepo;
	
	public void saveCustomer(CustomerAccount customerAccount) {
		Account account = customerAccount.getAccount();
		accountRepo.save(account);
		
		Customer customer = customerAccount.getCustomer();
		customer.setAcNo(account.getAcNo());
		customerRepo.save(customer);
	}
}
