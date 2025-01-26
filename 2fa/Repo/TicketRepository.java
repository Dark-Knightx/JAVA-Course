package com.example.BOWO.Repo;

import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByCreatedBy(User user);
    List<Ticket> findByStatus(String status);
}
