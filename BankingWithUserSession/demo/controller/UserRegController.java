package com.example.demo.controller;

import com.example.demo.modal.User;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserRegController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "userReg";
    }

    @PostMapping("/registerr")
    public String registerUser(@ModelAttribute User user, Model model) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
//            model.addAttribute("error", "Username already exists!");
            return "not";
        }
        if (customerRepo.findByPan(user.getPanId()) == null){
            return "not";
        }
        if (userRepository.findByPanId(user.getPanId()) != null){
            return "not";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        userRepository.save(user);
        return "works";
    }
}

