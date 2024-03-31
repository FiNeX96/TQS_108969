package tqs.deti.hw1.models;

import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import org.hibernate.annotations.GenericGenerator;

@Getter
@Setter
@ToString
@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Let JPA generate ID
    @GenericGenerator(name = "system-uuid")
    private String id;
    private String price; // account for currency changes
    private int tripID;
    private int seatNumber;
    private String name;
    private int phone;
    private String email;
}
