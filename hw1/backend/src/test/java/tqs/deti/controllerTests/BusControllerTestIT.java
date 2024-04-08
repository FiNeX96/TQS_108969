package tqs.deti.controllerTests;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tqs.deti.repositories.BusRepository;
import tqs.deti.models.Bus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties="spring.profiles.active=test")
@AutoConfigureTestDatabase
class BusControllerTestIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BusRepository busRepository;

    @Test
    @DisplayName("Test saving a bus ")
    void whenPostBus_thenCreateBus() {
        Bus bus = new Bus();
        bus.setName("bus bue fixe");
        bus.setTotalSeats(50);
        ResponseEntity<Bus> response = restTemplate.postForEntity("/bus/add", bus, Bus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("bus bue fixe");
        assertThat(response.getBody().getTotalSeats()).isEqualTo(50);
    }

    @Test
    @DisplayName("Test getting all buses")
     void whenHaveBuses_thenGetBuses() {
        Bus bus = new Bus();
        bus.setName("bus bue fixe");
        bus.setTotalSeats(50);
        Bus bus2 = new Bus();
        bus2.setName("bus bue fixe 2");
        bus2.setTotalSeats(39);
        busRepository.saveAndFlush(bus);
        busRepository.saveAndFlush(bus2);
        ResponseEntity<Bus[]> response = restTemplate.getForEntity("/bus/list", Bus[].class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody()[0].getName()).isEqualTo("bus bue fixe");
        assertThat(response.getBody()[0].getTotalSeats()).isEqualTo(50);
    }

    @Test
    @DisplayName("Test getting a bus by ID")
    void whenHaveABus_getABus() {
        Bus bus = new Bus();
        bus.setName("bus bue fixe");
        bus.setTotalSeats(50);
        busRepository.saveAndFlush(bus);
        ResponseEntity<Bus> response = restTemplate.getForEntity("/bus/get?id=1", Bus.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getName()).isEqualTo("bus bue fixe");
        assertThat(response.getBody().getTotalSeats()).isEqualTo(50);
    }

}
