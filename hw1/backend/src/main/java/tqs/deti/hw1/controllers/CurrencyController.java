package tqs.deti.hw1.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import tqs.deti.hw1.services.CurrencyExchangeService;

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

    @Autowired
    private CurrencyExchangeService currencyExchangeService;


    @GetMapping("/list")
    public ResponseEntity<Set<String>> listCurrencies() {
        logger.info("List of currencies requested");
        return ResponseEntity.ok(currencyExchangeService.listCurrencies());
    }

}
