package tqs.lab3.ex2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
 class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @Test
    void TestFindAll() throws Exception {
        Car tesla = new Car("Tesla", "Model S");
        Car audi = new Car("Audi", "A4");
        Car bmw = new Car("BMW", "M3");

        when(carRepository.findAll()).thenReturn(Arrays.asList(tesla, audi, bmw));

        assertThat(carManagerService.getAllCars())
                .hasSize(3)
                .extracting(Car::getMaker)
                .contains(tesla.getMaker(), audi.getMaker(), bmw.getMaker());

        verify(carRepository, times(1)).findAll();

    }

    @Test
    void TestSaveCar() throws Exception {
        Car tesla = new Car("Tesla", "Model S");

        when(carRepository.save(Mockito.any())).thenReturn(tesla);

        assertThat(carManagerService.save(tesla)).isEqualTo(tesla);
    }

    @Test
    void TestGetCarDetails() throws Exception {
        Car tesla = new Car("Tesla", "Model S");
        tesla.setCarId(1L);

        when(carRepository.findByCarId(1L)).thenReturn(tesla);

        assertThat(carManagerService.getCarDetails(1L)).contains(tesla);

    }

}
