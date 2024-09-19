package io.learn.pages;

import io.learn.exceptions.InvalidProductSelectionException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class ProductPage {

    private WebDriver driver;

    private By addToCartButton = By.id("add-to-cart-sauce-labs-backpack");
    private By productTitle = By.className("inventory_item_name");
    private By productPrice = By.className("inventory_item_price");
    private By cartLink = By.className("shopping_cart_link");
    private By hamburger = By.id("react-burger-menu-btn");
    private By logOutLink = By.id("logout_sidebar_link");

    public ProductPage(WebDriver driver) {
        this.driver = driver;
    }

    public boolean isProductsPageDisplayed() {
        return driver.findElement(addToCartButton).isDisplayed();
    }

    public boolean isProductListDisplayed() {
        return !driver.findElements(productTitle).isEmpty();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void addToCart() {
        driver.findElement(addToCartButton).click();
    }

    public void addProductToCart(String productName) {
        try {
            driver.findElement(By.xpath("//div[text()='" + productName + "']/parent::a/parent::div/following-sibling::div/button"))
                    .click();
        } catch (NoSuchElementException e) {
            throw new InvalidProductSelectionException("Product: " + productName + " is not available.");
        }
    }

    public boolean isProductTitleDisplayed(String productName) {
        return driver.findElement(By.xpath("//div[text()='" + productName + "']")).isDisplayed();
    }

    public boolean isProductPriceDisplayed(String productName) {
        return driver.findElement(By.xpath("//div[text()='" + productName + "']/../../../../div/div[2]/div"))
                .isDisplayed();
    }

    public boolean isProductInCart(String productName) {
        driver.findElement(cartLink).click();
        return driver.getPageSource().contains(productName);
    }

    public CartPage goToCart() {
        driver.findElement(cartLink).click();
        return new CartPage(driver);
    }

    public void logout() {
        driver.findElement(hamburger).click();
        driver.findElement(logOutLink).click();
    }
}
