package com.example.BOWO.Service;

import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Optional<Ticket> getTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public void answerTicket(Integer ticketId, String answer, User admin) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        ticket.setStatus("CLOSED");
        ticket.setAnswer(answer);
        ticket.setAnsweredBy(admin);
        ticket.setAnsweredAt(LocalDateTime.now());
        ticketRepository.save(ticket);
    }

}