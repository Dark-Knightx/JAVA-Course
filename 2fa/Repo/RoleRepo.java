package com.example.BOWO.Repo;

import com.example.BOWO.Modal.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Integer> {

    Optional<Role> findByName(String roleName);
}
