package com.example.ExpenseTracker.Repository;

import com.example.ExpenseTracker.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
