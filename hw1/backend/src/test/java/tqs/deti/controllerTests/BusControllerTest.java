package tqs.deti.controllerTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.mock.mockito.MockBean;
import tqs.deti.services.BusService;
import tqs.deti.models.Bus;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import tqs.deti.controllers.BusController;

@WebMvcTest(controllers = BusController.class,properties="spring.profiles.active=test")
public class BusControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BusService busService;

    @BeforeEach
    void setUp() {

        Bus bus = new Bus();
        bus.setName("bus bue fixe");
        bus.setTotalSeats(50);

        Bus bus2 = new Bus();
        bus2.setName("bus bue fixe 2");
        bus2.setTotalSeats(39);

        Bus bus3 = new Bus();
        bus3.setName("bus bue fixe 3");
        bus3.setTotalSeats(30);

        List<Bus> buses = Arrays.asList(bus, bus2, bus3);

        when(busService.findAll()).thenReturn(buses);
        when(busService.getBus(1)).thenReturn(bus);
        when(busService.getBus(2)).thenReturn(bus2);
        when(busService.getBus(3)).thenReturn(bus3);
    }

    @Test
    void whenHaveBuses_thenReturnAll() throws Exception {

        mvc.perform(get("/bus/list")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("bus bue fixe")))
                .andExpect(jsonPath("$[0].totalSeats", is(50)));

    }

    @Test
    void whenHaveBus_thenGetBus() throws Exception {

        mvc.perform(get("/bus/get?id=1")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.name", is("bus bue fixe")))
                .andExpect(jsonPath("$.totalSeats", is(50)));

    }

}
