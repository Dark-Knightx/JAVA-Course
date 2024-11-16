package com.example.demo.security;

import com.example.demo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("Valkyrae") == null) {
                com.example.demo.modal.User admin = new com.example.demo.modal.User();
                admin.setUsername("Valkyrae");
                admin.setPassword(passwordEncoder.encode("asdfress")); // Encrypt password
                admin.setRole("ADMIN");
                userRepository.save(admin);
            }

            if (userRepository.findByUsername("Leslie") == null) {
                com.example.demo.modal.User user = new com.example.demo.modal.User();
                user.setUsername("Leslie");
                user.setPassword(passwordEncoder.encode("asdfrezz")); // Encrypt password
                user.setRole("USER");
                userRepository.save(user);
            }
        };
    }
}
