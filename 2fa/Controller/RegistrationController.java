package com.example.BOWO.Controller;

import com.example.BOWO.Modal.Customer;
import com.example.BOWO.Modal.OTPgenerator;
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

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
    public String toSd(Model model,RedirectAttributes redirectAttributes) {
        if (model.containsAttribute("customer")) {
            return "rr";
        }
        redirectAttributes.addFlashAttribute("regg","Finsish Step One/Login to complete Step 2");
        return "redirect:/regg/reg";
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
    @PostMapping("/check")
    public String cdccs(){
        return "redirect:/regg/reg";
    }

    @PostMapping("/step1")
    public String registerStep1(@ModelAttribute Customer customer, Model model,
                                @RequestParam String email, @RequestParam String username,
                                RedirectAttributes redirectAttributes) {
        boolean hasErrors = false;
        if (customer.getPassword().isEmpty() || customer.getUsername().isEmpty() || customer.getEmail().isEmpty()){
            redirectAttributes.addFlashAttribute("customer", customer);
            redirectAttributes.addFlashAttribute("otpError", "Fill in the missing details");
            return "redirect:/regg/reg";
        }else {
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
            Pattern pattern = Pattern.compile(emailRegex);
            if (!pattern.matcher(email).matches()) {
                redirectAttributes.addFlashAttribute("otpError", "Invalid email format");
                hasErrors = true;
            }
            String passwordRegex = "^[0-9]{6}$";
            Pattern pattern2 = Pattern.compile(passwordRegex);
            if (!pattern2.matcher(customer.getPassword()).matches()) {
                redirectAttributes.addFlashAttribute("otpError2", "Invalid pass format");
                hasErrors = true;
            }

            if (hasErrors) {
                redirectAttributes.addFlashAttribute("customer", customer);
                return "redirect:/regg/reg"; // Redirect to GET /register
            }
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
            if (!model.containsAttribute("customer")) {
                model.addAttribute("customer", new Customer());
            }
            return "register"; // Return the registration page
    }

    @PostMapping("/step2")
    public String registerStep2(@ModelAttribute Customer customer,@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) throws IOException {
        if (userService.completeRegistration(customer,file)){
            redirectAttributes.addFlashAttribute("Success", "Registration Successful");
            return "redirect:/loginPage";
        }
        redirectAttributes.addFlashAttribute("customer",customer);
        return "redirect:/regi";
    }
    @GetMapping("/imag")
    public String registrationFo() {
        return "registrationForm";
    }

    @PostMapping("/imageSave")
    public String handleImageUpload(@RequestParam("profileImage") MultipartFile file, RedirectAttributes redirectAttributes) {
        // Validate file type
        boolean errors = false;
        String contentType = file.getContentType();
        System.out.println("File type: " + contentType); // Debug
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "No image file uploaded. Please select a file.");
            System.out.println("No file uploaded error added");
            errors = true; // Redirect to the upload page
        }
        if (!List.of("image/jpeg", "image/png", "image/jpg").contains(contentType)) {
            redirectAttributes.addFlashAttribute("error", "Invalid file type. Only JPG, JPEG, and PNG are allowed.");
            System.out.println("Invalid file type error added");
            errors = true;
        }

        // Validate file size (2MB)
        System.out.println("File size: " + file.getSize()); // Debug
        if (file.getSize() > 2 * 1024 * 1024) {
            redirectAttributes.addFlashAttribute("error2", "File size exceeds 2MB.");
            System.out.println("File size error added");
            errors = true;
        }
        if (errors){
            return "redirect:/regg/imag";
        }

        // Success case
        redirectAttributes.addFlashAttribute("success", "Image uploaded successfully!");
        System.out.println("Success message added");
        return "redirect:/regg/imag";
    }

}
