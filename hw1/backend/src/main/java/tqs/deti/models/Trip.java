package tqs.deti.models;
import jakarta.persistence.Id;

import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import java.util.List;

import jakarta.persistence.GenerationType;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Let JPA generate ID
    private int id;

    private int busID;

    @OneToMany(cascade=CascadeType.PERSIST)
    private List<Seat> seats;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private double price;

}
