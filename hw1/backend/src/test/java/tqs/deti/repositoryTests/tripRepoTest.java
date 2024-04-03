package tqs.deti.repositoryTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.deti.repositories.TripRepository;
import tqs.deti.models.Trip;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;


@DataJpaTest
public class tripRepoTest {

    @Autowired
    private TripRepository tripRepository;

    @Test
    void whenSaved_thenFindById() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);

        Trip found = tripRepository.findById(trip.getId());

        assertEquals(trip.getId(), found.getId());
        assertThat(found).isEqualTo(trip);
    }

    @Test
    void whenDelete_thenNotFound() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);
        tripRepository.delete(trip);

        Trip found = tripRepository.findById(trip.getId());

        assertThat(found).isNull();
    }

    @Test
    void whenSaved_thenFindByOrigin() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);

        Trip found = tripRepository.findByOrigin(trip.getOrigin()).get(0);

        assertEquals(trip.getOrigin(), found.getOrigin());
        assertThat(found).isEqualTo(trip);
    }

    @Test
    void whenDeleteByOrigin_thenNotFound() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);
        tripRepository.deleteByOrigin(trip.getOrigin());

        List<Trip> found = tripRepository.findByOrigin(trip.getOrigin());

        assertThat(found).isEmpty();
    }

    @Test
    void whenDeleteAll_thenEmpty() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);
        tripRepository.deleteAll();

        assertThat(tripRepository.findAll()).isEmpty();
    }

    @Test 
    void whenSaved_thenFindByDestination() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);

        Trip found = tripRepository.findByDestination(trip.getDestination()).get(0);

        assertEquals(trip.getDestination(), found.getDestination());
        assertThat(found).isEqualTo(trip);
    }

    @Test
    void whenDeleteByDestination_thenNotFound() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);
        tripRepository.deleteByDestination(trip.getDestination());

        List<Trip> found = tripRepository.findByDestination(trip.getDestination());

        assertThat(found).isEmpty();
    }

    @Test
    void whenSaved_thenFindByTime() {
        Trip trip = new Trip();
        trip.setOrigin("Aveiro");
        trip.setDestination("Porto");
        trip.setTime("12:00");
        trip.setBusID(1);

        tripRepository.save(trip);

        Trip found = tripRepository.findByTime(trip.getTime()).get(0);

        assertEquals(trip.getTime(), found.getTime());
        assertThat(found).isEqualTo(trip);
    }
    
}