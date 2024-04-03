package tqs.deti.bddTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ListTripsPage {
    private final WebDriver driver;

    public ListTripsPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectDeparture(String departure) {
        driver.findElement(By.cssSelector(".select:nth-child(2)")).click();
        driver.findElement(By.xpath("//option[. = '" + departure + "']")).click();
    }

    public void selectDestination(String destination) {
        driver.findElement(By.cssSelector(".select:nth-child(4)")).click();
        driver.findElement(By.xpath("//option[. = '" + destination + "']")).click();
    }

    public void selectFirstTrip() {
        driver.findElement(By.cssSelector(".items-center:nth-child(1) > .w-1\\/4:nth-child(2)")).click();
    }

    public void clickSearchButton() {
        driver.findElement(By.cssSelector(".items-center > .w-1\\/4:nth-child(3)")).click();
    }

    public List<WebElement> getTripElements() {
        return driver.findElements(By.cssSelector(".text-left"));
    }

}

