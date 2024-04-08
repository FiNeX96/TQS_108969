package tqs.deti.controllerTests;

import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import tqs.deti.controllers.TripsController;
import tqs.deti.services.TripService;
import tqs.deti.models.Trip;
@WebMvcTest(controllers = TripsController.class, properties="spring.profiles.active=test")
class TripControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TripService tripService;

    @BeforeEach
    void setUp() throws Exception {
        
        when(tripService.getDates()).thenReturn(List.of("2021-05-01", "2021-05-02", "2021-05-03"));
        when(tripService.getOrigins()).thenReturn(List.of("Aveiro", "Porto", "Lisboa"));
        when(tripService.getDestinations()).thenReturn(List.of("Porto","Lisboa","Aveiro"));
        when(tripService.listTripsFiltered("Aveiro", "Porto", "2021-05-01", "EUR")).thenReturn(List.of(new Trip()));
        when(tripService.getTrip(1, "EUR")).thenReturn(null);

    }

    @Test
    @DisplayName("Test getting all the dates for trips")
    void whenHaveDates_thenReturnAll() throws Exception {
        
        mvc.perform(get("/trips/get_dates"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("2021-05-01"));

    }


    @Test 
    @DisplayName("Test getting all the origins for trips")
    void whenHaveOrigins_thenReturnAll() throws Exception {
        
        mvc.perform(get("/trips/get_origins"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Aveiro"));

    }

    @Test
    @DisplayName("Test getting all the destinations for trips")
    void whenHaveDestinations_thenReturnAll() throws Exception {
        
        mvc.perform(get("/trips/get_destinations"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]").value("Porto"));

    }

    @Test
    @DisplayName("Test getting all the trips with filter search")
    void whenListTrips_findExistentTrip() throws Exception {
        
        mvc.perform(get("/trips/list?origin=Aveiro&destination=Porto&date=2021-05-01&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)));
                

    }

    @Test
    @DisplayName("Test getting a trip by id that doesnt exist")
    void whenLookForNonExistentTrip_findNothing() throws Exception {
        
        mvc.perform(get("/trips/get?id=5&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
                
    }

    @Test
    @DisplayName("Test getting a trip by id that exists")
    void whenLookForExistentTrip_findTrip() throws Exception {
        
        mvc.perform(get("/trips/get?id=1&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").doesNotExist());
                
    }

    @Test 
    @DisplayName("Test getting trips with filters with no results")
    void whenListTripsThatDontExist_thenGetEmptyResponse() throws Exception {
        
        mvc.perform(get("/trips/list?origin=Lisboa&destination=Porto&date=2021-05-03&currency=EUR"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(0)));
                

    }

    @Test 
    @DisplayName("Test getting a trip with invalid ID")
    void whenGetTripWithInvalidID_thenGiveError() throws Exception {
        
        mvc.perform(get("/trips/get?id=banana&currency=EUR"))
                .andExpect(status().isBadRequest());

    }



    
}
