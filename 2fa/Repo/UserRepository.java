package com.example.BOWO.Repo;

import com.example.BOWO.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {

    Optional<User> findByEmail(String username);

    Optional<User> findByUsername(String sykk);
}
