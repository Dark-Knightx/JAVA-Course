//package com.example.demo.Security;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    protected String obtainUsername(HttpServletRequest request) {
//        return request.getParameter("username");
//    }
//
//    @Override
//    protected String obtainPassword(HttpServletRequest request) {
//        return request.getParameter("password");
//    }
//
//    protected String obtainPattern(HttpServletRequest request) {
//        return request.getParameter("pattern");
//    }
//
//    @Override
//    public UsernamePasswordPinAuthenticationToken attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        String pattern = obtainPattern(request);
//
//        return new UsernamePasswordPinAuthenticationToken(username, password, pattern);
//    }
//}
