package com.example.BOWO.Modal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_banks")
public class UserBanks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}