package tqs.deti.hw1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tqs.deti.hw1.repositories.BusRepository;
import tqs.deti.hw1.models.Bus;


@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getBus(int id) {
        return busRepository.findById(id);
    }




    
}
