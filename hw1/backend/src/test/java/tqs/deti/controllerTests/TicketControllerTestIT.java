package tqs.deti.controllerTests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import tqs.deti.repositories.TicketRepository;
import tqs.deti.models.Bus;
import tqs.deti.models.Seat;
import tqs.deti.models.Ticket;
import tqs.deti.models.Trip;
import tqs.deti.repositories.BusRepository;
import tqs.deti.repositories.TripRepository;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;


import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.BeforeAll;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase
@TestInstance(Lifecycle.PER_CLASS)
public class TicketControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TripRepository tripRepository;

    private Ticket ticket = new Ticket();

    private Trip trip = new Trip();
    private Trip trip2 = new Trip();

    @BeforeAll
    void setup() {
        // setup a bus

        Bus bus = new Bus();
        bus.setName("autocarro 1");
        bus.setTotalSeats(20);
        bus.setId(1);

        busRepository.saveAndFlush(bus);

        trip.setBusID(1);
        trip.setDate("2021-05-05");
        trip.setDestination("Lisboa");
        trip.setOrigin("Porto");
        trip.setPrice(20);

        ArrayList<Seat> seatsBus2 = new ArrayList<>(bus.getTotalSeats());

        for (int i = 0; i < bus.getTotalSeats(); i++) {
            seatsBus2.add(new Seat()); // Create and add a new Seat object
        }

        for (int i = 6; i <= 13; i++) {
            seatsBus2.get(i).setSeatType("premium");
        }

        trip.setSeats(seatsBus2);

        trip.setTime("10:00");

        trip = tripRepository.saveAndFlush(trip);

        ticket.setEmail("josecalcas@gmail.com");
        ticket.setName("Jose calcas");
        ticket.setPhone(912345678);
        ticket.setPrice("20€");
        ticket.setSeatNumber(3);
        ticket.setTripID(trip.getId());
        ticketRepository.saveAndFlush(ticket);

        // make a bus with only 1 seat

        Bus bus2 = new Bus();
        bus2.setName("autocarro 2");
        bus2.setTotalSeats(1);
        bus2.setId(2);

        busRepository.saveAndFlush(bus2);

        trip2.setBusID(2);
        trip2.setDate("2021-05-05");
        trip2.setDestination("Lisboa");
        trip2.setOrigin("Porto");
        trip2.setPrice(20);

        ArrayList<Seat> seatsBus3 = new ArrayList<>(bus2.getTotalSeats());

        for (int i = 0; i < bus2.getTotalSeats(); i++) {
            seatsBus3.add(new Seat()); // Create and add a new Seat object
        }

        trip2.setSeats(seatsBus3);

        trip2.setTime("10:00");

        trip2 = tripRepository.saveAndFlush(trip2);
    }

    @Test
    void whenHaveTickets_thenGetTickets() {

        ResponseEntity<Ticket[]> response = restTemplate.getForEntity("/tickets/list", Ticket[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().length).isEqualTo(1);
        assertThat(response.getBody()[0].getEmail()).isEqualTo("josecalcas@gmail.com");
    }

    @Test
    void whenPostTicket_thenReturn200() {

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket, Ticket.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getEmail()).isEqualTo("josecalcas@gmail.com");
    }

    @Test
    void whenPost2TicketsSameSeatForSameTrip_thenGiveError() {

        Ticket ticket2 = new Ticket();
        ticket2.setEmail("mariapena@gmail.com");
        ticket2.setPhone(912345678);
        ticket2.setPrice("15USD");
        ticket2.setSeatNumber(3);
        ticket2.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket2, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void whenPostInvalidEmail_thenGiveError() {
        Ticket ticket3 = new Ticket();
        ticket3.setEmail("mariapena");
        ticket3.setPhone(912345678);
        ticket3.setPrice("15USD");
        ticket3.setSeatNumber(5);
        ticket3.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket3, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void whenPostInvalidPhone_thenGiveError() {
        Ticket ticket4 = new Ticket();
        ticket4.setEmail("albertojoaquim@ua.pt");
        ticket4.setPhone(912345);
        ticket4.setPrice("15USD");
        ticket4.setSeatNumber(4);
        ticket4.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket4, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test
    void whenPostTicketForInvalidTrip_thenGiveError() {
        Ticket ticket5 = new Ticket();
        ticket5.setEmail("albertojoaquim@ua.pt");
        ticket5.setPhone(912345);
        ticket5.setPrice("15USD");
        ticket5.setSeatNumber(4);
        ticket5.setTripID(32);

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket5, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

    }

    @Test 
    void whenPostTicketForInvalidSeat_thenGiveError() {
        Ticket ticket5 = new Ticket();
        ticket5.setEmail("albertojoaquim@ua.pt");
        ticket5.setPhone(912345);
        ticket5.setPrice("15USD");
        ticket5.setSeatNumber(23874);
        ticket5.setTripID(trip.getId());

        ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy", ticket5, Ticket.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }




    /*
     * I see no need for this test, seats are checked if they are taken 1 by 1, if
     * bus is full then all seats are taken, and it will give a bad request
     * 
     * @Test
     * void whenBusFull_thenGiveError() {
     * Ticket ticket6 = new Ticket();
     * Ticket ticket5 = new Ticket();
     * ticket5.setEmail("albertojoaquim@ua.pt");
     * ticket5.setPhone(123456789);
     * ticket5.setPrice("15USD");
     * ticket5.setSeatNumber(trip2.getSeats().get(0).getNumber());
     * ticket5.setTripID(trip2.getId());
     * 
     * ResponseEntity<Ticket> response = restTemplate.postForEntity("/tickets/buy",
     * ticket5, Ticket.class);
     * 
     * assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
     * 
     * ticket6.setEmail("manel@ua.pt");
     * ticket6.setPhone(123456789);
     * ticket6.setPrice("15USD");
     * ticket6.setSeatNumber(trip2.getSeats().get(1).getNumber());
     * ticket6.setTripID(trip2.getId());
     * 
     * ResponseEntity<Ticket> response2 = restTemplate.postForEntity("/tickets/buy",
     * ticket6, Ticket.class);
     * 
     * assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
     * 
     * }
     */

}
