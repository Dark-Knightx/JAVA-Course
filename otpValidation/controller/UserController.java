package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;
	@Autowired
	private UserService userService;

	@Autowired
	private TransactionRepo transactionRepo;

	@GetMapping("/dashboard")
	public String viewForm() {
		return "userDashboard"; // Ensure this view exists
	}

	@GetMapping("/tr")
	public String tr(){
		return "transfer";
	}
	@GetMapping("/transfer")
	public String transfer(@ModelAttribute Transaction transaction){
		if (userService.sendAmount(transaction)){
			return "userDashboard";
		}else {
			return "not";
		}
	}

	@GetMapping("/dpage")
	public String dpage(){
		return "deposit";
	}
	@GetMapping("/deposit")
	public String depo(@ModelAttribute Transaction transaction){

		if (userService.deposit(transaction)){
			return "userDashboard";
		}else {
			return "not";
		}
	}

	@GetMapping("/wpage")
	public String wpage(){
		return "withdraw";
	}
	@GetMapping("/withdraw")
	public String withdraw(@ModelAttribute Transaction transaction){
		if (userService.withdraw(transaction)){
			return "userDashboard";
		}else {
			return "not";
		}
	}

	@GetMapping("/updateCustomer")
	public String updateCustomer(Model model) {
		// Fetch the customer object from the database
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Fetch the Customer details using the username
		String username = userDetails.getUsername();
		Customer customer = customerRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Customer details not found"));

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String dobFormatted = customer.getDob().format(formatter);

		// Add the customer object to the model
		model.addAttribute("customer", customer);
		model.addAttribute("dobFormatted", dobFormatted);
		System.out.println(customer.getDob());
		System.out.println(customer.getDob().toString());
		// Return the name of the Thymeleaf template
		return "updateProfile";
	}


	@GetMapping("/details")
	public String viewUserDetails(Model model) {
		// Get the authenticated user
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		// Fetch the Customer details using the username
		String username = userDetails.getUsername();
		Customer customer = customerRepo.findByUsername(username)
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

		// Fetch the Customer to get the account number
		String username = userDetails.getUsername();
		Customer customer = customerRepo.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Customer details not found"));

		int userAccount = customer.getAcNo();

		// Fetch transactions for both sender and receiver
		HashSet<Transaction> senderTransactions = transactionRepo.findBySenderAccount(userAccount);
		List<Transaction> receiverTransactions = transactionRepo.findByReceiverAccount(userAccount);

		// Mark sender transactions as negative (except for deposits)
		for (Transaction transaction : senderTransactions) {
			if (transaction.getReceiverAccount() != userAccount || transaction.getTransactionType().equals("Withdrawn")) {
				// Only mark as negative if it's not a deposit
				transaction.setAmount(transaction.getAmount() * -1);
			}
		}

		// Combine both lists
		senderTransactions.addAll(receiverTransactions);

		// Add the transactions to the model
		model.addAttribute("transactionList", senderTransactions);

		// Return the transactions view
		return "transactions";
	}

}
