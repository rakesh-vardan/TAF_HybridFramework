package io.learn.tests;

import io.learn.data_provider.UserDataProvider;
import io.learn.pages.LoginPage;
import io.learn.pages.ProductPage;
import io.learn.utils.Browser;
import io.learn.utils.WebDriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.*;
import io.learn.utils.ConfigReader;

import static org.testng.Assert.assertTrue;

public class LoginWithDifferentUsersTest {
    private WebDriver driver;
    private LoginPage loginPage;
    private ProductPage productPage;
    private static final ConfigReader configReader = new ConfigReader();

    @BeforeClass
    @Parameters({"browser"})
    public void setUp(ITestContext context, String browserName) {
        Browser browser = Browser.valueOf(browserName.toUpperCase());
        driver = WebDriverFactory.createDriver(browser);
        context.setAttribute("driver", driver);
        driver.get(configReader.getProperty("base.url"));
        loginPage = new LoginPage(driver);
    }

    @Test(dataProvider = "userData")
    public void testLoginWithDifferentUsers(String username, String password, boolean shouldLoginSucceed) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        if (shouldLoginSucceed) {
            productPage = new ProductPage(driver);
            assertTrue(productPage.isProductsPageDisplayed(), "Products page should be displayed for user: " + username);
            productPage.logout();
            assertTrue(loginPage.isLoginPageDisplayed(), "User is not redirected to login page after logout");
        } else {
            assertTrue(loginPage.isLoginErrorDisplayed(), "Error message should be displayed for user: " + username);
        }
    }

    @DataProvider(name = "userData")
    private Object[][] userData() {
        return UserDataProvider.userData();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
