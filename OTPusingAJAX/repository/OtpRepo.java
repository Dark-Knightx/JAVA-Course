package com.example.demo.repository;

import com.example.demo.modal.OTPgenerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepo extends JpaRepository<OTPgenerator,Integer> {
    OTPgenerator findByEmail(String email);
}
