package tqs.deti.services;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TicketFieldValidator {
    private static final Logger logger = LoggerFactory.getLogger(TicketFieldValidator.class);


    public boolean validateEmail(String email) {
        logger.info("Validating email: " + email);
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validatePhone(int phone) {
        logger.info("Validating phone: " + phone);
        return String.valueOf(phone).matches("^[0-9]{9}$");
    }

    
}
