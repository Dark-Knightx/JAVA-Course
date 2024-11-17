package com.example.demo.service;

import com.example.demo.modal.Account;
import com.example.demo.modal.Customer;
import com.example.demo.repository.AccountRepo;
import com.example.demo.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AccountRepo accountRepo;

    @Autowired
    CustomerRepo customerRepo;

    public boolean saveCustomer(Customer customer) {
        Customer panExist = customerRepo.findByPan(customer.getPan());
        Customer mobileExist = customerRepo.findByMobile(customer.getMobile());

        if (panExist == null && mobileExist== null) {
            Account account = new Account();
            account.setAcNo((int) Math.floor(Math.random()*10000000));
            account.setIfsc("SBI" + (int) Math.floor(Math.random()*1000));
            accountRepo.save(account);
            customer.setAcNo(account.getAcNo());
            customerRepo.save(customer);
            return true;
        }else {
            return false;
        }
    }
}
