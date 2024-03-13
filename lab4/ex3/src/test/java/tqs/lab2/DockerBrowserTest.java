package tqs.lab2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.github.bonigarcia.seljup.BrowserType.CHROME;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(SeleniumJupiter.class)
public class DockerBrowserTest {
    private HomePage homePage;
    private ReservationPage reservationPage;
    private ConfirmationPage confirmationPage;


    @BeforeEach
    public void setUp(@DockerBrowser(type = CHROME) RemoteWebDriver driver) {
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
