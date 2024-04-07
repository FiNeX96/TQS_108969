package tqs.deti.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.deti.services.CurrencyExchangeService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.http.ResponseEntity;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/currencies")
@CrossOrigin("*")
public class CurrencyController {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyController.class);

    private CurrencyExchangeService currencyExchangeService;

    @Autowired
    public CurrencyController(CurrencyExchangeService currencyExchangeService) {
        this.currencyExchangeService = currencyExchangeService;
    }




    @GetMapping("/list")
    public ResponseEntity<Set<String>> listCurrencies() throws Exception {
        logger.info("List of currencies requested");
        return ResponseEntity.ok(currencyExchangeService.listCurrencies());
    }

    @GetMapping("/exchange")
    public ResponseEntity<Double> exchange(String from, String to) throws Exception {
        return ResponseEntity.ok(currencyExchangeService.exchange(from, to));
    }

}
