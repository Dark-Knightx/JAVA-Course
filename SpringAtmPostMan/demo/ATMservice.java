package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ATMservice {
	
	@Autowired
	ATMrepo atmRepo;
	
	public void addUser(ATMmodal user) {
		atmRepo.save(user);
	}
	
	public void deposit(int accountNo, int amount) {
		ATMmodal user = atmRepo.findByAccountNo(accountNo); // You'll need to create this method in the repository
	        if (user != null) {
	            // Update the amount
	            user.setAmount(user.getAmount() + amount);
	            atmRepo.save(user); 
	        } else {
	            System.out.println("User with account number " + accountNo + " not found.");
	        }
	}

	public void withdraw(int accountNo,int pin, int amount) {
		ATMmodal user = atmRepo.findByAccountNo(accountNo); // You'll need to create this method in the repository
	        if (user != null) {
	        	if (user.getPin()==pin) {
	        		if (user.getAmount()>=amount) {
	        			user.setAmount(user.getAmount() - amount);
	                    atmRepo.save(user); 
				}else {
						System.out.println("Insufficient Balance");
				}
			}else {
				System.out.println("WrongPin");
			}
	        } else {
	            System.out.println("User with account number " + accountNo + " not found.");
	        }
	}
}
