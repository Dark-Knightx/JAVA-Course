package com.example.BOWO.Controller;

import com.example.BOWO.Modal.Customer;
import com.example.BOWO.Modal.OTPgenerator;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.OTPRepo;
import com.example.BOWO.Service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/regg")
public class RegistrationController {

    @Autowired
    private UserService userService;
    @Autowired
    private OTPRepo otpRepo;
    private final ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();


    @PostConstruct
    public void init() {
        taskScheduler.initialize();
    }

//    @GetMapping("/reg")
//    public String toS(Model model) {
//        model.addAttribute("customer", new Customer());
//        return "register";
//    }

    @GetMapping("/regi")
    public String toSd() {
        return "rr";
    }

    @PostMapping("/otp")
    public String generateOtp(@RequestParam String email, Model model) {
        if (email == null || email.isEmpty()) {
            model.addAttribute("customer", new Customer());
            model.addAttribute("otpError", "Email is required to generate OTP");
            return "register"; // Return to the registration page with an error
        }
        OTPgenerator otPgenerator2 = otpRepo.findByEmail(email);
        if (otPgenerator2 == null){
            userService.genotp(email);
        }else {
            model.addAttribute("customer", new Customer());
            model.addAttribute("otpError", "Enter OTP");
            model.addAttribute("email",email);
            return "register";
        }

        // Generate OTP without email validation

        OTPgenerator otPgenerator = otpRepo.findByEmail(email);

        if (otPgenerator != null) {
            // Schedule OTP removal after 30 seconds
            taskScheduler.schedule(() -> otpRepo.delete(otPgenerator),
                    new Date(System.currentTimeMillis() + 50000));
        }

        // Success message
        model.addAttribute("otpSuccess", "OTP sent to your email");
        model.addAttribute("customer", new Customer());
        model.addAttribute("email",email);
        model.addAttribute("otpClass",otPgenerator);
        return "register"; // Return to the same page with success message
    }


//    @PostMapping("/step1")
//    public String registerStep1(@ModelAttribute Customer customer,Model model,@RequestParam String email,@RequestParam String username) {
//        boolean hasErrors = false;
//        if (email == null || email.isEmpty()) {
//            model.addAttribute("customer",customer);
//            model.addAttribute("otpError", "Email is required to generate OTP");
//            hasErrors = true; // Return to the registration page with an error
//        }
//        if (username == null || username.isEmpty()) {
//            model.addAttribute("customer",customer);
//            model.addAttribute("otpError2", "username is missing");
//            hasErrors = true; // Return to the registration page with an error
//        }
//        if (hasErrors){
//            return "register";
//        }
//
//        if (userService.saveStep1(customer)){
//            model.addAttribute("customer", customer);
//            return "redirect:/regg/regi";
//        }else{
//            return "redirect:/regg/reg";
//        }
//    }
//

    @PostMapping("/step1")
    public String registerStep1(@ModelAttribute Customer customer, Model model,
                                @RequestParam String email, @RequestParam String username,
                                RedirectAttributes redirectAttributes) {
        boolean hasErrors = false;

        // Check for errors
        if (email == null || email.isEmpty()) {
            redirectAttributes.addFlashAttribute("otpError", "Email is required to generate OTP");
            hasErrors = true;
        }
        if (username == null || username.isEmpty()) {
            redirectAttributes.addFlashAttribute("otpError2", "Username is missing");
            hasErrors = true;
        }

        if (hasErrors) {
            redirectAttributes.addFlashAttribute("customer", customer);
            return "redirect:/regg/reg"; // Redirect to GET /register
        }

        // Process and redirect based on save result
        if (userService.saveStep1(customer)) {
            redirectAttributes.addFlashAttribute("customer", customer);
            return "redirect:/regg/regi";
        } else {
            return "redirect:/regg/reg";
        }
    }

    @GetMapping("/reg")
    public String showRegisterPage(Model model) {
        // Check for flash attributes and add them back to the model
        if (!model.containsAttribute("customer")) {
            model.addAttribute("customer", new Customer());
        }
        return "register"; // Return the registration page
    }

    @GetMapping("/step2")
    public String registerStep2(@RequestParam String email,@ModelAttribute Customer customer) {
        if (userService.completeRegistration(email,customer)){
            return "redirect:/login";
        }
        return "redirect:/regi";
    }
    @GetMapping("/imag")
    public String registrationFo() {
        return "registrationForm";
    }

    @PostMapping("/imageSave")
    public String handleImageUpload(@RequestParam("profileImage") MultipartFile file, Model model) {
        // Validate file type
        String contentType = file.getContentType();
        System.out.println("File type: " + contentType); // Debug
        if (!List.of("image/jpeg", "image/png", "image/jpg").contains(contentType)) {
            model.addAttribute("error", "Invalid file type. Only JPG, JPEG, and PNG are allowed.");
            System.out.println("Invalid file type error added");
            return "registrationForm";
        }

        // Validate file size (2MB)
        System.out.println("File size: " + file.getSize()); // Debug
        if (file.getSize() > 2 * 1024 * 1024) {
            model.addAttribute("error", "File size exceeds 2MB.");
            System.out.println("File size error added");
            return "registrationForm";
        }

        // Success case
        model.addAttribute("success", "Image uploaded successfully!");
        System.out.println("Success message added");
        return "registrationForm";
    }

}
