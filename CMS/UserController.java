package com.example.BOWO.Controller;

import com.example.BOWO.Modal.CMS;
import com.example.BOWO.Repo.CmsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CmsRepo cmsRepo;

    @GetMapping("/dashboard")
    public String viewForm(){
        return "overview";
    }

    @GetMapping("/cms")
    public String viewFocfrm(Model model){

        CMS cms = cmsRepo.findByName("Sykkk");
        model.addAttribute("cmsText",cms.getCms());
        return "cmsUser";
    }
}
