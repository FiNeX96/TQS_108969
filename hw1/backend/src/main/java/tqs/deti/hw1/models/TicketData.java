package tqs.deti.hw1.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TicketData {
    private String id;
    private String price; // account for currency changes
    private int tripID;
    private int seatNumber;
    private int busID;
    private String origin;
    private String destination;
    private String date;
    private String time;
    private String name;
    private int phone;
    private String email;
}
