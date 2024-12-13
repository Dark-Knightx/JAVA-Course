package com.example.BOWO.Controller;

import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Repo.CmsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CmsRepo cmsRepo;

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
