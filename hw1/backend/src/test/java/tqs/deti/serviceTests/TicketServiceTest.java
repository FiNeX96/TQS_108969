package tqs.deti.serviceTests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import java.util.Arrays;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;

import tqs.deti.services.TicketService;
import tqs.deti.models.Bus;

import tqs.deti.repositories.TicketRepository;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    @DisplayName("Test seat available for trip")
    void testSeatAvailableForTrip() {
        when(ticketRepository.findBySeatNumberAndTripID(1, 1)).thenReturn(null);

        assertThat(ticketService.seatAvailableForTrip(1, 1)).isFalse();

        verify(ticketRepository, times(1)).findBySeatNumberAndTripID(1, 1);
    }

    @Test
    @DisplayName("Test finding tickets by ID")
    void testFindTicketsByID() {
        when(ticketRepository.findByTripID(1)).thenReturn(Arrays.asList());

        assertThat(ticketService.findTicketsByID(1)).isEmpty();

        verify(ticketRepository, times(1)).findByTripID(1);
    }

    @Test
    @DisplayName("Test finding all tickets")
    void testFindAllTickets() {
        when(ticketRepository.findAll()).thenReturn(Arrays.asList());

        assertThat(ticketService.findAllTickets()).isEmpty();

        verify(ticketRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test buying a null ticket")
    void testBuyNullTicket() {
        Bus bus = new Bus();
        bus.setId(1);
        bus.setName("autocarro 1");
        bus.setTotalSeats(50);

        when(ticketRepository.save(any())).thenReturn(null);

        assertThat(ticketService.buyTicket(null)).isNull();

        verify(ticketRepository, times(1)).save(any());
    }

}
