package com.example.BOWO.Controller;

import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.UserRepository;
import com.example.BOWO.Service.TwoFactorAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class loginController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @GetMapping("/loginPage")//userLogin
    public String tof() {
        return "login";
    }



    @GetMapping("/loginPageAdmin")
    public String toff() {
        return "loginPage"; // Show login page if not authenticated
    }

    @GetMapping("/verify-2faPage")
    public String show2FAVerificationPage() {
        return "verify-2fa"; // HTML template for 2FA code verification
    }
    @PostMapping("/verify-2faa")
    public String verify2FACode(@RequestParam("code") String code, HttpServletRequest request,Principal principal, Model model) {
        User admin = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        if (!twoFactorAuthService.verifyCode(admin.getSecretKey(), code)) {
            model.addAttribute("error", "Invalid or expired 2FA code. Please try again.");
            return "verify-2fa";
        }
        request.getSession().setAttribute("2faVerified", true);
        return "redirect:/admin/dashboard"; // Redirect to dashboard on success
    }

}

