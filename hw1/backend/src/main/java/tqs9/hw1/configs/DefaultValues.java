package tqs9.hw1.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

import tqs9.hw1.models.*;
import tqs9.hw1.repositories.*;
import tqs9.hw1.services.*;

@Component
public class DefaultValues implements ApplicationRunner {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private TripRepository tripRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Bus bus1 = new Bus();
        bus1.setTotal_seats(50);
        bus1.setName("Ganda Autocarro");

        int bus1_id = busRepository.save(bus1).getId();

        Bus bus2 = new Bus();
        bus2.setTotal_seats(37);
        bus2.setName("Ganda Autocarro 2");

        int bus2_id = busRepository.save(bus2).getId();

        Trip trip1 = new Trip();
        trip1.setOrigin("Aveiro");
        trip1.setDestination("Porto");
        trip1.setBusID(bus1_id);
        trip1.setPrice(10.0);
        trip1.setDate("2021-03-01");
        trip1.setTime("10:00");

        ArrayList<Seat> seats_bus1 = new ArrayList<Seat>(bus1.getTotal_seats());

        for (int i = 0; i < bus1.getTotal_seats(); i++) {
            seats_bus1.add(new Seat()); // Create and add a new Seat object
        }

        // make seats 1 to 5 premium

        for (int i = 1; i <= 5; i++) {
            seats_bus1.get(i).setSeatType("premium");
            ;
        }

        trip1.setSeats(seats_bus1);

        Trip trip2 = new Trip();
        trip2.setOrigin("Aveiro");
        trip2.setDestination("Lisboa");
        trip2.setBusID(bus2_id);
        trip2.setPrice(15.99);
        trip2.setDate("2021-03-01");
        trip2.setTime("15:00");

        ArrayList<Seat> seats_bus2 = new ArrayList<Seat>(bus2.getTotal_seats());

        for (int i = 0; i < bus2.getTotal_seats(); i++) {
            seats_bus2.add(new Seat()); // Create and add a new Seat object
        }

        for (int i = 6; i <= 13; i++) {
            seats_bus2.get(i).setSeatType("premium");
            ;
        }

        trip1.setSeats(seats_bus1);
        trip2.setSeats(seats_bus2);

        tripRepository.save(trip1);
        tripRepository.save(trip2);

        // Create additional buses
        Bus bus3 = new Bus();
        bus3.setTotal_seats(45);
        bus3.setName("Ganda Autocarro 3");
        int bus3_id = busRepository.save(bus3).getId();

        Bus bus4 = new Bus();
        bus4.setTotal_seats(40);
        bus4.setName("Ganda Autocarro 4");
        int bus4_id = busRepository.save(bus4).getId();

        // Create additional trips
        Trip trip3 = new Trip();
        trip3.setOrigin("Porto");
        trip3.setDestination("Lisboa");
        trip3.setBusID(bus3_id);
        trip3.setPrice(12.0);
        trip3.setDate("2021-03-02");
        trip3.setTime("09:00");

        ArrayList<Seat> seats_bus3 = new ArrayList<Seat>(bus3.getTotal_seats());
        for (int i = 0; i < bus3.getTotal_seats(); i++) {
            seats_bus3.add(new Seat()); // Create and add a new Seat object
        }
        for (int i = 1; i <= 5; i++) {
            seats_bus3.get(i).setSeatType("premium");
        }
        trip3.setSeats(seats_bus3);

        Trip trip4 = new Trip();
        trip4.setOrigin("Lisboa");
        trip4.setDestination("Faro");
        trip4.setBusID(bus4_id);
        trip4.setPrice(18.99);
        trip4.setDate("2021-03-02");
        trip4.setTime("14:00");

        ArrayList<Seat> seats_bus4 = new ArrayList<Seat>(bus4.getTotal_seats());
        for (int i = 0; i < bus4.getTotal_seats(); i++) {
            seats_bus4.add(new Seat()); // Create and add a new Seat object
        }
        for (int i = 6; i <= 13; i++) {
            seats_bus4.get(i).setSeatType("premium");
        }
        trip4.setSeats(seats_bus4);

        // Save the additional trips
        tripRepository.save(trip3);
        tripRepository.save(trip4);

        // Create additional buses for trips 5 to 10
        Bus bus5 = new Bus();
        bus5.setTotal_seats(50);
        bus5.setName("Ganda Autocarro 5");
        int bus5_id = busRepository.save(bus5).getId();

        Bus bus6 = new Bus();
        bus6.setTotal_seats(45);
        bus6.setName("Ganda Autocarro 6");
        int bus6_id = busRepository.save(bus6).getId();

        // Create trips 5 to 10
        for (int tripNumber = 5; tripNumber <= 10; tripNumber++) {
            Trip trip = new Trip();
            trip.setOrigin("Origin " + tripNumber);
            trip.setDestination("Destination " + tripNumber);
            trip.setBusID(tripNumber % 2 == 0 ? bus6_id : bus5_id); // Alternate buses
            trip.setPrice(10.0 * tripNumber);
            trip.setDate("2021-03-" + (tripNumber + 1)); // Adjust date accordingly
            trip.setTime("10:00");

            ArrayList<Seat> seats = new ArrayList<Seat>(bus5.getTotal_seats());
            for (int i = 0; i < bus5.getTotal_seats(); i++) {
                seats.add(new Seat()); // Create and add a new Seat object
            }
            // Mark seats 1 to 5 as premium for even trips, 6 to 13 for odd trips
            for (int i = 1; i <= 5; i++) {
                seats.get(i).setSeatType(tripNumber % 2 == 0 ? "premium" : "normal");
            }
            trip.setSeats(seats);

            tripRepository.save(trip);
        }

    }
}
