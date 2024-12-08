package com.example.demo.Security;

import com.example.demo.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String loginType = request.getParameter("loginType"); // Get login type from the request
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        logger.info("User logged in with authorities: {}", authorities);

        for (GrantedAuthority authority : authorities) {
            if ("user".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_USER")) {
                response.sendRedirect("/user/dashboard");
                logger.info("User logged in with authorities: {}", authorities);
                return;
            }
            if ("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")) {
                String pattern = request.getParameter("pattern");
                String customPattern = "12345";
                if (!customPattern.equals(pattern)) {
                    response.sendRedirect("/loginPage?error=invalid");
                    return;
                }
            }
            if ("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")) {
                String pattern = request.getParameter("pattern");
                String customPattern = "12345";
                if (customPattern.equals(pattern)) {
                    response.sendRedirect("/admin/dashboard");
                    logger.info("User logged in with authorities: {}", authorities);
                    return;
                }
            }
            if ("user".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")){
                response.sendRedirect("/userloginPage?error=invalid");
                return;
            }
            if("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_USER")){
                response.sendRedirect("/loginPage?error=invalid");
                return;
            }
        }

//        response.sendRedirect("/loginPage?error=invalid"); // Redirect to an error page if mismatch occurs
    }

}
