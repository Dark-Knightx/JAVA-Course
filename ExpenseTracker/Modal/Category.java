package com.example.ExpenseTracker.Modal;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private boolean status; // Active/Inactive

    @ManyToOne
    private User user;

    // Getters and setters
}

