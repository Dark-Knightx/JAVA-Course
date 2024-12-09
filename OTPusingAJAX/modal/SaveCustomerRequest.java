package com.example.demo.modal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveCustomerRequest {
    private Customer customer;
    private String otp;
    private String email;

}

