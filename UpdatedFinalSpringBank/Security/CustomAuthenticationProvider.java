//package com.example.demo.Security;
//
//import com.example.demo.modal.User;
//import com.example.demo.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Component;
//
//import java.util.stream.Collectors;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        String pattern = ((UsernamePasswordPinAuthenticationToken) authentication).getPattern();
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
//
//        if (!user.getPassword().equals(password)) {
//            throw new BadCredentialsException("Invalid password");
//        }
//
//        if (!user.getPattern().equals(pattern)) {
//            throw new BadCredentialsException("Invalid Pattern");
//        }
//
////        return new UsernamePasswordPinAuthenticationToken(
////                username,
////                password,
////                pattern,
////                user.getRoles().stream()
////                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
////                        .collect(Collectors.toList())
////        );
//        return new UsernamePasswordPinAuthenticationToken(
//                username,
//                password,
//                pattern,
//                user.getRoles().stream()
//                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
//                        .collect(Collectors.toList())
//        );
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return UsernamePasswordPinAuthenticationToken.class.isAssignableFrom(authentication);
//    }
//}
