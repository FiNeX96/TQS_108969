package tqs.deti.bddTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class BuyTicketPage {
    private final WebDriver driver;

    public BuyTicketPage(WebDriver driver) {
        this.driver = driver;
    }

    public void selectTicket() {
        driver.findElement(By.cssSelector(".pt-20")).click();
    }

    public void selectSeat(int seat) {
        driver.findElement(By.xpath("//button["+seat+"]")).click();
    }

    public void checkSeatNotTaken(int seat) {
        assertEquals("Buy Ticket - 10.00 EUR", driver.findElement(By.cssSelector(".btn-info")).getText());
    }

    public void checkSeatTaken(int seat) {
        assertEquals("Seat Taken", driver.findElement(By.cssSelector(".px-4")).getText());
    }

    public void enterName(String name) {
        driver.findElement(By.id("name")).click();
        driver.findElement(By.id("name")).sendKeys(name);
    }

    public void enterPhone(String phone) {
        driver.findElement(By.id("phone")).click();
        driver.findElement(By.id("phone")).sendKeys(phone);
    }

    public void enterEmail(String email) {
        driver.findElement(By.id("email")).click();
        driver.findElement(By.id("email")).sendKeys(email);
    }

    public void clickBuyTicketButton() {
        driver.findElement(By.cssSelector(".btn-info")).click();
    }

    public String getSeatTakenMessage(){
        return driver.findElement(By.cssSelector(".px-4")).getText();
    }

    public int getConfirmationMessage(){

        return driver.findElements(By.xpath("xpath=//div[@id='root']/div/div[3]/p")).size();



    }
}

