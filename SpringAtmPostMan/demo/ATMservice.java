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
            atmRepo.save(user); // Save the updated user back to the database
        } else {
            // Handle the case where the user is not found (optional)
            System.out.println("User with account number " + accountNo + " not found.");
        }
	}
	
}
