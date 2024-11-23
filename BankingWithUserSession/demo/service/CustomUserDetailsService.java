package com.example.demo.service;

import com.example.demo.modal.Admin;
import com.example.demo.modal.User;
import com.example.demo.repository.AdminRepo;
import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        Optional<User> appUser = Optional.ofNullable(userRepository.findByUsername(username));
        Admin appUser2 = adminRepo.findByUsername(username);
        if (appUser.isPresent()) {
            User user = appUser.get();
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword()) // Password is already encoded
                    .roles(user.getRole()) // ROLE_USER or ROLE_ADMIN
                    .build();
        }else if (appUser2 != null) {
            return org.springframework.security.core.userdetails.User.builder()
                    .username(appUser2.getUsername())
                    .password(appUser2.getPassword()) // Password is already encoded
                    .roles(appUser2.getRole()) // ROLE_USER or ROLE_ADMIN
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
