package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.TransactionRepo;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private TransactionRepo transactionRepo;

    @GetMapping("/dashboard")
    public String viewForm(){
        return "adminDashboard";
    }

    @GetMapping("/saveCustomer")
    public String viewForm(@ModelAttribute("customer") Customer customer, Model model) {
        boolean hasErrors = false;

        // Validate username
        if (adminService.isUsernameTaken(customer.getUsername())) {
            model.addAttribute("usernameError", "Username is taken");
            hasErrors = true;
        }
        if(adminService.isMobileTaken(customer.getMobile())){
            model.addAttribute("mobileError", "Mobile Number Taken");
            hasErrors = true;
        }
        if (customer.getDob() != null) {
            LocalDate today = LocalDate.now();
            Period age = Period.between(customer.getDob(), today);

            if (age.getYears() < 18) {
                model.addAttribute("dobError", "You must be at least 18 years old");
                hasErrors = true;
            }
        } else {
            model.addAttribute("dobError", "Date of Birth is required");
            hasErrors = true;
        }

        if (hasErrors) {
            return "register"; // Stay on the form page with error messages
        }

        if (adminService.saveCustomer(customer)) {
            return "adminDashboard";
        } else {
            return "not";
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
