package com.example.demo.controller;

import com.example.demo.modal.Customer;
import com.example.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/dashboard")
    public String viewForm(){
        return "adminDashboard";
    }

    @GetMapping("/saveCustomer")
    public String viewForm(@ModelAttribute Customer customer, Model model){
        if (adminService.saveCustomer(customer)) {
            return "adminDashboard";
        }else {
            return "not";
        }
    }

    @GetMapping("/reg")
    public String registerPage(){
        return "register";
    }
}
