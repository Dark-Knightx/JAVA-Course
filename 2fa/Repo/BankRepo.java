package com.example.BOWO.Repo;

import com.example.BOWO.Modal.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepo extends JpaRepository<Bank,Integer> {
}
