package com.example.demo.modal;

import org.springframework.beans.factory.annotation.Autowired;

public class CustomerAccount {
	@Autowired
	private Customer customer;
	@Autowired
	private Account account;

	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}


}
