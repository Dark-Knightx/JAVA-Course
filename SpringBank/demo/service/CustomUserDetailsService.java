package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.repository.AdminRepo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepo adminRepo;

    public CustomUserDetailsService(UserRepository userRepository, AdminRepo adminRepo) {
        this.userRepository = userRepository;
        this.adminRepo = adminRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.modal.User appUser = userRepository.findByUsername(username);
        Admin appUser2 = adminRepo.findByUsername(username);
        if (appUser != null) {
            return User.builder()
                    .username(appUser.getUsername())
                    .password(appUser.getPassword()) // Password is already encoded
                    .roles(appUser.getRole()) // ROLE_USER or ROLE_ADMIN
                    .build();

        } else if (appUser2 != null) {
            return User.builder()
                    .username(appUser2.getUsername())
                    .password(appUser2.getPassword()) // Password is already encoded
                    .roles(appUser2.getRole()) // ROLE_USER or ROLE_ADMIN
                    .build();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
