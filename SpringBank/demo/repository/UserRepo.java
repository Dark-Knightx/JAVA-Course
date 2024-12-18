package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.modal.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	User findByUsername(String username);

    	User findByPanId(int panId);

}
