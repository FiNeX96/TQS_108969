package tqs.lab4;

import org.slf4j.Logger;

import io.github.bonigarcia.seljup.SeleniumJupiter;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.openqa.selenium.chrome.ChromeDriver;

import static org.slf4j.LoggerFactory.getLogger;
import static org.assertj.core.api.Assertions.assertThat;
import static java.lang.invoke.MethodHandles.lookup;

@ExtendWith(SeleniumJupiter.class)
public class HelloWorldChromeJupiterTest {

    static final Logger log = getLogger(lookup().lookupClass());

    @Test
    public void test(ChromeDriver driver) {
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);
        String title = driver.getTitle();
        log.debug("The title of {} is {}", sutUrl, title);

        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java");
    }

}