//package com.example.demo.Security;
//
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//public class UsernamePasswordPinAuthenticationToken extends UsernamePasswordAuthenticationToken {
//    private final String pattern;
//
//    public UsernamePasswordPinAuthenticationToken(Object principal, Object credentials, String pattern, Collection<? extends GrantedAuthority> authorities) {
//        super(principal, credentials, authorities);
//        this.pattern = pattern;
//    }
//
//    // Constructor for authentication without authorities (initial login attempt)
//    public UsernamePasswordPinAuthenticationToken(Object principal, Object credentials, String pattern) {
//        super(principal, credentials);
//        this.pattern = pattern;
//    }
//
//    public String getPattern() {
//        return pattern;
//    }
//}
