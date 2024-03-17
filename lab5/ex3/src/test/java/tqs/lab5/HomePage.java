package tqs.lab5;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {
    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open() {
        driver.get("https://blazedemo.com/");
    }

    public void selectDeparture(String departure) {
        driver.findElement(By.name("fromPort")).click();
        driver.findElement(By.xpath("//option[. = '" + departure + "']")).click();
    }

    public void selectDestination(String destination) {
        driver.findElement(By.name("toPort")).click();
        driver.findElement(By.xpath("//option[. = '" + destination + "']")).click();
    }

    public void searchFlights() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }
}
