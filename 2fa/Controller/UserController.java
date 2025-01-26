package com.example.BOWO.Controller;

//import com.example.BOWO.Modal.BankDetails;
import com.example.BOWO.Modal.*;
import com.example.BOWO.Repo.*;
//import com.example.BOWO.Service.BankDetailsService;
import com.example.BOWO.Service.ImageService;
import com.example.BOWO.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/tickets/create")
    public String showCreateTicketForm(Model model) {
        model.addAttribute("ticket", new Ticket());
        return "create-ticket";
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
        model.addAttribute("customer",customer);
        return "businessprofile";
    }
    @Autowired
    private UserService userService;
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable Integer id) {
        byte[] image = userService.getImage(id);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }

    @GetMapping("/cms")
    public String viewFocfrm(Model model){

        CMS cms = cmsRepo.findByName("Sykkk");
        model.addAttribute("cmsText",cms.getCms());
        return "cmsUser";
    }
    @Autowired
    private TicketRepository ticketRepository;
    @GetMapping("/support")
    public String support(Model model,Authentication authentication){
        model.addAttribute("ticket", new Ticket());
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Ticket> tickets = ticketRepository.findByCreatedBy(user);
        model.addAttribute("tickets", tickets);
        return "support";
    }
    @PostMapping("/createTicket")
    public String createTicket(@ModelAttribute Ticket ticket, Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ticket.setCreatedBy(user);
        ticketRepository.save(ticket);
        return "redirect:/user/support";
    }
    @PostMapping("/removeTicket")
    public String removeTicket(@RequestParam Integer ticketId){
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ticketRepository.delete(ticket);
        return "redirect:/user/support?tab=view";
    }
    @Autowired
    private LoginHistoryRepo loginHistoryRepo;
    @GetMapping("/loginHistory")
    public String loginHi(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size, Model model){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Pageable pageable = PageRequest.of(page,size);
        Page<LoginHistory> Logg = loginHistoryRepo.findByEmail(email,pageable);

//        List<LoginHistory> Logg = loginHistoryRepo.findByEmail(email);

        model.addAttribute("currentPage",page);
        model.addAttribute("size",size);
        model.addAttribute("logins",Logg.getContent());
        model.addAttribute("totalPages",Logg.getTotalPages());
        model.addAttribute("totalEntries",Logg.getTotalElements());
        return "loginHistory";
    }
    @GetMapping("/loginHistoryy")
    public String loginHiy(@RequestParam(defaultValue = "0") int page,
                          @RequestParam(defaultValue = "5") int size,
                          Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        // Create a pageable object with the current page and size
        Pageable pageable = PageRequest.of(page, size);

        // Fetch paginated login history
        Page<LoginHistory> loginsPage = loginHistoryRepo.findByEmail(email, pageable);

        // Add attributes to the model for Thymeleaf template
        model.addAttribute("logins", loginsPage.getContent()); // Current page's data
        model.addAttribute("currentPage", page);              // Current page number
        model.addAttribute("size", size);                     // Page size
        model.addAttribute("totalPages", loginsPage.getTotalPages()); // Total pages
        model.addAttribute("totalEntries", loginsPage.getTotalElements());
        return "loginnHistory";
    }


}
