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
import tqs.deti.services.CurrencyExchangeService;

@ExtendWith(MockitoExtension.class)
class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private CurrencyExchangeService currencyExchangeService;

    @InjectMocks
    private TripService tripService;


    @Test
     void testGetDates() {
        when(tripRepository.findDates()).thenReturn(Arrays.asList("2021-05-01", "2021-05-02", "2021-05-03"));

        assertThat(tripService.getDates())
        .contains("2021-05-01", "2021-05-02", "2021-05-03")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findDates();


    }

    @Test
     void testGetOrigins() {
        when(tripRepository.findOrigins()).thenReturn(Arrays.asList("Aveiro", "Porto", "Lisboa"));

        assertThat(tripService.getOrigins())
        .contains("Aveiro", "Porto", "Lisboa")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findOrigins();

    }

    
    @Test
     void testGetDestinations() {
        when(tripRepository.findDestinations()).thenReturn(Arrays.asList("Aveiro", "Porto", "Lisboa"));

        assertThat(tripService.getDestinations())
        .contains("Aveiro", "Porto", "Lisboa")
        .hasSize(3);
        
        verify(tripRepository, times(1)).findDestinations();

    }

    @Test
     void testListTripsFiltered() throws Exception {
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01")).thenReturn(Arrays.asList());

        assertThat(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "EUR"))
        .isEmpty();
        
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01");

    }

    @Test
     void testListTripsFilteredWithResults() throws Exception {
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01")).thenReturn(Arrays.asList(new Trip(), new Trip()));

        assertThat(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "EUR"))
        .hasSize(2);
        
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01");

    }

    @Test
     void testGetTripWithoutResults() {
        when(tripRepository.findById(1)).thenReturn(null);

        assertThat(tripService.getTrip(1, "EUR")).isNull();
        
        verify(tripRepository, times(1)).findById(1);

    }

    @Test
     void testGetTripWithResults() {

        when(tripRepository.findById(1)).thenReturn(new Trip());

        assertThat(tripService.getTrip(1, "EUR")).isNotNull();
        
        verify(tripRepository, times(1)).findById(1);

    }

    @Test
     void testListTrips() {
        
        when(tripRepository.findAll()).thenReturn(Arrays.asList(new Trip(), new Trip()));

        assertThat(tripService.listTrips())
        .hasSize(2);
        
        verify(tripRepository, times(1)).findAll();

    }


    @Test
    public void testGetTripWithoutEuro() throws Exception {
      Trip trip = new Trip();
      trip.setPrice(10.0);
      when(tripRepository.findById(1)).thenReturn(trip);
      // Mock the currency exchange to return a value greater than 1
      when(currencyExchangeService.exchange("EUR", "USD")).thenReturn(1.2);
    
      assertThat(tripService.getTrip(1, "USD").getPrice()).isEqualTo(12.0);
    
      verify(tripRepository, times(1)).findById(1);
    }

    @Test
    void testTripsFilteredWithCurrencyExchange() throws Exception {
        Trip trip = new Trip();
        trip.setPrice(10.0);
        when(tripRepository.findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01")).thenReturn(Arrays.asList(trip));
        when(currencyExchangeService.exchange("EUR", "USD")).thenReturn(1.2);
        // rate may change, so we need to use a range
        assertThat(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "USD").get(0).getPrice()).isEqualTo(12.0);
        
        verify(tripRepository, times(1)).findByOriginAndDestinationAndDate("Aveiro", "Porto", "2021-05-01");

    }


    






    
}
