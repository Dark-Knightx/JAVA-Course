package com.example.demo.security;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AdminRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initializeUsers(UserRepository userRepository, AdminRepo adminRepo, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByUsername("Valkyrae") == null) {
                com.example.demo.modal.User user = new com.example.demo.modal.User();
                admin.setUsername("Valkyrae");
                admin.setPassword(passwordEncoder.encode("asdfress")); // Encrypt password
                admin.setRole("USER");
                userRepository.save(admin);
            }

            if (adminRepo.findByUsername("Pokimane") == null) {
                Admin admin = new com.example.demo.modal.Admin();
                admin.setUsername("Pokimane");
                admin.setPassword(passwordEncoder.encode("asdfaaaa")); // Encrypt password
                admin.setRole("ADMIN");
                adminRepo.save(admin);
            }
        };
    }
}
