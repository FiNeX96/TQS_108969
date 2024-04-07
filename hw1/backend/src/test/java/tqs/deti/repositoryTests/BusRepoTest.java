package tqs.deti.repositoryTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import tqs.deti.models.Bus;
import tqs.deti.repositories.BusRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BusRepoTest {

    @Autowired
    private BusRepository busRepository;

    @Test
    void whenSaved_thenFindById() {
        Bus bus = new Bus();
        bus.setName("Bus 1");
        bus.setTotalSeats(39);

        busRepository.save(bus);

        Bus found = busRepository.findById(bus.getId());

        assertEquals(bus.getId(), found.getId());
        assertThat(found).isEqualTo(bus);
    }

    @Test
    void whenDelete_thenNotFound() {
        Bus bus = new Bus();
        bus.setName("Bus 2");
        bus.setTotalSeats(39);

        busRepository.save(bus);
        busRepository.delete(bus);

        Bus found = busRepository.findById(bus.getId());

        assertThat(found).isNull();
    }

    @Test
    void whenSaved_thenFindByName() {
        Bus bus = new Bus();
        bus.setName("Bus 3");
        bus.setTotalSeats(39);

        busRepository.save(bus);

        Bus found = busRepository.findByName(bus.getName());

        assertEquals(bus.getName(), found.getName());
        assertThat(found).isEqualTo(bus);
    }

    @Test
    void whenDeleteByName_thenNotFound() {
        Bus bus = new Bus();
        bus.setName("Bus 4");
        bus.setTotalSeats(39);

        busRepository.save(bus);
        busRepository.deleteByName(bus.getName());

        Bus found = busRepository.findByName(bus.getName());

        assertThat(found).isNull();
    }

    @Test
    void whenDeleteAll_thenEmpty() {
        Bus bus = new Bus();

        bus.setName("Bus 5");
        bus.setTotalSeats(39);

        busRepository.save(bus);
        busRepository.deleteAll();

        assertThat(busRepository.findAll()).isEmpty();
    }

    @Test
    void whenVariousBus_thenFindAll() {
        Bus bus1 = new Bus();

        bus1.setName("Bus 6");
        bus1.setTotalSeats(39);

        Bus bus2 = new Bus();

        bus2.setName("Bus 7");
        bus2.setTotalSeats(40);

        busRepository.save(bus1);
        busRepository.save(bus2);

        assertThat(busRepository.findAll()).contains(bus1, bus2);
    }

}
