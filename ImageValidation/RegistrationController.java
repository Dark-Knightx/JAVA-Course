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

    @GetMapping("/reg")
    public String toS(Model model) {
        model.addAttribute("customer", new Customer());
        return "register";
    }

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


    @GetMapping("/step1")
    public String registerStep1(@ModelAttribute Customer customer,Model model) {

        if (userService.saveStep1(customer)){
            model.addAttribute("customer", customer);
            return "redirect:/regi";
        }else{
            return "redirect:/reg";
        }
    }
//
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
