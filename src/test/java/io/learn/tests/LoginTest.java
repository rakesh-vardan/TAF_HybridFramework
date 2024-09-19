package io.learn.tests;

import com.aventstack.extentreports.ExtentTest;
import io.learn.exceptions.LoginFailedException;
import io.learn.listeners.TestListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class LoginTest extends BaseTest {

    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test(priority = 1, groups = { "smoke" })
    public void testLoginSuccess() {
        ExtentTest test = TestListener.getTest();
        test.info("Starting test: testLoginSuccess");

        // Verify that the user is on the Products page
        test.info("Verifying Products page is displayed");
        assertTrue(productPage.isProductsPageDisplayed(), "Products page is not displayed.");
        test.pass("Products page displayed successfully.");

        // Verify the page URL
        String currentUrl = productPage.getCurrentUrl();
        test.info("Verifying current URL: "  +currentUrl);
        assertEquals(currentUrl, this.getExpectedPageUrl(), "Page URL is incorrect.");
        test.pass("Correct URL displayed.");

        // Verify that the product list is displayed
        test.info("Verifying product list is displayed");
        assertTrue(productPage.isProductListDisplayed(), "Product list is not displayed.");
        test.pass("Product list is displayed successfully.");

        test.info("Test testLoginSuccess passed successfully");
    }

    @Test(priority = 2)
    public void testLoginFailure() {
        ExtentTest test = TestListener.getTest();
        test.info("Starting test: testLoginFailure");

        // Logout from the current session
        test.info("Logging out of the application");
        productPage.logout();
        test.pass("Logged out successfully.");

        // Verify that the user is redirected to the login page
        test.info("Verifying Login page is displayed after logout");
        assertTrue(loginPage.isLoginPageDisplayed(), "User is not redirected to login page after logout");
        test.pass("Login page is displayed successfully.");

        // Enter incorrect credentials
        test.info("Entering username: standard_user");
        loginPage.enterUsername("standard_user");

        test.info("Entering incorrect password: wrong_password");
        loginPage.enterPassword("wrong_password");

        test.info("Clicking login button");
        try {
            loginPage.clickLogin();
            test.fail("Login was successful, but it should have failed.");
            fail("Login should have failed, but it was successful.");
        } catch (LoginFailedException e) {
            test.pass("Login failed as expected: " + e.getMessage());
        }

        // Verify that the error message is displayed
        test.info("Verifying error message is displayed on login failure");
        assertTrue(loginPage.isLoginErrorDisplayed(), "Error message is not displayed.");
        test.pass("Error message displayed successfully.");

        test.info("Test testLoginFailure passed successfully");
    }

    private String getExpectedPageUrl() {
        logger.debug("Fetching expected page URL from config");
        return configReader.getProperty("base.url") + "inventory.html";
    }
}

