package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.Account;


@Repository
public interface AccountRepo extends JpaRepository<Account, Integer>{

	Account findByAcNo(int senderAccount);
	
	
}
