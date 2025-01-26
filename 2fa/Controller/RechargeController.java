package com.example.BOWO.Controller;

import com.example.BOWO.Service.RazorpayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recharge")
public class RechargeController {

    @Autowired
    private RazorpayService razorpayService;

//    @PostMapping("/createOrder")
//    public String createRechargeOrder(@RequestParam double amount) {
//        return razorpayService.createOrder(amount);
//    }

    @PostMapping("/createOrder")
    public ResponseEntity<?> createRechargeOrder(@RequestParam double amount) {
        String orderData = razorpayService.createOrder(amount);
        return ResponseEntity.ok(orderData);
    }
}

