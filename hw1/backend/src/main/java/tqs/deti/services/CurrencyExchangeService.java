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

    @Autowired
    public CurrencyExchangeService() {
    }

    public CurrencyExchangeService(int ttl) {
        cacheTTL = ttl;
        cachedRates = new HashMap<>();
    }

    public boolean cacheExchangeRates(Map<String, Object> rates) {
        cachedRates = rates;
        lastCaching = System.currentTimeMillis();
        logger.info("Caching exchange rates at %s for %d milliseconds", lastCaching, cacheTTL);
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

        List<String> allowedHosts = List.of("https://v6.exchangerate-api.com/v6/");

        String apiLink = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + from;
        
        boolean isValid = false;

        for (String host : allowedHosts) {
            if (apiLink.startsWith(host)) {
                isValid = true;
                break;
            }
        }

        if (!isValid) {
            throw new Exception("Invalid API link");
        }

        String content = doRequest(apiLink);

        JSONObject obj = new JSONObject(content);

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

    public String doRequest(String link) throws Exception {

        URL url = new URL(link);

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
