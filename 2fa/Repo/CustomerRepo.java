package com.example.BOWO.Repo;

import com.example.BOWO.Modal.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepo extends JpaRepository<Customer, Integer> {
    Customer findByEmail(String userId);

    List<Customer> findByKycVerification(String notVerified);
}
