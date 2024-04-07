package tqs.deti.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;


import java.util.Arrays;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;

import tqs.deti.services.BusService;
import tqs.deti.models.Bus;

import tqs.deti.repositories.BusRepository;

@ExtendWith(MockitoExtension.class)
class BusServiceTest {

    @Mock
    private BusRepository busRepository;

    @InjectMocks
    private BusService busService;

    @Test
    void testGetBusById() {

        Bus bus = new Bus();
        bus.setId(1);
        bus.setName("autocarro 1");
        bus.setTotalSeats(50);

        when(busRepository.findById(1)).thenReturn(bus);

        assertThat(busService.getBus(1)).isEqualTo(bus);

        verify(busRepository, times(1)).findById(1);

    }

    @Test

    void testFindAllBus() {

        Bus bus1 = new Bus();
        bus1.setId(1);
        bus1.setName("autocarro 1");
        bus1.setTotalSeats(50);

        Bus bus2 = new Bus();
        bus2.setId(2);
        bus2.setName("autocarro 2");
        bus2.setTotalSeats(50);

        when(busRepository.findAll()).thenReturn(Arrays.asList(bus1, bus2));

        assertThat(busService.findAll())
                .contains(bus1, bus2)
                .hasSize(2);

        verify(busRepository, times(1)).findAll();

    }

    @Test

    void testAddBus() {

        Bus bus = new Bus();
        bus.setId(1);
        bus.setName("autocarro 1");
        bus.setTotalSeats(50);

        when(busRepository.save(bus)).thenReturn(bus);

        assertThat(busService.addBus(bus)).isEqualTo(bus);

        verify(busRepository, times(1)).save(bus);

    }

}
