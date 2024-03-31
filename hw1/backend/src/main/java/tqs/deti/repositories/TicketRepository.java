package tqs.deti.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tqs.deti.models.Ticket;
import java.util.List;


@Repository 
public interface TicketRepository extends JpaRepository<Ticket, Integer>{

    public Ticket findById(int id);

    public List<Ticket> findByTripID(int tripID);

    public Ticket findBySeatNumberAndTripID(int seatNumber, int tripID);


    
}
