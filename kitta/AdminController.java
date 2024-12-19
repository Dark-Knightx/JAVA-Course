package com.example.BOWO.Controller;

import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Modal.Customer;
import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.CmsRepo;
import com.example.BOWO.Repo.CustomerRepo;
import com.example.BOWO.Repo.UserRepository;
import com.example.BOWO.Service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/tickets/open")
    public String getOpenTickets(Model model) {
        List<Ticket> tickets = ticketService.getOpenTickets();
        model.addAttribute("tickets", tickets);
        return "open-tickets";
    }

    @GetMapping("/tickets/{id}/answer")
    public String showAnswerTicketForm(@PathVariable Integer id, Model model) {
        Optional<Ticket> ticketOptional = ticketService.getTicketById(id);
        if (ticketOptional.isPresent()) {
            model.addAttribute("ticket", ticketOptional.get());
            return "answer-ticket";
        }
        return "redirect:/admin/tickets/open";
    }

    @PostMapping("/tickets/{id}/answer")
    public String answerTicket(
            @PathVariable Integer id,
            @RequestParam String answer,
            Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ticketService.answerTicket(id, answer, admin);
        return "redirect:/admin/tickets/open";
    }

    @GetMapping("/dashboard")
    public String viewForm(){
        return "adminDashboard";
    }
    @GetMapping("/cms")
    public String viewFocfrm(Model model) {
        CMS cms = cmsRepo.findByName("Sykkk");
        if (cms == null) {
            cms = new CMS(); // Create a default CMS object if none exists
            cms.setName("Sykkk");
        }
        model.addAttribute("cms", cms); // Bind CMS object to the form
        return "cmsAdmin";
    }

    @GetMapping("/unverified-customers")
    public String getUnverifiedCustomers(Model model) {
        List<Customer> unverifiedCustomers = customerRepo.findByKycVerification("Not Verified");
        model.addAttribute("customers", unverifiedCustomers);
        return "admin-dashboard"; // Name of your admin dashboard HTML page
    }
    @PostMapping("/verify-customer")
    public String verifyCustomer(@RequestParam Integer id) {
        Customer customer = customerRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid customer ID: " + id));
        customer.setKycVerification("Verified");
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