package com.example.BOWO.Repo;

import com.example.BOWO.Modal.OTPgenerator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OTPRepo extends JpaRepository<OTPgenerator,Integer> {
    OTPgenerator findByEmail(String email);
}
