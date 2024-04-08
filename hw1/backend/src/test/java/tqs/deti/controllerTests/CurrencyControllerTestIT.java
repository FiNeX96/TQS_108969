package tqs.deti.controllerTests;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import tqs.deti.services.CurrencyExchangeService;

import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase
class CurrencyControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CurrencyExchangeService currencyExchangeService;

    @Test
    void whenDoExchange_thenGetResponse() {
        ResponseEntity<String> response = restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
                String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // assert that it returns a double, cant really test for values as they may
        // change
        assertThat(Double.parseDouble(response.getBody())).isInstanceOf(Double.class);

    }

    @Test
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
    void testCacheHitsAndMisses() {

        // clean cache hits and misses

        currencyExchangeService.setCacheHits(0);
        currencyExchangeService.setCacheMisses(0);
        currencyExchangeService.cleanCachedRates();

        restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
        String.class);

        ResponseEntity<Map> response2 = restTemplate.getForEntity("/currencies/cache_stats",Map.class);

        assertThat(response2.getBody().get("cacheHits")).isEqualTo(0);
        assertThat(response2.getBody().get("cacheMisses")).isEqualTo(1);

        restTemplate.getForEntity("/currencies/exchange?from=EUR&to=USD",
        String.class);

        ResponseEntity<Map> response4 = restTemplate.getForEntity("/currencies/cache_stats", Map.class);

        assertThat(response4.getBody().get("cacheHits")).isEqualTo(1);
        assertThat(response4.getBody().get("cacheMisses")).isEqualTo(1);

    }

}
