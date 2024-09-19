package io.learn.utils;

import io.learn.exceptions.RemoteDriverInitializationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;

public class WebDriverFactory {

    static String remote = System.getProperty("remote"); // Use system property to determine remote execution
    static String hubUrl = "http://localhost:4444/wd/hub"; // Default Hub URL (change if necessary)

    private WebDriverFactory() {
    }

    public static WebDriver createDriver(Browser browser) {
        WebDriver driver;

        if ("true".equalsIgnoreCase(remote)) {
            driver = getRemoteWebDriver(browser);
        } else {
            driver = getLocalWebDriver(browser);
        }
        return driver;
    }

    private static WebDriver getRemoteWebDriver(Browser browser) {
        // Remote WebDriver setup
        WebDriver driver;
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setBrowserName(browser.getBrowserName());
            driver = new RemoteWebDriver(new URI(hubUrl).toURL(), capabilities);
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RemoteDriverInitializationException("Failed to initialize remote WebDriver for " + browser, e);
        }
        return driver;
    }

    private static WebDriver getLocalWebDriver(Browser browser) {
        WebDriver driver;
        // Local WebDriver setup
        switch (browser) {
            case FIREFOX:
                driver = new FirefoxDriver();
                break;
            case EDGE:
                driver = new EdgeDriver();
                break;
            case CHROME:
            default:
                driver = new ChromeDriver();
                break;
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
}
