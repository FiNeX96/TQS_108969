package tqs.deti.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.server.ResponseStatusException;



import org.springframework.http.HttpStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tqs.deti.services.BusService;
import tqs.deti.models.Bus;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/bus")
public class BusController {

    private static final Logger logger = LoggerFactory.getLogger(BusController.class);

    @Autowired
    private BusService busService;


    @GetMapping("/get")
    public ResponseEntity<Bus> getBus(@RequestParam int id) {
        Bus bus = busService.getBus(id);
        logger.info("Bus with id " + id + " requested");
        if (bus != null) {
            return ResponseEntity.ok(bus);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Bus not found!");
        }
    }


    
}
