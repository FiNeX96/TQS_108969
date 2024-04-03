package tqs.deti.bddTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){ 
        driver.get("http://localhost:5173/");
    }

    public void clickSeeTripsButton() {
        driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
    }


}
