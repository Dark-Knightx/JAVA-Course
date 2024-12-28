package com.example.BOWO.Controller;

//import com.example.BOWO.Modal.BankDetails;
import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Modal.Customer;
import com.example.BOWO.Repo.CmsRepo;
//import com.example.BOWO.Service.BankDetailsService;
import com.example.BOWO.Repo.CustomerRepo;
import com.example.BOWO.Repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Service.TicketService;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CmsRepo cmsRepo;
    @Autowired
    private CustomerRepo customerRepo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TicketService ticketService;

    @GetMapping("/tickets")
    public String getUserTickets(Authentication authentication, Model model) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Ticket> tickets = ticketService.getTicketsByUser(user);
        model.addAttribute("tickets", tickets);
        return "user-tickets";
    }
    @Autowired
private DepositHistoryRepo depositHistoryRepo;
// Use this editor to write, compile and run your Java code online
import java.util.Random;
class Main {
    public static void main(String[] args) {
        String prefix = "AXIX";
        Random random = new Random();
        int randomNumber = 1000000 + random.nextInt(9000000);
        System.out.println(prefix + randomNumber);
    }
}
@GetMapping("/depositHistory")
public String depositHistory(Model model) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    String email = userDetails.getUsername();

    // Fetch the last 5 deposit records sorted by depositDate (latest first)
    List<Deposit> last5Deposits = depositHistoryRepo.findTop5ByUserEmailOrderByDepositDateDesc(email);

    // Add attributes to the model
    model.addAttribute("deposits", last5Deposits);
    return "depositHistory"; // Name of the Thymeleaf template
}

    @GetMapping("/tickets/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "create-ticket";
    }

    @PostMapping("/tickets")
    public String createTicket(@ModelAttribute Ticket ticket, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ticket.setCreatedBy(user);
        ticketService.createTicket(ticket);
        return "redirect:/user/tickets";
    }

    @GetMapping("/dashboard")
    public String viewForm(){
        return "overview";
    }

    @GetMapping("/businessprofile")
    public String businessprofile(Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();
        Customer customer = customerRepo.findByEmail(username);

//        String kycStatus = customer.getKycVerification();
//        if (kycStatus.equals("Not Verified")) {
//            model.addAttribute("kycStatusNegative",kycStatus);
//        } else if (kycStatus.equals("Verified")) {
//            model.addAttribute("kycStatusPositive",kycStatus);
//        }
        model.addAttribute("customer",customer);
        return "businessprofile";
    }

    @GetMapping("/cms")
    public String viewFocfrm(Model model){

        CMS cms = cmsRepo.findByName("Sykkk");
        model.addAttribute("cmsText",cms.getCms());
        return "cmsUser";
    }



}
