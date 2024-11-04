package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.modal.Account;

public interface AccountRepo extends JpaRepository<Account, Integer>{

}
