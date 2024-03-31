package tqs.deti.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tqs.deti.models.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus, Integer>{

    public Bus findById(int id);

    public Bus findByName(String name);

    public void deleteById(int id);

    public void deleteByName(String name);

    public void deleteAll();
    
}
