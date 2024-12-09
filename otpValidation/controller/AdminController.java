package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.modal.OTPgenerator;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.OtpRepo;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {



    @Autowired
    private AdminService adminService;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private OtpRepo otpRepo;

    @PostMapping("/otp")
    public String generateOtp(@RequestParam String email, Model model) {
        if (email == null || email.isEmpty()) {
            model.addAttribute("customer", new Customer());
            model.addAttribute("otpError", "Email is required to generate OTP");
            return "register"; // Return to the registration page with an error
        }

        // Generate OTP without email validation
        adminService.genotp(email);
        OTPgenerator otPgenerator = otpRepo.findByEmail(email);

        // Success message
        model.addAttribute("otpSuccess", "OTP sent to your email");
        model.addAttribute("customer", new Customer());
        model.addAttribute("email",email);
        model.addAttribute("otpClass",otPgenerator);
        return "register"; // Return to the same page with success message
    }



    @GetMapping("/dashboard")
    public String viewForm(){
        return "adminDashboard";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(
            @ModelAttribute("customer") Customer customer,
            @RequestParam("otp") String otp,@RequestParam String email,
            Model model) {

        boolean hasErrors = false;

        // Validate username uniqueness
        if (adminService.isUsernameTaken(customer.getUsername())) {
            model.addAttribute("usernameError", "Username is already taken");
            hasErrors = true;
        }

        // Validate mobile number uniqueness
        if (adminService.isMobileTaken(customer.getMobile())) {
            model.addAttribute("mobileError", "Mobile number is already taken");
            hasErrors = true;
        }

        // Validate date of birth
        if (customer.getDob() == null) {
            model.addAttribute("dobError", "Date of birth is required");
            hasErrors = true;
        } else {
            LocalDate today = LocalDate.now();
            Period age = Period.between(customer.getDob(), today);

            if (age.getYears() < 18) {
                model.addAttribute("dobError", "You must be at least 18 years old");
                hasErrors = true;
            }
        }
        customer.setEmail(email);
        // Validate OTP
        OTPgenerator otpRecord = otpRepo.findByEmail(customer.getEmail());
        if (otpRecord == null || !otpRecord.getOtp().equals(otp)) {
            model.addAttribute("otpError", "Invalid OTP");
            model.addAttribute("email",email);
            hasErrors = true;
        }

        if (hasErrors) {
            return "register";
        }

        // Save customer logic if everything is fine
        if (adminService.saveCustomer(customer)) {
            model.addAttribute("success", "Customer successfully saved!");
            return "adminDashboard";
        } else {
            model.addAttribute("otpError", "Error saving customer");
            return "register";
        }
    }


    @GetMapping("/transactionPage")
    public String transactionPage(){
        return "transactionPage";
    }
    @GetMapping("/viewTransactions")
    public String viewTransactions(@RequestParam int senderAccount,Model model){
        HashSet<Transaction> transactionList = transactionRepo.findAllBySenderAccount(senderAccount);
        ArrayList<Transaction> transactionsList2 = transactionRepo.findAllByReceiverAccount(senderAccount);
        transactionList.addAll(transactionsList2);
        if (!transactionList.isEmpty()) {
            for (Transaction transaction : transactionList){
                if (transaction.getSenderAccount() == senderAccount && transaction.getReceiverAccount() != senderAccount){
                    transaction.setAmount(transaction.getAmount() * -1);
                }
            }
            model.addAttribute("transactionList",transactionList);
            return "transactions";
        }else {
            return "works";
        }
    }

    @GetMapping("/reg")
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new Customer()); // Add a new Customer object to the model
        return "register";
    }
}
