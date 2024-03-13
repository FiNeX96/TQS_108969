package tqs.lab2;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ReservationPage {
    private final WebDriver driver;

    public ReservationPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getAirlineName(int row) {
        return driver.findElement(By.cssSelector("tr:nth-child(" + row + ") > td:nth-child(4)")).getText();
    }

    public void chooseFlight(int row) {
        driver.findElement(By.cssSelector("tr:nth-child(" + row + ") .btn")).click();
    }

    public void fillPassengerDetails(String name, String address, String city, String state, String zipCode, String creditCardNumber, String nameOnCard) {
        driver.findElement(By.id("inputName")).sendKeys(name);
        driver.findElement(By.id("address")).sendKeys(address);
        driver.findElement(By.id("city")).sendKeys(city);
        driver.findElement(By.id("state")).sendKeys(state);
        driver.findElement(By.id("zipCode")).sendKeys(zipCode);
        driver.findElement(By.id("creditCardNumber")).sendKeys(creditCardNumber);
        driver.findElement(By.id("nameOnCard")).sendKeys(nameOnCard);
        driver.findElement(By.id("rememberMe")).click();
    }
}
