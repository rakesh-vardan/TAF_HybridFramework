package io.learn.tests;

import io.learn.exceptions.LoginFailedException;
import io.learn.pages.CartPage;
import io.learn.pages.LoginPage;
import io.learn.pages.ProductPage;
import io.learn.utils.Browser;
import io.learn.utils.ConfigReader;
import io.learn.utils.WebDriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public abstract class BaseTest {

    protected WebDriver driver;
    protected ProductPage productPage;
    protected LoginPage loginPage;
    protected CartPage cartPage;
    protected static final ConfigReader configReader = new ConfigReader();
    private static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeClass(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(ITestContext context, String browserName) {
        logger.debug("Initializing WebDriver for browser: {}", browserName);
        Browser browser = Browser.valueOf(browserName.toUpperCase());
        logger.debug("Initializing WebDriver");
        driver = WebDriverFactory.createDriver(browser);
        context.setAttribute("driver", driver);

        logger.info("Navigating to base URL: {}", configReader.getProperty("base.url"));
        driver.get(configReader.getProperty("base.url"));

        loginPage = new LoginPage(driver);
        logger.info("Entering credentials and logging in.");
        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");

        try {
            productPage = loginPage.clickLogin();
            logger.info("Login successful, navigating to Product Page.");
        } catch (LoginFailedException e) {
            logger.error("Login failed: {}", e.getMessage());
            throw e; // Ensure the test fails if login is not successful
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            logger.info("Quitting WebDriver.");
            driver.quit();
        }
    }
}
