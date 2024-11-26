package com.example.demo.service;

import com.example.demo.modal.Account;
import com.example.demo.modal.Transaction;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.modal.User;
import com.example.demo.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private TransactionRepo transactionRepo;

    public boolean deposit(Transaction transaction){
        Account account = accountRepo.findByAcNo(transaction.getSenderAccount());

        if (account != null && transaction.getAmount()>100){
            account.setBalance(account.getBalance() + transaction.getAmount());
            transaction.setReceiverAccount(transaction.getSenderAccount());
            accountRepo.save(account);
            transactionRepo.save(transaction);
            return  true;
        }else {
            return false;
        }
    }

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
