package com.example.BOWO.Controller;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TwoFactorController {

    @PostMapping("/verify2fa")
    public String verify2fa(HttpServletRequest request, @RequestParam("otp") String otp) {
        String secretKey = (String) request.getSession().getAttribute("2faSecret");
        GoogleAuthenticator gAuth = new GoogleAuthenticator();

        // Verify the OTP
        boolean isOtpValid = gAuth.authorize(secretKey, Integer.parseInt(otp));

        if (isOtpValid) {
            // Redirect to admin dashboard if OTP is valid
            return "redirect:/admin/dashboard";
        } else {
            // Show error if OTP is invalid
            return "redirect:/admin/2fa?error=Invalid OTP";
        }
    }
}


