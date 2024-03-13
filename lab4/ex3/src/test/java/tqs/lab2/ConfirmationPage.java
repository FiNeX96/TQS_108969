package tqs.lab2;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ConfirmationPage {
    private final WebDriver driver;

    public ConfirmationPage(WebDriver driver) {
        this.driver = driver;
    }

    public void confirmPurchase() {
        driver.findElement(By.cssSelector(".btn-primary")).click();
    }

    public String getConfirmationMessage() {
        return driver.findElement(By.cssSelector("h1")).getText();
    }
}
