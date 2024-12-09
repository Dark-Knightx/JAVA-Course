package com.example.demo.controller;

import com.example.demo.modal.Role;
import com.example.demo.modal.User;
import com.example.demo.modal.UserRoles;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepository;
import com.example.demo.repository.UserRoleRepo;
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
//    @Autowired
//    private CustomerRepo customerRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRoleRepo userRoleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "userReg";
    }

    @PostMapping("/registerr")
    public String registerUser(@ModelAttribute User user, @ModelAttribute("roleName") String roleName, Model model) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            model.addAttribute("error", "Username already exists!");
            return "not";
        }

        Role role = roleRepo.findByName(roleName).orElseGet(() -> {
            Role newRole = new Role();
            newRole.setName(roleName);
            return roleRepo.save(newRole);
        });

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        user.getRoles().add(role);
        userRepository.save(user);

        return "works";
    }

}

