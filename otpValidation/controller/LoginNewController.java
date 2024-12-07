//package com.example.demo.controller;
//
//import com.example.demo.modal.User;
//import com.example.demo.repository.UserRepository;
//import com.example.demo.service.CustomUserDetailsService;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//public class LoginNewController {
//
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/loginPage")
//    public String getLoginPage() {
//        return "loginPage";
//    }
//
//    @PostMapping("/loginPage")
//    public String processLogin(@RequestParam String username,
//                               @RequestParam String password,
//                               @RequestParam String pattern,
//                               HttpServletRequest request) {
//
//        System.out.println("Username: " + username);
//        System.out.println("Password: " + password);
//        System.out.println("Pattern: " + pattern);
//
//        // Attempt to find the user in the repository
//        User user = userRepository.findByUsername(username).orElse(null);
//        if (user == null) {
//            System.out.println("User not found in the database.");  // Debugging log
//            return "redirect:/loginPage?error=user_not_found";
//        }
//
//        System.out.println("User found: " + user.getUsername());  // Debugging log
//
//        // Validate pattern
//        if (!isValidPattern(user, pattern)) {
//            return "redirect:/loginPage?error=invalid_pattern";
//        }
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password)
//        );
//
//        if (authentication.isAuthenticated()) {
//            return "redirect:/dashboard";  // Success
//        } else {
//            return "redirect:/loginPage?error=invalid_credentials";  // Failure
//        }
//    }
//
//    private boolean isValidPattern(User user, String pattern) {
//        String storedPattern = user.getPattern();
//        return storedPattern != null && storedPattern.equals(pattern);
//    }
//}
//
