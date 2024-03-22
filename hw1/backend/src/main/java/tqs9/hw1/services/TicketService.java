package tqs9.hw1.services;
import tqs9.hw1.models.*;
import tqs9.hw1.repositories.TicketRepository;
import tqs9.hw1.repositories.BusRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BusRepository busRepository;


    public boolean seatAvailableForTrip(int tripID, int seatID) {
        return ticketRepository.findBySeatNumberAndTripID(seatID, tripID) != null;
    }

   

    public List<Ticket> listTickets(int tripID) {
        return ticketRepository.findByTripID(tripID);
    }

   
    
}
