package tqs.deti.hw1.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.deti.hw1.models.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer>{

    public Bus findById(int id);
    
}
