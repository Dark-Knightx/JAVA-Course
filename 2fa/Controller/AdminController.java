package com.example.BOWO.Controller;

import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Modal.Customer;
import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.CmsRepo;
import com.example.BOWO.Repo.CustomerRepo;
import com.example.BOWO.Repo.TicketRepository;
import com.example.BOWO.Repo.UserRepository;
import com.example.BOWO.Service.TicketService;
import com.example.BOWO.Service.TwoFactorAuthService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CmsRepo cmsRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private TwoFactorAuthService twoFactorAuthService;

    @GetMapping("/openTickets")
    public String getOpenTickets(Model model) {
        List<Ticket> tickets = ticketRepository.findByStatus("OPEN");
        model.addAttribute("tickets", tickets);
        return "open-tickets";
    }
    @PostMapping("/answerTicket")
    public String answ(@RequestParam Integer id,@RequestParam String answer){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ticketService.answerTicket(id, answer, admin);
        return "redirect:/admin/openTickets";
    }

    @GetMapping("/dashboard")
    public String adminDashboard(HttpServletRequest request) {
        Boolean patternValidated = (Boolean) request.getSession().getAttribute("patternValidated");
        Boolean twoFaVerified = (Boolean) request.getSession().getAttribute("2faVerified");

        if (patternValidated == null || !patternValidated || twoFaVerified == null || !twoFaVerified) {
            return "redirect:/loginPageAdmin?error=Unauthorized";
        }
        return "adminDashboard"; // Render the admin dashboard page
    }

    @GetMapping("/cms")
    public String viewFocfrm(Model model,HttpServletRequest request) {
        Boolean patternValidated = (Boolean) request.getSession().getAttribute("patternValidated");
        Boolean twoFaVerified = (Boolean) request.getSession().getAttribute("2faVerified");

        if (patternValidated == null || !patternValidated || twoFaVerified == null || !twoFaVerified) {
            return "redirect:/loginPageAdmin?error=Unauthorized";
        }
        CMS cms = cmsRepo.findByName("Sykkk");
        if (cms == null) {
            cms = new CMS(); // Create a default CMS object if none exists
            cms.setName("Sykkk");
        }
        model.addAttribute("cms", cms); // Bind CMS object to the form
        return "cmsAdmin";
    }



    @PostMapping("/enable-2fa")
    public String enable2FA(Principal principal, Model model) {
        try {
            // Retrieve the current user
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if 2FA is already enabled for the user
            if (user.is2faEnabled()) {
                model.addAttribute("error", "2FA is already enabled for this user.");
                return "error"; // Return an error page if 2FA is already enabled
            }

            // Generate a secret key for the user only if it's not set


            user.set2faEnabled(true); // Mark the user as having 2FA enabled
            userRepository.save(user);

            // Generate the QR code for the user to scan in their 2FA app
            String qrCode = twoFactorAuthService.generateQrCode(user.getSecretKey(), user.getEmail());
            model.addAttribute("qrCode", qrCode); // Add the QR code to the model

            return "enable-2fa"; // Name of the HTML template where the QR code will be shown
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while enabling 2FA: " + e.getMessage());
            return "error"; // You should define an error page for this purpose
        }
    }


    @PostMapping("/verify-2fa")
    public ResponseEntity<?> verify2FA(Principal principal, @RequestParam String code) {
        try {
            // Retrieve the current user from the database
            User user = userRepository.findByEmail(principal.getName())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Check if 2FA is enabled for the user
            if (!user.is2faEnabled()) {
                return ResponseEntity.badRequest().body("2FA is not enabled for this user.");
            }

            // Verify the code entered by the user using the secret key
            boolean isValid = twoFactorAuthService.verifyCode(user.getSecretKey(), code);

            // If the code is valid, allow the user to continue
            if (isValid) {
                return ResponseEntity.ok("2FA code verified successfully. You are now authenticated.");
            } else {
                // If the code is invalid, return an error response
                return ResponseEntity.badRequest().body("Invalid 2FA code. Please try again.");
            }

        } catch (Exception e) {
            // Handle any exceptions that might occur (e.g., user not found, etc.)
            return ResponseEntity.status(500).body("An error occurred while verifying the 2FA code: " + e.getMessage());
        }
    }
//    @GetMapping("/verify-2faPage")
//    public String show2FAVerificationPage() {
//        return "verify-2fa"; // HTML template for 2FA code verification
//    }
//    @PostMapping("/verify-2faa")
//    public String verify2FACode(@RequestParam("code") String code, Principal principal, Model model) {
//        User admin = userRepository.findByEmail(principal.getName())
//                .orElseThrow(() -> new RuntimeException("Admin not found"));
//
//        if (!twoFactorAuthService.verifyCode(admin.getSecretKey(), code)) {
//            model.addAttribute("error", "Invalid or expired 2FA code. Please try again.");
//            return "verify-2fa";
//        }
//
//        return "redirect:/admin/dashboard"; // Redirect to dashboard on success
//    }


    @GetMapping("/unverified-customers")
    public String getUnverifiedCustomers(Model model, RedirectAttributes redirectAttributes) {
        List<Customer> unverifiedCustomers = customerRepo.findByKycVerification("Not Verified");
        if (unverifiedCustomers.isEmpty()){
            redirectAttributes.addFlashAttribute("NoU","There arent any Unverified Customers");
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("customers", unverifiedCustomers);
        return "admin-dashboard"; // Name of your admin dashboard HTML page
    }
    @PostMapping("/verify-customer")
    public String verifyCustomer(@RequestParam Integer id,@RequestParam String comments) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
        customer.setKycVerification("Verified");
        customer.setBrandNameVerification("Accepted");
        customer.setBusinessIdVerification("Accepted");
        customer.setCategoryVerification("Accepted");
        customer.setDescriptionVerification("Accepted");
        customer.setComments(comments);
        customerRepo.save(customer); // Save the updated customer

        // Redirect back to the admin dashboard
        return "redirect:/admin/unverified-customers";
    }
    @PostMapping("/reject-customer")
    public String rejectCustomer(@RequestParam Integer id,
                                 @RequestParam String nameVerify,
                                 @RequestParam String idVerify,
                                 @RequestParam String categoryVerify,
                                 @RequestParam String descVerify,
                                 @RequestParam String comments) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
        customer.setKycVerification("Rejected");
        customer.setBrandNameVerification(nameVerify);
        customer.setBusinessIdVerification(idVerify);
        customer.setCategoryVerification(categoryVerify);
        customer.setDescriptionVerification(descVerify);
        customer.setComments(comments);
        customerRepo.save(customer); // Save the updated customer

        // Redirect back to the admin dashboard
        return "redirect:/admin/unverified-customers";
    }


    @PostMapping("/updatecms")
    public String updatecms(@ModelAttribute CMS cms) {
        CMS existingCms = cmsRepo.findByName("Sykkk");
        if (existingCms != null) {
            existingCms.setCms(cms.getCms()); // Update the CMS field
            cmsRepo.save(existingCms);       // Save changes to the database
        }
        return "redirect:/admin/cms"; // Redirect to the CMS admin page
    }
}