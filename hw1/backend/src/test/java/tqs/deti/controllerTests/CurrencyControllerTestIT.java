package tqs.deti.controllerTests;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import tqs.deti.services.CurrencyExchangeService;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase
class CurrencyControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Test
    @DisplayName("Test that there is a response when a currency exchange is made")
    void whenDoExchange_thenGetResponse() {
        ResponseEntity<String> response = restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assert that it returns a double, cant really test for values as they may
        // change
        assertThat(Double.parseDouble(response.getBody())).isInstanceOf(Double.class);

    }

    @Test 
    @DisplayName("Test that the response is cached when the same exchange is made twice")
    void whenMultipleExchanges_thenGetCachedRates() {
        ResponseEntity<String> response = restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        ResponseEntity<String> response2 = restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
                String.class);
        assertThat(response2.getStatusCode()).isEqualTo(HttpStatus.OK);

        // assert that first response is equal to second response ( cached rates )
        assertThat(response.getBody()).isEqualTo(response2.getBody());

    }

    @Test
    @DisplayName("Test that the cache hits and misses are updated correctly")
    void testCacheHitsAndMisses() {

        // clean cache hits and misses

        currencyExchangeService.setCacheHits(0);
        currencyExchangeService.setCacheMisses(0);
        currencyExchangeService.cleanCachedRates();

        restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
                String.class);

        ResponseEntity<Map<String, Integer>> response2 = restTemplate.exchange(
                "/currencies/cache_stats",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Integer>>() {
                });

        assertThat(response2.getBody()).containsEntry("cacheHits", 0);
        assertThat(response2.getBody()).containsEntry("cacheMisses", 1);

        // For /currencies/exchange?from=EUR&to=USD
        restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD", String.class);

        // For /currencies/cache_stats again
        ResponseEntity<Map<String, Integer>> response4 = restTemplate.exchange(
                "/currencies/cache_stats",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Integer>>() {
                });

        assertThat(response4.getBody()).containsEntry("cacheHits", 1);
        assertThat(response4.getBody()).containsEntry("cacheMisses", 1);

    }

}
