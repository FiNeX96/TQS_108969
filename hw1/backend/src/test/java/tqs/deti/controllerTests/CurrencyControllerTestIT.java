package tqs.deti.controllerTests;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.jupiter.api.Test;

import org.springframework.http.ResponseEntity;

import org.springframework.http.HttpStatus;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "spring.profiles.active=test")
@AutoConfigureTestDatabase
class CurrencyControllerTestIT {

    @Autowired
    private TestRestTemplate restTemplate;

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

}
