package tqs.deti.services;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    private Set<String> currencies;
    Map<String, Object> cachedRates = new HashMap<>();
    private int cacheTTL = 3600 * 1000; // 1 hour
    private long lastCaching = 0;
    private String apiKey = "9a42f01a628d9752376f4eaf";
    private String exchangeRates = "conversion_rates";

    public CurrencyExchangeService(int ttl) {
        cacheTTL = ttl;
        cachedRates = new HashMap<>();
    }

    @Autowired
    public CurrencyExchangeService() {
        cachedRates = new HashMap<>();
    }

    public boolean cacheExchangeRates(Map<String, Object> rates) {
        cachedRates = rates;
        lastCaching = System.currentTimeMillis();
        logger.info("Caching exchange rates at {} for {} milliseconds", lastCaching, cacheTTL);
        return true;
    }

    public boolean isCacheValid() {

        if (cachedRates.isEmpty()) {
            return false;
        }
        if (lastCaching == 0) {
            return false;
        }

        return System.currentTimeMillis() < lastCaching + cacheTTL;

    }

    public Set<String> listCurrencies() throws Exception {
        logger.info("List of currencies requested");
        if (currencies == null)
            exchange("EUR", "USD");
        return currencies;
    }

    public double exchange(String from, String to) throws Exception {

        if (isCacheValid()) {

            Double rate = Double.parseDouble(cachedRates.get(to).toString());
            logger.info("Cache hit, returning exchange rate");
            return rate;

        } else {
            logger.info("Cache is not valid, redoing exchange rates request");
        }


        String baseApiUrl = "https://v6.exchangerate-api.com/v6/";

        List<String> allowedFrom = List.of("EUR", "USD", "JPY", "BGN", "CZK", "DKK", "GBP", "HUF", "PLN", "RON", "SEK",
                "CHF", "ISK", "NOK", "HRK", "RUB", "TRY", "AUD", "BRL", "CAD", "CNY", "HKD", "IDR", "ILS", "INR", "KRW",
                "MXN", "MYR", "NZD", "PHP", "SGD", "THB", "ZAR");

        if (!allowedFrom.contains(from)) {
            throw new Exception("Invalid currency");
        }
        
        String apiLink = baseApiUrl + apiKey + "/latest/" + from;
        
        
        URL url = new URL(apiLink);


        String content = doRequest(url);

        JSONObject obj = new JSONObject(content);

        cacheExchangeRates(obj.getJSONObject(exchangeRates).toMap());

        currencies = obj.getJSONObject(exchangeRates).keySet();

        double rate;

        try {
            rate = obj.getJSONObject(exchangeRates).getDouble(to);
        } catch (Exception e) {
            throw new Exception("Currency not found");
        }

        return rate;

    }

    public String doRequest(URL url) throws Exception {

        HttpURLConnection con = (HttpURLConnection) url.openConnection();

        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        return content.toString();
    }

}
