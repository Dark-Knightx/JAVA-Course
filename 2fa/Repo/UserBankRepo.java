package com.example.BOWO.Repo;

import com.example.BOWO.Modal.UserBanks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBankRepo extends JpaRepository<UserBanks,Integer> {
}
