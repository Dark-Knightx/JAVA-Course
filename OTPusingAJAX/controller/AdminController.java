package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.modal.OTPgenerator;
import com.example.demo.modal.SaveCustomerRequest;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.OtpRepo;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.service.AdminService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
@Controller
@RequestMapping("/admin")
public class AdminController {


    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
    @Autowired
    private AdminService adminService;
    @Autowired
    private TransactionRepo transactionRepo;
    @Autowired
    private OtpRepo otpRepo;

    @PostConstruct
    public void init() {
        taskScheduler.initialize();
    }

    @PostMapping("/otp")
    public String generateOtp(@RequestBody Map<String, String> payload, Model model) {
        String email = payload.get("email"); // Extract email from JSON payload
        if (email == null || email.isEmpty()) {
            model.addAttribute("customer", new Customer());
            model.addAttribute("otpError", "Email is required to generate OTP");
            return "register"; // Return to the registration page with an error
        }

        OTPgenerator otPgenerator2 = otpRepo.findByEmail(email);
        if (otPgenerator2 == null) {
            adminService.genotp(email);
        } else {
            model.addAttribute("customer", new Customer());
            model.addAttribute("otpError", "Enter OTP");
            model.addAttribute("email", email);
            return "register";
        }

        // Generate OTP without email validation
        OTPgenerator otPgenerator = otpRepo.findByEmail(email);

        if (otPgenerator != null) {
            // Schedule OTP removal after 30 seconds
            taskScheduler.schedule(() -> otpRepo.delete(otPgenerator),
                    new Date(System.currentTimeMillis() + 45000));
        }

        // Success message
        model.addAttribute("otpSuccess", "OTP sent to your email");
        model.addAttribute("customer", new Customer());
        model.addAttribute("email", email);
        model.addAttribute("otpClass", otPgenerator);

        return "register";
    }




    @GetMapping("/dashboard")
    public String viewForm(){
        return "adminDashboard";
    }

    @PostMapping("/saveCustomer")
    public String saveCustomer(
            @RequestBody SaveCustomerRequest request,
            Model model) {

        Customer customer = request.getCustomer();
        String otp = request.getOtp();
        String email = request.getEmail();
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
            model.addAttribute("email", email);
            hasErrors = true;
        }

        if (hasErrors) {
            model.addAttribute("email", email);
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


//    @PostMapping("/saveCustomer")
//    public String saveCustomer(
//            @ModelAttribute("customer") Customer customer,
//            @RequestParam("otp") String otp,@RequestParam String email,
//            Model model) {
//
//        boolean hasErrors = false;
//
//        // Validate username uniqueness
//        if (adminService.isUsernameTaken(customer.getUsername())) {
//            model.addAttribute("usernameError", "Username is already taken");
//            hasErrors = true;
//        }
//
//        // Validate mobile number uniqueness
//        if (adminService.isMobileTaken(customer.getMobile())) {
//            model.addAttribute("mobileError", "Mobile number is already taken");
//            hasErrors = true;
//        }
//
//        // Validate date of birth
//        if (customer.getDob() == null) {
//            model.addAttribute("dobError", "Date of birth is required");
//            hasErrors = true;
//        } else {
//            LocalDate today = LocalDate.now();
//            Period age = Period.between(customer.getDob(), today);
//
//            if (age.getYears() < 18) {
//                model.addAttribute("dobError", "You must be at least 18 years old");
//                hasErrors = true;
//            }
//        }
//        customer.setEmail(email);
//        // Validate OTP
//        OTPgenerator otpRecord = otpRepo.findByEmail(customer.getEmail());
//        if (otpRecord == null || !otpRecord.getOtp().equals(otp)) {
//            model.addAttribute("otpError", "Invalid OTP");
//            model.addAttribute("email",email);
//            hasErrors = true;
//        }
//
//        if (hasErrors) {
//            model.addAttribute("email",email);
//            return "register";
//        }
//
//        // Save customer logic if everything is fine
//        if (adminService.saveCustomer(customer)) {
//            model.addAttribute("success", "Customer successfully saved!");
////            model.addAttribute("email",email);
//            return "adminDashboard";
//        } else {
//            model.addAttribute("otpError", "Error saving customer");
//            return "register";
//        }
//    }


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
