package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;


import tqs.deti.services.CurrencyExchangeService;


public class cacheTest {


    private static CurrencyExchangeService currencyExchangeService = new CurrencyExchangeService(5000); // 5 seconds of cache ttl


    @Test
    public void testResultAfterTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assert (currencyExchangeService.isCacheValid());

        Thread.sleep(6000);
        assert (!currencyExchangeService.isCacheValid());


    }

    @Test
    public void testResultBeforeTTLExpire() throws Exception {

        currencyExchangeService.exchange("EUR", "USD");
        assert (currencyExchangeService.isCacheValid());

        Thread.sleep(4000);
        
        assert (currencyExchangeService.isCacheValid());

        
    }

}
