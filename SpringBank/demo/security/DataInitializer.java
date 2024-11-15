// package com.example.demo.Security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("admin") == null) {
                com.example.demo.Security.User admin = new com.example.demo.Security.User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("admin123")); // Encrypt password
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("user") == null) {
                com.example.demo.Security.User user = new com.example.demo.Security.User();
                user.setUsername("user");
                user.setPassword(passwordEncoder.encode("user123")); // Encrypt password
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }
}
