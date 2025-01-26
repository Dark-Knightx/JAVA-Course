package com.example.BOWO.Repo;

import com.example.BOWO.Modal.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRoles,Integer> {
}
