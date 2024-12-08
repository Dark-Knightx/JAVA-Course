package com.example.demo.service;

import com.example.demo.modal.Account;
import com.example.demo.modal.Customer;
import com.example.demo.modal.OTPgenerator;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.OtpRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private OtpRepo otpRepo;

    public void genotp(String email) {
        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        OTPgenerator otpGenerator = new OTPgenerator();
        otpGenerator.setEmail(email);
        otpGenerator.setOtp(String.valueOf((int) (Math.random() * 90000) + 10000)); // Generate random 5-digit OTP
        otpRepo.save(otpGenerator);
    }




    public boolean saveCustomer(Customer customer) {
        // Check if username already exists
        if (isUsernameTaken(customer.getUsername())) {
            return false; // Username already exists
        }

        // Check if mobile number is already associated with another customer
        if (isMobileTaken(customer.getMobile())) {
            return false; // Mobile number already exists
        }

        // Generate and save an account for the customer
        Account account = createNewAccount();
        account.setIfsc("SBI2324");
        account.setAcNo(12135445);
        accountRepo.save(account);

        // Assign account to the customer
        customer.setPan(12365);
        customer.setAcNo(account.getAcNo());

        // Save the customer
        customerRepo.save(customer);
        return true;
    }

    /**
     * Checks if a username is already taken.
     * @param username The username to check.
     * @return true if the username exists, false otherwise.
     */
    public boolean isUsernameTaken(String username) {
        return customerRepo.findByUsername(username).isPresent();
    }

    /**
     * Checks if a mobile number is already associated with another customer.
     * @param mobile The mobile number to check.
     * @return true if the mobile number exists, false otherwise.
     */
    public boolean isMobileTaken(String mobile) {
        return customerRepo.findByMobile(mobile) != null;
    }

    /**
     * Creates a new account with a random account number and IFSC code.
     * @return The newly created account.
     */
    private Account createNewAccount() {
        Account account = new Account();
        account.setAcNo((int) (Math.random() * 1_000_000_00)); // Generate random 8-digit account number
        account.setIfsc("SBI" + (int) (Math.random() * 1_000)); // Generate random 4-digit IFSC code
        return account;
    }
}
