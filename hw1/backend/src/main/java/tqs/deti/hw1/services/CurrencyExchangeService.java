package tqs.deti.hw1.services;

import java.net.URL;
import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import java.util.Set;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyExchangeService {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyExchangeService.class);

    private Set<String> currencies;
    Map<String, Object> cachedRates = new HashMap<String, Object>();
    private int cacheTTL = 3600 * 1000; // 1 hour
    private long lastCaching = 0;

    public boolean cacheExchangeRates(Map<String, Object> rates) {
        cachedRates = rates;
        lastCaching = System.currentTimeMillis();
        logger.info("Caching exchange rates at " + lastCaching + " for " + cacheTTL + " milliseconds" );
        return true;
    }

    public boolean isCacheValid() {

        if (cachedRates.isEmpty()) {
            return false;
        }
        if (lastCaching == 0) {
            return false;
        }

        if (System.currentTimeMillis() < lastCaching + cacheTTL) {

            return true;
        } else {
            return false;
        }

    }

    public Set<String> listCurrencies() {
        logger.info("List of currencies requested");
        if (currencies == null) {
            try {
                exchange("EUR", "USD");
            } catch (Exception e) {

            }
        }
        return currencies;
    }

    public double exchange(String from, String to) throws Exception {

        if (isCacheValid()) {
            try {
                Double rate = Double.parseDouble(cachedRates.get(to).toString());
                logger.info("Cache hit, returning exchange rate");
                return rate;
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("Currency not found");
            }

        }
        else{
            logger.info("Cache is not valid, redoing exchange rates request");
        }

        URL url = new URL("https://v6.exchangerate-api.com/v6/9a42f01a628d9752376f4eaf/latest/" + from);

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

        JSONObject obj = new JSONObject(content.toString());

        cacheExchangeRates(obj.getJSONObject("conversion_rates").toMap());

        currencies = obj.getJSONObject("conversion_rates").keySet();

        double rate;

        try {
            rate = obj.getJSONObject("conversion_rates").getDouble(to);
        } catch (Exception e) {
            throw new Exception("Currency not found");
        }

        return rate;

    }

}
