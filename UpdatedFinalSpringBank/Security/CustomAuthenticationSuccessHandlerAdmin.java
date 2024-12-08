//package com.example.demo.Security;
//
//import com.example.demo.repository.UserRepository;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.util.Collection;
//
//public class CustomAuthenticationSuccessHandlerAdmin implements AuthenticationSuccessHandler {
//
//    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandlerAdmin.class);
//
//    @Autowired
//    private UserRepository userRepository;
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        String loginType = request.getParameter("loginType"); // Get login type from the request
//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//        logger.info("User logged in with authorities: {}", authorities);
//
//        for (GrantedAuthority authority : authorities) {
//            if ("admin".equalsIgnoreCase(loginType) && !authority.getAuthority().equals("ROLE_ADMIN")) {
//                response.sendRedirect("/loginPage?error=invalid");
//                return;
//            }else if ("admin".equalsIgnoreCase(loginType) && authority.getAuthority().equals("ROLE_ADMIN")) {
//                String pattern = request.getParameter("pattern");
//                String customPattern = "12345";
//                if (customPattern.equals(pattern)) {
//                    response.sendRedirect("/admin/dashboard");
//                    return;
//                }
//            }
//        }
//
//        response.sendRedirect("/loginPage?error=invalid");
//    }
//
//}
