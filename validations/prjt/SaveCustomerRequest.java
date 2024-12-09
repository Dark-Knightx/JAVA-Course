package com.example.demo.modal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class SaveCustomerRequest {

    @NotNull(message = "Customer details are required")
    private Customer customer;

    @NotEmpty(message = "OTP is required")
    private String otp;

    @NotEmpty(message = "Email is required")
    private String email;

    // Getters & Setters
    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

