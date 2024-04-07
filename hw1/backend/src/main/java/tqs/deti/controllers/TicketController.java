package tqs.deti.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;

import tqs.deti.services.TicketService;
import tqs.deti.services.BusService;
import tqs.deti.services.TripService;
import tqs.deti.models.Ticket;
import tqs.deti.models.Trip;
import tqs.deti.models.Seat;
import tqs.deti.models.Bus;
import tqs.deti.models.TicketData;
import tqs.deti.services.TicketFieldValidator;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/tickets")
public class TicketController {

    private static final Logger logger = LoggerFactory.getLogger(TicketController.class);

    
    private TicketService ticketService;

    
    private BusService busService;

    
    private TripService tripService;

    
    private TicketFieldValidator ticketFieldValidator;

    @Autowired
    public TicketController(TicketService ticketService, BusService busService, TripService tripService,
            TicketFieldValidator ticketFieldValidator) {
        this.ticketService = ticketService;
        this.busService = busService;
        this.tripService = tripService;
        this.ticketFieldValidator = ticketFieldValidator;
    }

    @PostMapping("/buy")
    public ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket) {

        logger.info(String.format("Ticket purchase requested for trip %d and seat %d", ticket.getTripID(), ticket.getSeatNumber()));

        if (!tripService.tripExists(ticket.getTripID())) {
            logger.info(String.format("Trip with id %d requested", ticket.getTripID()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        if (!ticketFieldValidator.validateEmail(ticket.getEmail())
                || !ticketFieldValidator.validatePhone(ticket.getPhone())) {
            logger.info(String.format("Failed to validate email and/or phone on ticket purchase %d", ticket.getId()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email or phone number");
        }

        Trip trip = tripService.getTrip(ticket.getTripID(), "EUR");

        if (trip == null) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip not found");
        }

        int givenSeatIndex = ticket.getSeatNumber() - trip.getSeats().get(0).getNumber();

        if (givenSeatIndex < 0 || givenSeatIndex >= trip.getSeats().size()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        ticket.setSeatNumber(givenSeatIndex + 1);

        Bus tripBus = busService.getBus(trip.getBusID());

        int lastTicketOfBus = trip.getSeats().get(0).getNumber() + trip.getSeats().size();
        int givenSeat = ticket.getSeatNumber();

        if (givenSeat > lastTicketOfBus
                || ticket.getSeatNumber() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid seat number");
        }

        List<Seat> seats = trip.getSeats();

        Seat seat = seats.get(givenSeatIndex);

        if (seat.isTaken()) {
            logger.info(String.format("Seat already taken on ticket purchase %d", ticket.getId()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seat already occupied");
        }

        // count number of seats taken

        int seatsTaken = 0;
        for (Seat s : seats) {
            if (s.isTaken()) {
                seatsTaken++;
            }
        }

        if (seatsTaken >= tripBus.getTotalSeats()) {
            logger.info(String.format("Bus full on ticket purchase %d", ticket.getId()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bus full");
        }

        Ticket t = ticketService.buyTicket(ticket);

        return ResponseEntity.ok(t);

    }

    @GetMapping("/list")
    public ResponseEntity<List<TicketData>> listTickets() {

        List<Ticket> tickets = ticketService.findAllTickets();
        // add the busID and time to each ticket

        // convert to TicketData
        List<TicketData> ticketData = new ArrayList<>();

        for (Ticket t : tickets) {
            TicketData td = new TicketData();
            td.setId(t.getId());
            td.setTripID(t.getTripID());
            td.setSeatNumber(t.getSeatNumber());
            td.setName(t.getName());
            td.setPhone(td.getPhone());
            td.setEmail(t.getEmail());
            td.setPrice(t.getPrice());
            td.setBusID(tripService.getTrip(t.getTripID(), "EUR").getBusID());
            td.setDate(tripService.getTrip(t.getTripID(), "EUR").getDate());
            td.setTime(tripService.getTrip(t.getTripID(), "EUR").getTime());
            td.setOrigin(tripService.getTrip(t.getTripID(), "EUR").getOrigin());
            td.setDestination(tripService.getTrip(t.getTripID(), "EUR").getDestination());
            ticketData.add(td);
        }

        return ResponseEntity.ok(ticketData);
    }

}
