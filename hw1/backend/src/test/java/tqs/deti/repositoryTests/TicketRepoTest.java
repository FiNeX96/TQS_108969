package tqs.deti.repositoryTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.deti.repositories.TicketRepository;
import tqs.deti.models.Ticket;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;

@DataJpaTest
 class TicketRepoTest {

    @Autowired
    private TicketRepository ticketRepository;

    @Test
    @DisplayName("Test saving a ticket and then finding it by ID")
    void whenSaved_thenFindById() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);

        Ticket found = ticketRepository.findById(ticket.getId());

        assertEquals(ticket.getId(), found.getId());
        assertThat(found).isEqualTo(ticket);
    }

    @Test
    @DisplayName("Test deleting a ticket and then confirming it is not found")
    void whenDelete_thenNotFound() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);
        ticketRepository.delete(ticket);

        Ticket found = ticketRepository.findById(ticket.getId());

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test saving a ticket and then finding it by trip ID")
    void whenSaved_thenFindByTripID() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);

        Ticket found = ticketRepository.findByTripID(ticket.getTripID()).get(0);

        assertEquals(ticket.getTripID(), found.getTripID());
        assertThat(found).isEqualTo(ticket);
    }

    @Test
    @DisplayName("Test deleting a ticket by trip ID and then confirming it is not found")
    void whenDeleteByTripID_thenNotFound() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);
        ticketRepository.deleteByTripID(ticket.getTripID());

        List<Ticket> found = ticketRepository.findByTripID(ticket.getTripID());

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Test saving a ticket and then finding it by seat number and trip ID")
    void whenSaved_thenFindBySeatNumberAndTripID() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);

        Ticket found = ticketRepository.findBySeatNumberAndTripID(ticket.getSeatNumber(), ticket.getTripID());

        assertEquals(ticket.getSeatNumber(), found.getSeatNumber());
        assertEquals(ticket.getTripID(), found.getTripID());
        assertThat(found).isEqualTo(ticket);
    }

    @Test
    @DisplayName("Test deleting a ticket and then confirming it is not found when searching by it seat number and trip ID")
    void whenDelete_thenNotFoundBySeatNumberAndTripID() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);
        ticketRepository.delete(ticket);

        Ticket found = ticketRepository.findBySeatNumberAndTripID(ticket.getSeatNumber(), ticket.getTripID());

        assertThat(found).isNull();
    }

    @Test
    @DisplayName("Test deleting all tickets and then trying to find any")
    void whenDeleteAll_thenEmpty() {
        Ticket ticket = new Ticket();
        ticket.setPrice("10€");
        ticket.setTripID(1);
        ticket.setSeatNumber(1);

        ticketRepository.save(ticket);
        ticketRepository.deleteAll();

        assertThat(ticketRepository.findAll()).isEmpty();
    }




    
}
