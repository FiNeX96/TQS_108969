
package tqs.lab5;

import static java.lang.invoke.MethodHandles.lookup;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

import org.slf4j.Logger;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class BlazeDemoSteps {

    private WebDriver driver;
    HomePage homePage;
    ReservationPage reservationPage;
    ConfirmationPage confirmationPage;

    static final Logger log = getLogger(lookup().lookupClass());

    @When("i open the website {string}")
    public void i_open_the_website(String string) {
        //WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get(string);
        homePage = new HomePage(driver);
    }

    @And("i select {string} as departure city")
    public void i_select_as_departure_city(String string) {
        homePage.selectDeparture(string);
    }

    @And("i select {string} as destination city")
    public void i_select_as_destination_city(String string) {
        homePage.selectDestination(string);
    }

    @And("i click on Find Flights button")
    public void i_click_on_find_flights_button() {
        homePage.searchFlights();
    }

    @And("i select the first flight")
    public void i_select_the_first_flight() {
        reservationPage = new ReservationPage(driver);
        reservationPage.chooseFlight(1);
    }

    @And("i enter {string} as first name")
    public void i_enter_as_first_name(String string) {
        reservationPage.fillPassengerDetails(string, "address", "city", "state", "zipCode", "creditCardNumber", "nameOnCard");
    }

    @And("i click on Purchase Flight button")
    public void i_click_on_purchase_flight_button() {
        confirmationPage = new ConfirmationPage(driver);
        confirmationPage.confirmPurchase();
    }

    @Then("i should see the message {string}")
    public void i_should_see_the_message(String string) {
        assertEquals(string, confirmationPage.getConfirmationMessage());
        driver.quit();
    }

    
    

}