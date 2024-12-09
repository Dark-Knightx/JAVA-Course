package com.example.demo.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationFailureHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        logger.error("Authentication failed: {}", exception.getMessage());

        // Determine login type from the request
        String loginType = request.getParameter("loginType");
        if ("user".equalsIgnoreCase(loginType)) {
            response.sendRedirect("/userloginPage?error=invalid");
        } else if ("admin".equalsIgnoreCase(loginType)) {
            response.sendRedirect("/loginPage?error=invalid");
        }
    }
}
