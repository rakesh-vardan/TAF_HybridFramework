package io.learn.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
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
        test.log(Status.INFO, "Starting test: testLoginSuccess");

        // Verify that the user is on the Products page
        test.log(Status.INFO, "Verifying Products page is displayed");
        assertTrue(productPage.isProductsPageDisplayed(), "Products page is not displayed.");
        test.log(Status.PASS, "Products page displayed successfully.");

        // Verify the page URL
        String currentUrl = productPage.getCurrentUrl();
        test.log(Status.INFO, "Verifying current URL: "  +currentUrl);
        assertEquals(currentUrl, this.getExpectedPageUrl(), "Page URL is incorrect.");
        test.log(Status.PASS, "Correct URL displayed.");

        // Verify that the product list is displayed
        test.log(Status.INFO, "Verifying product list is displayed");
        assertTrue(productPage.isProductListDisplayed(), "Product list is not displayed.");
        test.log(Status.PASS, "Product list is displayed successfully.");

        test.log(Status.INFO, "Test testLoginSuccess passed successfully");
    }

    @Test(priority = 2)
    public void testLoginFailure() {
        ExtentTest test = TestListener.getTest();
        test.log(Status.INFO, "Starting test: testLoginFailure");

        // Logout from the current session
        test.log(Status.INFO, "Logging out of the application");
        productPage.logout();
        test.log(Status.PASS, "Logged out successfully.");

        // Verify that the user is redirected to the login page
        test.log(Status.INFO, "Verifying Login page is displayed after logout");
        assertTrue(loginPage.isLoginPageDisplayed(), "User is not redirected to login page after logout");
        test.log(Status.PASS, "Login page is displayed successfully.");

        // Enter incorrect credentials
        test.log(Status.INFO, "Entering username: standard_user");
        loginPage.enterUsername("standard_user");

        test.log(Status.INFO, "Entering incorrect password: wrong_password");
        loginPage.enterPassword("wrong_password");

        test.log(Status.INFO, "Clicking login button");
        try {
            loginPage.clickLogin();
            test.log(Status.FAIL, "Login was successful, but it should have failed.");
            fail("Login should have failed, but it was successful.");
        } catch (LoginFailedException e) {
            test.log(Status.PASS, "Login failed as expected: " + e.getMessage());
        }

        // Verify that the error message is displayed
        test.log(Status.INFO, "Verifying error message is displayed on login failure");
        assertTrue(loginPage.isLoginErrorDisplayed(), "Error message is not displayed.");
        test.log(Status.PASS, "Error message displayed successfully.");

        test.log(Status.INFO, "Test testLoginFailure passed successfully");
    }

    private String getExpectedPageUrl() {
        logger.debug("Fetching expected page URL from config");
        return configReader.getProperty("base.url") + "inventory.html";
    }
}

