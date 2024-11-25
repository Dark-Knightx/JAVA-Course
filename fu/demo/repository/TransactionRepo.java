package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.Transaction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Repository
public interface TransactionRepo extends JpaRepository<Transaction, Integer>{

    HashSet<Transaction> findAllBySenderAccount(int senderAccount);
    List<Transaction> findBySenderAccount(int senderAccount);
    ArrayList<Transaction> findAllByReceiverAccount(int senderAccount);
}
