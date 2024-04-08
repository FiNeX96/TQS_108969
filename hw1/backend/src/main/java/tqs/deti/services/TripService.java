package tqs.deti.services;

import tqs.deti.models.Trip;
import tqs.deti.repositories.TripRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TripService {

    private static final Logger logger = LoggerFactory.getLogger(TripService.class);

    private TripRepository tripsRepository;

    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    public TripService(TripRepository tripsRepository, CurrencyExchangeService currencyExchangeService) throws Exception {
        this.tripsRepository = tripsRepository;
        this.currencyExchangeService = currencyExchangeService;
    }

    public boolean tripExists(int tripID) {
        return tripsRepository.existsById(tripID);
    }

    public Trip getTrip(int tripID, String currency) throws Exception {

        Trip trip = tripsRepository.findById(tripID);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trip;
        }

        double exchangeRate = 1.0;

        
        logger.info("Exchanging currency from EUR to {}", currency);
        exchangeRate = currencyExchangeService.exchange("EUR", currency);


        trip.setPrice(trip.getPrice() * exchangeRate);

        logger.info("Trip with id {} requested in currency {}", tripID, currency);

        return trip;
    }

    public List<Trip> listTrips() {
        return tripsRepository.findAll();
    }

    public List<Trip> listTripsFiltered(String origin, String destination, String date, String currency)
            throws Exception {

        List<Trip> trips;
        trips = tripsRepository.findByOriginAndDestinationAndDate(origin, destination, date);

        if (currency == null || currency.equals("EUR")) { // EUR is base currency, no need to exchange
            return trips;
        }

        double exchangeRate = 1.0;

        logger.info("Exchanging currency from EUR to {}", currency);
        exchangeRate = currencyExchangeService.exchange("EUR", currency);

        for (Trip trip : trips) {
            trip.setPrice(trip.getPrice() * exchangeRate);
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