package tqs.lab3.ex4;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car, Long> {


    public List<Car> findAll();

}
