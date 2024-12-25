package com.example.BOWO.Repo;

import com.example.BOWO.Modal.LoginHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepo extends JpaRepository<LoginHistory,Integer> {
    List<LoginHistory> findByEmail(String email);
    Page<LoginHistory> findByEmail(String email, Pageable pageable);
}
