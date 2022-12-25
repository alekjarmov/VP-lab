package mk.ukim.finki.wp.lab.selenium;

import org.openqa.selenium.WebDriver;

public class AbstractPage {

    protected WebDriver driver;

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
    }

    static void get(WebDriver driver, String relativeUrl) {
        String url = System.getProperty("geb.build.baseUrl", "http://localhost:9999") + relativeUrl;
        driver.get(url);
    }

}
