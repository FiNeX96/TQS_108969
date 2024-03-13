package tqs.lab2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SeleniumJupiter.class)
public class Lab4Ex3Test {
    private WebDriver driver;
    private HomePage homePage;
    private ReservationPage reservationPage;
    private ConfirmationPage confirmationPage;


    public Lab4Ex3Test(ChromeDriver driver) {
        this.driver = driver;
    }

    @BeforeEach
    public void setUp() {
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        reservationPage = new ReservationPage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }


    @Test
    public void lab4ex2() {
        homePage.open();
        homePage.selectDeparture("Mexico City");
        homePage.selectDestination("London");
        homePage.searchFlights();
        assertEquals(reservationPage.getAirlineName(1), "Virgin America");

        reservationPage.chooseFlight(2);
        reservationPage.fillPassengerDetails("ze manel", "rua da rua", "ganda cidade", "leiria city", "234234", "23423", "asdasd");

        confirmationPage.confirmPurchase();
        assertEquals(confirmationPage.getConfirmationMessage(), "Thank you for your purchase today!");
    }
}


