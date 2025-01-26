package com.example.BOWO.Repo;

import com.example.BOWO.Modal.MyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MyRepository extends JpaRepository<MyEntity, Long> {
    // No additional methods needed for basic CRUD operations
}

