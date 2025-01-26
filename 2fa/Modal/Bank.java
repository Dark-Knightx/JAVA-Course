package com.example.BOWO.Modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String accountNumber;
    private String bankName;
    private Integer balance;
    private boolean verified = false;

}

