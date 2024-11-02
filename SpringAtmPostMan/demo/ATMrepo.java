package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ATMrepo extends JpaRepository<ATMmodal, Long>{

	ATMmodal findByAccountNo(int accountNo);
}
