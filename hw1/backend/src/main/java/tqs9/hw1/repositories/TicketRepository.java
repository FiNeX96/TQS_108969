package tqs9.hw1.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs9.hw1.models.*;
import java.util.ArrayList;

@Repository 
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    public Ticket findById(int id);

    public ArrayList<Ticket> findByTripID(int tripID);

    public Ticket findBySeatNumberAndTripID(int seatNumber, int tripID);


    
}
