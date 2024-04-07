package tqs.deti.services;

import tqs.deti.models.Ticket;
import tqs.deti.models.Trip;
import tqs.deti.repositories.TicketRepository;
import tqs.deti.repositories.TripRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketService {

    private static final Logger logger = LoggerFactory.getLogger(TicketService.class);

    
    private TicketRepository ticketRepository;

    
    private TripRepository tripRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, TripRepository tripRepository) {
        this.ticketRepository = ticketRepository;
        this.tripRepository = tripRepository;
    }

    public boolean seatAvailableForTrip(int tripID, int seatID) {
        logger.info("Checking if seat {} is available for trip {}", seatID, tripID);
        return ticketRepository.findBySeatNumberAndTripID(seatID, tripID) != null;
    }

    public List<Ticket> findTicketsByID(int tripID) {
        logger.info("Finding tickets for trip {}", tripID);
        return ticketRepository.findByTripID(tripID);
    }

    public List<Ticket> findAllTickets() {
        logger.info("Finding all tickets");
        return ticketRepository.findAll();
    }

    public Ticket buyTicket(Ticket ticket) {

        try {
            ticketRepository.save(ticket);

            Trip trip = tripRepository.findById(ticket.getTripID());

            trip.getSeats().get(ticket.getSeatNumber()-1).setTaken(true);

            tripRepository.save(trip);


            ticketRepository.save(ticket);

            logger.info("Ticket purchased with id {}", ticket.getId());

            return ticket;

        } catch (Exception e) {
            logger.info("Error purchasing ticket: {}", e.getMessage());
            return null;
        }

    }

}
