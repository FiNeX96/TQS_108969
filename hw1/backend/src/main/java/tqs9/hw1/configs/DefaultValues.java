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

    }
}
