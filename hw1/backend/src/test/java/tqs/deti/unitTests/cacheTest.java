package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;


import tqs.deti.services.CurrencyExchangeService;


 class CacheTest {


    private static CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(5000); // 5 seconds of cache ttl


    @Test
    void testResultAfterTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assert (currencyExchangeService.isCacheValid());

        Thread.sleep(6000);
        assert (!currencyExchangeService.isCacheValid());


    }

    @Test
     void testResultBeforeTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assert (currencyExchangeService.isCacheValid());

        Thread.sleep(4000);
        
        assert (currencyExchangeService.isCacheValid());

    }

     void testListCurrencies() throws Exception  {

        assertThat(currencyExchangeService.listCurrencies())
        .contains("EUR","USD");

    }


}
