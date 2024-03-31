package tqs.deti.controllerTests;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import tqs.deti.services.CurrencyExchangeService;

import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import tqs.deti.controllers.CurrencyController;

import org.junit.jupiter.api.BeforeAll;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.Set;


@WebMvcTest(controllers = CurrencyController.class, properties="spring.profiles.active=test")
public class CurrencyControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CurrencyExchangeService currencyExchangeService;

    @BeforeEach
    void setUp() throws Exception {
        when(currencyExchangeService.listCurrencies()).thenReturn(Set.of("EUR", "USD", "JPY", "BRL"));
        // real api provides more, this is just exemplary
        when(currencyExchangeService.exchange("EUR", "USD")).thenReturn(0.95);
        when(currencyExchangeService.exchange("EUR", "JPY")).thenReturn(163.0);
        when(currencyExchangeService.exchange("EUR", "BRL")).thenReturn(5.0);
    }

    @Test
    void whenGetCurrencies_thenReturnCurrencies() throws Exception {

        mvc.perform(get("/currencies/list"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(4)));

                // for some reason, the set is being reordered, making it so that for example jsonPath("$[0]", is("EUR")) fails because it is 


    }

    @Test
    void whenGetExchange_thenReturnExchange() throws Exception {

        mvc.perform(get("/currencies/exchange?from=EUR&to=USD"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(0.95)));

        mvc.perform(get("/currencies/exchange?from=EUR&to=JPY"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(163.0)));

        mvc.perform(get("/currencies/exchange?from=EUR&to=BRL"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", is(5.0)));

    }

}
