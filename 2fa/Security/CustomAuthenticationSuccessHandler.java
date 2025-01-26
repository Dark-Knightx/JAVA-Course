package com.example.BOWO.Security;

import com.example.BOWO.Modal.LoginHistory;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.LoginHistoryRepo;
import com.example.BOWO.Repo.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collection;

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private LoginHistoryRepo loginHistoryRepo;
    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String loginType = request.getParameter("loginType"); // Get login type from the request
        String email = request.getParameter("username");
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        for (GrantedAuthority authority : authorities) {
            if ("user".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_USER")) {
                LoginHistory loginHistory = new LoginHistory();
                loginHistory.setEmail(email);
                loginHistory.setLocalDateTime(LocalDateTime.now());
                loginHistoryRepo.save(loginHistory);
                response.sendRedirect("/user/dashboard");
                logger.info("User logged in with authorities: {}", authorities);
                return;
            }
            if ("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")) {
                String pattern = request.getParameter("pattern");
                String customPattern = "12345";

                User admin = userRepository.findByEmail(email)
                        .orElseThrow(() -> new RuntimeException("Admin not found"));


//                if (customPattern.equals(pattern)) {
//                    if (admin.is2faEnabled()) {
//                        // Redirect to the 2FA verification page
//                        response.sendRedirect("/verify-2faPage");
//                        return;
//                    }
//                    response.sendRedirect("/admin/dashboard");
//                    logger.info("User logged in with authorities: {}", authorities);
//                    return;
//                }else{
//                    response.sendRedirect("/loginPageAdmin?error=Incorrect Pattern");
//                    return;
//                }
                if (customPattern.equals(pattern)) {
                    if (admin.is2faEnabled()) {
                        request.getSession().setAttribute("2faVerified", false); // Initial state
                        request.getSession().setAttribute("patternValidated", true); // Pattern validated
                        response.sendRedirect("/verify-2faPage");
                        return;
                    } else {
                        request.getSession().setAttribute("patternValidated", true); // Mark pattern validated
                        response.sendRedirect("/admin/dashboard");
                        return;
                    }
                } else {
                    response.sendRedirect("/loginPageAdmin?error=Incorrect Pattern");
                    return;
                }

            }
            if ("user".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")){
                response.sendRedirect("/loginPage?error=Invalid Credentials");
                return;
            }
            if("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_USER")){
                response.sendRedirect("/loginPageAdmin?error=Invalid Credentials");
                return;
            }
        }

//        response.sendRedirect("/loginPage?error=invalid"); // Redirect to an error page if mismatch occurs
    }



}
