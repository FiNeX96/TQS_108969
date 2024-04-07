package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;



import tqs.deti.services.CurrencyExchangeService;


 class CacheTest {


    private static CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(5000); // 5 seconds of cache ttl


    @Test
    void testResultAfterTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

        Thread.sleep(6000);
        assertThat(currencyExchangeService.isCacheValid()).isFalse();


    }

    @Test
     void testResultBeforeTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

        Thread.sleep(4000);
        
        assertThat(currencyExchangeService.isCacheValid()).isTrue();

    }

    @Test
     void testListCurrencies() throws Exception  {

        assertThat(currencyExchangeService.listCurrencies())
        .contains("EUR","USD");

    }




}
