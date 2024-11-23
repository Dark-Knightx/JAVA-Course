package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private CustomerRepo customerRepo;

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

	// private int getPanIdFromUsername(String username) {
	// 	return 12345; 
	// }
}
