package tqs.deti.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tqs.deti.repositories.BusRepository;
import tqs.deti.models.Bus;


@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    public Bus getBus(int id) {
        return busRepository.findById(id);
    }

    public Iterable<Bus> findAll() {
        return busRepository.findAll();
    }

    public Bus addBus(Bus bus) {
        return busRepository.save(bus);
    }




    
}
