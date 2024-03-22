package tqs9.hw1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tqs9.hw1.repositories.BusRepository;
import tqs9.hw1.models.Bus;
import tqs9.hw1.models.Trip;


@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getBus(int id) {
        return busRepository.findById(id);
    }



    
}
