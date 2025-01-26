package com.example.BOWO.Security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication != null) {
            // Get user roles
            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

            // Redirect based on roles
            if (roles.contains("ROLE_ADMIN")) {
                response.sendRedirect("/loginPageAdmin?logout");
            } else if (roles.contains("ROLE_USER")) {
                response.sendRedirect("/loginPage?logout");
            } else {
                response.sendRedirect("/loginPage?logout"); // Default page
            }
        } else {
            // If no authentication, redirect to a default page
            response.sendRedirect("/loginPage?logout");
        }
    }
}
