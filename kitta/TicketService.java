package com.example.BOWO.Service;

import com.example.BOWO.Modal.Ticket;
import com.example.BOWO.Modal.User;
import com.example.BOWO.Repo.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getTicketsByUser(User user) {
        return ticketRepository.findByCreatedBy(user);
    }

    public List<Ticket> getOpenTickets() {
        return ticketRepository.findByStatus("OPEN");
    }

    public Optional<Ticket> getTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Optional<Ticket> answerTicket(Integer ticketId, String answer, User admin) {
        Optional<Ticket> optionalTicket = ticketRepository.findById(ticketId);
        if (optionalTicket.isPresent()) {
            Ticket ticket = optionalTicket.get();
            ticket.setStatus("CLOSED");
            ticket.setAnswer(answer);
            ticket.setAnsweredBy(admin);
            ticket.setAnsweredAt(LocalDateTime.now());
            return Optional.of(ticketRepository.save(ticket));
        }
        return Optional.empty();
    }
}