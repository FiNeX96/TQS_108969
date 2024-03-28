package tqs9.hw1.services;
import tqs9.hw1.models.*;
import tqs9.hw1.repositories.TripRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TripService {

    @Autowired
    private TripRepository tripsRepository;


    public boolean tripExists(int tripID)
    {
        return tripsRepository.existsById(tripID);
    }

    public Trip getTrip(int tripID)
    {
        return tripsRepository.findById(tripID);
    }

    public List<Trip> listTrips() {
        return tripsRepository.findAll();
    }

      
    public List<Trip> listTripsFiltered(String origin, String destination, String date) {
        return tripsRepository.findByOriginAndDestinationAndDate(origin, destination, date);
    }

    public List<String> getDates() {
        return tripsRepository.findDates();
    }

    public List<String> getOrigins() {
        return tripsRepository.findOrigins();
    }

    public List<String> getDestinations() {
        return tripsRepository.findDestinations();
    }
    


}