package com.example.demo.repository;

import com.example.demo.modal.Admin;
import com.example.demo.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Admin findByUsername(String username);
}
