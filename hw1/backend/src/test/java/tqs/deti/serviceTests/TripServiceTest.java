package tqs.deti.serviceTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import tqs.deti.services.TripService;

import java.util.Arrays;

import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.junit.jupiter.MockitoExtension;

import tqs.deti.repositories.TripRepository;
import tqs.deti.models.Trip;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @InjectMocks
    private TripService tripService;


    @Test
    public void testGetDates() {
        when(tripRepository.findDates()).thenReturn(Arrays.asList("2021-05-01", "2021-05-02", "2021-05-03"));

        assertThat(tripService.getDates())
        .contains("2021-05-01", "2021-05-02", "2021-05-03")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findDates();


    }

    @Test
    public void testGetOrigins() {
        when(tripRepository.findOrigins()).thenReturn(Arrays.asList("Aveiro", "Porto", "Lisboa"));

        assertThat(tripService.getOrigins())
        .contains("Aveiro", "Porto", "Lisboa")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findOrigins();

    }

    
    @Test
    public void testGetDestinations() {
        when(tripRepository.findDestinations()).thenReturn(Arrays.asList("Aveiro", "Porto", "Lisboa"));

        assertThat(tripService.getDestinations())
        .contains("Aveiro", "Porto", "Lisboa")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findDestinations();

    }

    @Test
    public void testListTripsFiltered() {
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01")).thenReturn(Arrays.asList());

        assertThat(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "EUR"))
        .hasSize(0);
        
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01");

    }

    @Test
    public void testListTripsFilteredWithResults() {
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01")).thenReturn(Arrays.asList(new Trip(), new Trip()));

        assertThat(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "EUR"))
        .hasSize(2);
        
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01");

    }

    @Test
    public void testGetTripWithoutResults() {
        when(tripRepository.findById(1)).thenReturn(null);

        assertThat(tripService.getTrip(1, "EUR")).isNull();
        
        verify(tripRepository, times(1)).findById(1);

    }

    @Test
    public void testGetTripWithResults() {

        when(tripRepository.findById(1)).thenReturn(new Trip());

        assertThat(tripService.getTrip(1, "EUR")).isNotNull();
        
        verify(tripRepository, times(1)).findById(1);

    }

    @Test
    public void testListTrips() {
        
        when(tripRepository.findAll()).thenReturn(Arrays.asList(new Trip(), new Trip()));

        assertThat(tripService.listTrips())
        .hasSize(2);
        
        verify(tripRepository, times(1)).findAll();

    }





    
}