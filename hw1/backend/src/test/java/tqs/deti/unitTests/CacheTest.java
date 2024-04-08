package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;



import tqs.deti.services.CurrencyExchangeService;


 class CacheTest {


    private static CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(5000); // 5 seconds of cache ttl


    @Test
    @DisplayName("Test if cache is valid after 5 seconds")
    void testResultAfterTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

        Thread.sleep(6000);
        assertThat(currencyExchangeService.isCacheValid()).isFalse();


    }

    @Test
    @DisplayName("Test if cache is valid before 5 seconds")
     void testResultBeforeTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

        Thread.sleep(4000);
        
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

    }

    @Test
    @DisplayName("Test the list of currencies")
     void testListCurrencies() throws Exception  {

        assertThat(currencyExchangeService.listCurrencies())
        .contains("EUR","USD");

    }




}
