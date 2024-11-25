package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private TransactionRepo transactionRepo;

	@GetMapping("/dashboard")
	public String viewForm() {
		return "userDashboard";
	}

	@GetMapping("/details")
	public String viewUserDetails(Model model) {
		// Get the authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Fetch the Customer details using the panId
		String username = userDetails.getUsername();
		Customer customer = customerRepo.findByPan(getPanIdFromUsername(username))
				.orElseThrow(() -> new RuntimeException("Customer details not found"));

		// Add the customer to the model
		model.addAttribute("customer", customer);

		// Return the user details view
		return "userDetails";
	}

	@GetMapping("/viewTransactions")
	public String viewTransactions(Model model) {
		// Get the authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Log the username
		System.out.println("Authenticated username: " + userDetails.getUsername());

		// Fetch the Customer to get the account number
		String username = userDetails.getUsername();
		int panId = getPanIdFromUsername(username);
		System.out.println("Extracted PAN ID: " + panId);

		Customer customer = customerRepo.findByPan(panId)
				.orElseThrow(() -> new RuntimeException("Customer details not found"));

		// Log the fetched customer details
		System.out.println("Fetched Customer: " + customer);

		// Fetch transactions based on the account number
		List<Transaction> transactionList = transactionRepo.findBySenderAccount(customer.getAcNo());
		System.out.println("Fetched Transactions: " + transactionList);

		// Add the transactions to the model
		model.addAttribute("transactionList", transactionList);

		// Return the transactions view
		return "transactions";
	}


	private int getPanIdFromUsername(String username) {
		// Use the CustomerRepo to fetch the Customer by username
		Customer customer = customerRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("User not found with username: " + username));

		// Return the PAN ID of the Customer
		return customer.getPan();
	}

}
