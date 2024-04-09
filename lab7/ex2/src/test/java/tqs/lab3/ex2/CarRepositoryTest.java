package tqs.lab3.ex2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private TestEntityManager testEntityManager;


    @Test
    void whenValidId_thenCarShouldBeFound() {
        Car tesla = new Car("Tesla", "Model S");
        testEntityManager.persistAndFlush(tesla);

        Car found = carRepository.findByCarId(tesla.getCarId());

        assertThat(found.getMaker()).isEqualTo(tesla.getMaker());
    }

    @Test
    void whenInvalidId_thenCarShouldNotBeFound() {
        Car fromDb = carRepository.findByCarId(1000L);
        assertThat(fromDb).isNull();
    }

    @Test 
    void whenFindAll_thenReturnAllCars() {
        Car tesla = new Car("Tesla", "Model S");
        Car audi = new Car("Audi", "A4");
        Car bmw = new Car("BMW", "M3");

        testEntityManager.persistAndFlush(tesla);
        testEntityManager.persistAndFlush(audi);
        testEntityManager.persistAndFlush(bmw);

        assertThat(carRepository.findAll()).hasSize(3).extracting(Car::getMaker).containsOnly(tesla.getMaker(), audi.getMaker(), bmw.getMaker());
    }
    
}
