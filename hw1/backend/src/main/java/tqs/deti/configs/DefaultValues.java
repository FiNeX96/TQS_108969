package tqs.deti.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import tqs.deti.models.Bus;
import tqs.deti.models.Seat;
import tqs.deti.models.Trip;
import tqs.deti.repositories.BusRepository;
import tqs.deti.repositories.TripRepository;


@Component
@Generated
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "dev")
public class DefaultValues implements ApplicationRunner {

    private static final  Logger logger = LoggerFactory.getLogger(DefaultValues.class);

    
    private BusRepository busRepository;

    
    private TripRepository tripRepository;

    @Autowired
    public DefaultValues(BusRepository busRepository, TripRepository tripRepository) {
        this.busRepository = busRepository;
        this.tripRepository = tripRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        logger.info("Creating default values");

        String premium = "premium";
        String lisboa = "Lisboa";

        Bus bus1 = new Bus();
        bus1.setTotalSeats(50);
        bus1.setName("Ganda Autocarro");

        int bus1ID = busRepository.save(bus1).getId();

        Bus bus2 = new Bus();
        bus2.setTotalSeats(37);
        bus2.setName("Ganda Autocarro 2");

        int bus2ID = busRepository.save(bus2).getId();

        Trip trip1 = new Trip();
        trip1.setOrigin("Aveiro");
        trip1.setDestination("Porto");
        trip1.setBusID(bus1ID);
        trip1.setPrice(10.0);
        trip1.setDate("2021-03-01");
        trip1.setTime("10:00");

        ArrayList<Seat> seatsBus1 = new ArrayList<>(bus1.getTotalSeats());

        for (int i = 0; i < bus1.getTotalSeats(); i++) {
            seatsBus1.add(new Seat()); // Create and add a new Seat object
        }

        // make seats 1 to 5 premium

        for (int i = 1; i <= 5; i++) {
            seatsBus1.get(i).setSeatType(premium);
        }

        trip1.setSeats(seatsBus1);

        Trip trip2 = new Trip();
        trip2.setOrigin("Aveiro");
        trip2.setDestination(lisboa);
        trip2.setBusID(bus2ID);
        trip2.setPrice(15.99);
        trip2.setDate("2021-03-01");
        trip2.setTime("15:00");

        ArrayList<Seat> seatsBus2 = new ArrayList<>(bus2.getTotalSeats());

        for (int i = 0; i < bus2.getTotalSeats(); i++) {
            seatsBus2.add(new Seat()); // Create and add a new Seat object
        }

        for (int i = 6; i <= 13; i++) {
            seatsBus2.get(i).setSeatType(premium);
        }

        trip1.setSeats(seatsBus1);
        trip2.setSeats(seatsBus2);

        tripRepository.save(trip1);
        tripRepository.save(trip2);

        // Create additional buses
        Bus bus3 = new Bus();
        bus3.setTotalSeats(45);
        bus3.setName("Ganda Autocarro 3");
        int bus3ID = busRepository.save(bus3).getId();

        Bus bus4 = new Bus();
        bus4.setTotalSeats(40);
        bus4.setName("Ganda Autocarro 4");
        int bus4ID = busRepository.save(bus4).getId();

        // Create additional trips
        Trip trip3 = new Trip();
        trip3.setOrigin("Porto");
        trip3.setDestination(lisboa);
        trip3.setBusID(bus3ID);
        trip3.setPrice(12.0);
        trip3.setDate("2021-03-02");
        trip3.setTime("09:00");

        ArrayList<Seat> seatsBus3 = new ArrayList<>(bus3.getTotalSeats());
        for (int i = 0; i < bus3.getTotalSeats(); i++) {
            seatsBus3.add(new Seat()); // Create and add a new Seat object
        }
        for (int i = 1; i <= 5; i++) {
            seatsBus3.get(i).setSeatType(premium);
        }
        trip3.setSeats(seatsBus3);

        Trip trip4 = new Trip();
        trip4.setOrigin(lisboa);
        trip4.setDestination("Faro");
        trip4.setBusID(bus4ID);
        trip4.setPrice(18.99);
        trip4.setDate("2021-03-02");
        trip4.setTime("14:00");

        ArrayList<Seat> seatsBus4 = new ArrayList<>(bus4.getTotalSeats());
        for (int i = 0; i < bus4.getTotalSeats(); i++) {
            seatsBus4.add(new Seat()); // Create and add a new Seat object
        }
        for (int i = 6; i <= 13; i++) {
            seatsBus4.get(i).setSeatType(premium);
        }
        trip4.setSeats(seatsBus4);

        // Save the additional trips
        tripRepository.save(trip3);
        tripRepository.save(trip4);

        // Create additional buses for trips 5 to 10
        Bus bus5 = new Bus();
        bus5.setTotalSeats(50);
        bus5.setName("Ganda Autocarro 5");
        int bus5ID = busRepository.save(bus5).getId();

        Bus bus6 = new Bus();
        bus6.setTotalSeats(45);
        bus6.setName("Ganda Autocarro 6");
        int bus6ID = busRepository.save(bus6).getId();

        // Create trips 5 to 10
        for (int tripNumber = 5; tripNumber <= 10; tripNumber++) {
            Trip trip = new Trip();
            trip.setOrigin("Origin " + tripNumber);
            trip.setDestination("Destination " + tripNumber);
            trip.setBusID(tripNumber % 2 == 0 ? bus6ID : bus5ID); // Alternate buses
            trip.setPrice(10.0 * tripNumber);
            trip.setDate("2021-03-" + (tripNumber + 1)); // Adjust date accordingly
            trip.setTime("10:00");

            ArrayList<Seat> seats = new ArrayList<>(bus5.getTotalSeats());
            for (int i = 0; i < bus5.getTotalSeats(); i++) {
                seats.add(new Seat()); // Create and add a new Seat object
            }
            // Mark seats 1 to 5 as premium for even trips, 6 to 13 for odd trips
            for (int i = 1; i <= 5; i++) {
                seats.get(i).setSeatType(tripNumber % 2 == 0 ? premium : "normal");
            }
            trip.setSeats(seats);

            tripRepository.save(trip);
        }

    }
}
