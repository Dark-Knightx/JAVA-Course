package com.example.BOWO.Repo;

import com.example.BOWO.Modal.CMS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CmsRepo extends JpaRepository<CMS,Integer> {
    CMS findByName(String sykkk);
}
