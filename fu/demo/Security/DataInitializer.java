//package com.example.demo.Security;
//
//import com.example.demo.modal.Admin;
//import com.example.demo.repository.AdminRepo;
//import com.example.demo.repository.UserRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//public class DataInitializer {
//
//    @Bean
//    public CommandLineRunner initializeUsers(UserRepository userRepository, AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("Sykk") == null) {
//                com.example.demo.modal.User user = new com.example.demo.modal.User();
//                user.setUsername("Sykk");
//                user.setPassword(passwordEncoder.encode("asdfress")); // Encrypt password
//                user.setRole("USER");
//                userRepository.save(user);
//            }
//
//            if (adminRepo.findByUsername("Pokimane") == null) {
//                Admin admin = new com.example.demo.modal.Admin();
//                admin.setUsername("Pokimane");
//                admin.setPassword(passwordEncoder.encode("asdfaaaa")); // Encrypt password
//                admin.setRole("ADMIN");
//                adminRepo.save(admin);
//            }
//        };
//    }
//}
