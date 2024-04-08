package tqs.deti.unitTests;



import org.junit.jupiter.api.Test;

import tqs.deti.services.TicketFieldValidator;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;
 class TicketFieldValidatorsTest {


    @Test
    @DisplayName("Test invalid email")
    void testInvalidEmail() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertFalse(ticketFieldValidator.validateEmail("gandamail@.pt"));
    }



    @Test
    @DisplayName("Test valid email")
     void testValidEmail() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertTrue(ticketFieldValidator.validateEmail("zemanel@gmail.com"));
    }

    
    @Test
    @DisplayName("Test invalid phone")
     void testInvalidPhone() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertFalse(ticketFieldValidator.validatePhone(9123723));
    }

    @Test
    @DisplayName("Test valid phone")
     void testValidPhone() {
        TicketFieldValidator ticketFieldValidator = new TicketFieldValidator();
        assertTrue(ticketFieldValidator.validatePhone(917264927));
    }


}
