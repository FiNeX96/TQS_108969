package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;

import tqs.deti.services.TicketFieldValidator;

import static org.junit.jupiter.api.Assertions.*;
public class ticketFieldValidatorsTest {


    @Test
    public void testInvalidEmail() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertFalse(ticketFieldValidator.validateEmail("gandamail@.pt"));
    }

    @Test
    public void testValidEmail() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertTrue(ticketFieldValidator.validateEmail("zemanel@gmail.com"));
    }

    
    @Test
    public void testInvalidPhone() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertFalse(ticketFieldValidator.validatePhone(9123723));
    }

    @Test
    public void testValidPhone() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertTrue(ticketFieldValidator.validatePhone(917264927));
    }


}
