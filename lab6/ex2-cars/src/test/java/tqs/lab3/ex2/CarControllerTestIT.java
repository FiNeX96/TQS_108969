package tqs.lab3.ex2;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
@TestPropertySource( locations = "application-integrationtest.properties")
class CarControllerTestIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @BeforeEach
    public void setUp() {
        // cleanup the db
        carRepository.deleteAll();
    }

    @Test
    void whenValidInput_thenCreateCar() {
        Car tesla = new Car("Tesla", "Model S");
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/cars", tesla, Car.class);

        List<Car> found = carRepository.findAll();
        assertThat(found).extracting(Car::getMaker).containsOnly("Tesla");
    }

    @Test
    void WhenGetCars_thenReturnAllCars() {
        Car tesla = new Car("Tesla", "Model S");
        Car audi = new Car("Audi", "A4");
        Car bmw = new Car("BMW", "M3");

        carRepository.saveAndFlush(tesla);
        carRepository.saveAndFlush(audi);
        carRepository.saveAndFlush(bmw);

        ResponseEntity<Car[]> response = restTemplate.getForEntity("/api/cars", Car[].class);
        assertThat(response.getBody()).hasSize(3).extracting(Car::getMaker).containsOnly(tesla.getMaker(),
                audi.getMaker(), bmw.getMaker());
    }

    @Test
    void WhenGetCarsAndNoCars_thenReturnEmptyList() {
        ResponseEntity<Car[]> response = restTemplate.getForEntity("/api/cars", Car[].class);
        assertThat(response.getBody()).isEmpty();
    }
      
    @Test 
    void whenValidInput_thenGetCar() {
        Car tesla = new Car("Tesla", "Model S");
        Car audi = new Car("Audi", "A4");
        Car bmw = new Car("BMW", "M3");

        carRepository.saveAndFlush(tesla);
        carRepository.saveAndFlush(audi);
        carRepository.saveAndFlush(bmw);

        ResponseEntity<Car> response = restTemplate.getForEntity("/api/cars/" + tesla.getCarId(), Car.class);
        assertThat(response.getBody()).isEqualTo(tesla);
    }
    

}
