package com.example.ExpenseTracker.Modal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;
    private Date date;

    @ManyToOne
    private User user;

    @ManyToOne
    private Category category;

    private String type; // Income or Expense

    // Getters and setters
}

