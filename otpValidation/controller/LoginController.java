package com.example.demo.controller;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {


    @GetMapping("/loginPage")
    public String getLoginPage() {
        return "loginPage";
    }

    @GetMapping("/userloginPage")
    public String userLoginPage() {
        return "userLoginPage";
    }

}
