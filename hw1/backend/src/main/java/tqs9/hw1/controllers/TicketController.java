package tqs9.hw1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.HttpStatus;

import tqs9.hw1.services.*;
import tqs9.hw1.models.*;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private BusService busService;

    @Autowired
    private TripService tripService;


    /* 

    @PostMapping("/buy")
    public @ResponseBody ResponseEntity<Ticket> buyTicket(@RequestBody Ticket ticket) {

        // check if this trip exists
        if (!tripService.tripExists(ticket.getTrip().getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Trip does not exist!");
        }

        // check if this seat is available
        if (ticketService.seatAvailableForTrip(ticket.getTrip().getId(), ticket.getSeatNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Seat is not available!");
        }

        // buy the ticket and return it
        Ticket newTicket = ticketService.buyTicket(ticket);
        if (newTicket != null) {
            return ResponseEntity.ok(newTicket);
        } else {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while buying the ticket!");
        }
    
    }

    */

    @GetMapping("/list")
    public ResponseEntity<List<Ticket>> listTickets(@RequestParam int tripID) {
        return ResponseEntity.ok(ticketService.listTickets(tripID));
    }

    
}
