package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.Account;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.CustomerRepo;
import com.example.demo.repository.TransactionRepo;

@Service
public class TransactionService {
	
	@Autowired
	TransactionRepo transactionRepo;
	@Autowired
	AccountRepo accountRepo;
	@Autowired
	CustomerRepo customerRepo;
	
	
	public boolean sendAmount(Transaction transaction) {
		Account senderAccount = accountRepo.findByAcNo(transaction.getSenderAccount());
		Account receiverAccount = accountRepo.findByAcNo(transaction.getReceiverAccount());
		
		if (senderAccount != null && receiverAccount != null) {
			if (transaction.getAmount()>=100) {
				senderAccount.setBalance(senderAccount.getBalance() - transaction.getAmount());
				accountRepo.save(senderAccount);
				
				receiverAccount.setBalance(receiverAccount.getBalance() + transaction.getAmount());
				accountRepo.save(receiverAccount);
				
				transactionRepo.save(transaction);
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
}
