package tqs.deti.services;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class TicketFieldValidator {
    private static final Logger logger = LoggerFactory.getLogger(TicketFieldValidator.class);

    public boolean validateEmail(String email) {
        logger.info("Validating email: %s", email);
        return email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    }

    public boolean validatePhone(int phone) {
        logger.info("Validating phone: %d", phone);
        return String.valueOf(phone).matches("^\\d{9}$");
    }

}
