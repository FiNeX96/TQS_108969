package tqs.deti.services;

import tqs.deti.models.Trip;
import tqs.deti.repositories.TripRepository;
import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripService.class);
    

    
    private TripRepository tripsRepository;

    
    CurrencyExchangeService currencyExchangeService;

    @Autowired
    public TripService(TripRepository tripsRepository, CurrencyExchangeService currencyExchangeService) {
        this.tripsRepository = tripsRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public boolean tripExists(int tripID) {
        return tripsRepository.existsById(tripID);
    }

    public Trip getTrip(int tripID, String currency) {

        Trip trip = tripsRepository.findById(tripID);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trip;
        }

        double exchange_rate = 1.0;

        try {
            System.out.println("Exchanging currency from EUR to " + currency);
            exchange_rate = currencyExchangeService.exchange("EUR", currency);
        } catch (Exception e) {

        }

        trip.setPrice(trip.getPrice() * exchange_rate);

        logger.info("Trip with id " + tripID + " requested" + " in currency " + currency);

        return trip;
    }

    public List<Trip> listTrips() {
        return tripsRepository.findAll();
    }

    public List<Trip> listTripsFiltered(String origin, String destination, String date, String currency) {

        List<Trip> trips = new ArrayList<Trip>();
        trips = tripsRepository.findByOriginAndDestinationAndDate(origin, destination, date);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trips;
        }

        double exchange_rate = 1.0;

        try {
            exchange_rate = currencyExchangeService.exchange("EUR", currency);
        } catch (Exception e) {
        }

        for (Trip trip : trips) {
            trip.setPrice(trip.getPrice() * exchange_rate);
        }

        logger.info("List of trips requested ");

        return trips;
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