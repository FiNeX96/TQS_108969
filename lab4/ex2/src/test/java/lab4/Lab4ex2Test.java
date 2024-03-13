import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.JavascriptExecutor;
import java.util.HashMap;
import java.util.Map;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(SeleniumJupiter.class)
public class Lab4ex2Test {
    private final WebDriver driver;
    public Lab4ex2Test(ChromeDriver driver) {
        this.driver = driver;
    }
    private Map<String, Object> vars;


    @Test
    public void lab4ex2() {
        driver.get("https://blazedemo.com/");
        driver.manage().window().setSize(new Dimension(845, 824));
        driver.findElement(By.name("fromPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("fromPort"));
            dropdown.findElement(By.xpath("//option[. = 'Mexico City']")).click();
        }
        driver.findElement(By.name("toPort")).click();
        {
            WebElement dropdown = driver.findElement(By.name("toPort"));
            dropdown.findElement(By.xpath("//option[. = 'London']")).click();
        }
        driver.findElement(By.cssSelector(".btn-primary")).click();
        assertEquals(driver.findElement(By.cssSelector("tr:nth-child(1) > td:nth-child(4)")).getText(), "Virgin America");
        driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();
        driver.findElement(By.id("inputName")).click();
        driver.findElement(By.id("inputName")).sendKeys("ze manel");
        driver.findElement(By.id("address")).click();
        driver.findElement(By.id("address")).sendKeys("rua da rua");
        driver.findElement(By.id("city")).click();
        driver.findElement(By.id("city")).sendKeys("ganda cidade");
        driver.findElement(By.id("state")).click();
        driver.findElement(By.id("state")).sendKeys("leiria city");
        driver.findElement(By.id("zipCode")).click();
        driver.findElement(By.id("zipCode")).sendKeys("234234");
        driver.findElement(By.id("creditCardNumber")).click();
        driver.findElement(By.id("creditCardNumber")).sendKeys("23423");
        driver.findElement(By.id("nameOnCard")).click();
        driver.findElement(By.id("nameOnCard")).sendKeys("asdasd");
        driver.findElement(By.id("rememberMe")).click();
        driver.findElement(By.cssSelector(".btn-primary")).click();
        driver.findElement(By.cssSelector("h1")).click();
        assertEquals(driver.findElement(By.cssSelector("h1")).getText(), "Thank you for your purchase today!");
    }
}
