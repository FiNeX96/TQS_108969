package tqs9.hw1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs9.hw1.services.TripService;
import tqs9.hw1.models.Trip;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@CrossOrigin("*")
@RequestMapping(path = "/trips")
public class TripsController {

    @Autowired
    private TripService tripService;

    @GetMapping("/list")
    public ResponseEntity<List<Trip>> listTrips(@RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination, @RequestParam(required = false) String date) {
        
        return ResponseEntity.ok(tripService.listTripsFiltered(origin, destination, date));

    }

    @GetMapping("/get")
    public ResponseEntity<Trip> getTrip(@RequestParam int id) {
        return ResponseEntity.ok(tripService.getTrip(id));
    }

    @GetMapping("/get_dates")
    public ResponseEntity<List<String>> getDates() {
        return ResponseEntity.ok(tripService.getDates());
    }

    @GetMapping("/get_origins")
    public ResponseEntity<List<String>> getOrigins() {
        return ResponseEntity.ok(tripService.getOrigins());
    }

    @GetMapping("/get_destinations")
    public ResponseEntity<List<String>> getDestinations() {
        return ResponseEntity.ok(tripService.getDestinations());
    }

}
