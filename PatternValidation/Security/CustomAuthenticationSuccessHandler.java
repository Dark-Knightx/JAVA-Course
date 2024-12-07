package com.example.demo.Security;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepository userRepository;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        logger.info("User logged in with authorities: {}", authorities);

        String pattern = request.getParameter("pattern");
        String CustomPattern = "12345";

            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals("ROLE_USER")) {
                    response.sendRedirect("/user/dashboard");
                    return;
                }
                else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                    if (CustomPattern.equals(pattern)) {
                        response.sendRedirect("/admin/dashboard");
                        return;
                    }
                }
            }
        response.sendRedirect("/loginPage"); // Default page if no role matched
    }
}
