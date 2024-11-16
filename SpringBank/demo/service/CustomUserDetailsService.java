package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.demo.modal.User appUser = userRepository.findByUsername(username);
        if (appUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword()) // Password is already encoded
                .roles(appUser.getRole()) // ROLE_USER or ROLE_ADMIN
                .build();
    }
}
