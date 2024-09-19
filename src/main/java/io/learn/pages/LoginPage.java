package io.learn.pages;

import io.learn.exceptions.ElementNotFoundException;
import io.learn.exceptions.LoginFailedException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    private WebDriver driver;

    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By errorMessage = By.cssSelector(".error-message-container");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void enterUsername(String username) {
        driver.findElement(usernameField).clear();
        driver.findElement(usernameField).sendKeys(username);
    }

    public void enterPassword(String password) {
        driver.findElement(passwordField).clear();
        driver.findElement(passwordField).sendKeys(password);
    }

    public ProductPage clickLogin() {
        driver.findElement(loginButton).click();
        if (isLoginErrorDisplayed()) {
            throw new LoginFailedException("Login failed due to incorrect username or password.");
        }
        return new ProductPage(driver);
    }

    public boolean isLoginErrorDisplayed() {
        try {
            WebElement element = driver.findElement(errorMessage);
            return element.isDisplayed();
        } catch (NoSuchElementException e) {
            // If the error message is not found, it means there was no error, so return false.
            return false;
        }
    }


    public boolean isLoginPageDisplayed() {
        return driver.findElement(usernameField).isDisplayed();
    }
}