package tqs.deti.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tqs.deti.models.Trip;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

@Repository
public interface TripRepository extends JpaRepository<Trip, Integer>{

    public Trip findById(int id);

    @Query("SELECT t FROM Trip t WHERE (:origin IS NULL OR t.origin = :origin) AND (:destination IS NULL OR t.destination = :destination) AND (:date IS NULL OR t.date = :date)")
    List<Trip> findByOriginAndDestinationAndDate(@Param("origin") String origin, @Param("destination") String destination, @Param("date") String date);

    @Query("SELECT DISTINCT t.date FROM Trip t")
    List<String> findDates();

    @Query("SELECT DISTINCT t.origin FROM Trip t")
    List<String> findOrigins();

    @Query("SELECT DISTINCT t.destination FROM Trip t")
    List<String> findDestinations();

    
}
