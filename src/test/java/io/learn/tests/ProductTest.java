package io.learn.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.learn.listeners.TestListener;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class ProductTest extends BaseTest {

    @Test
    public void testProductDetails() {
        ExtentTest test = TestListener.getTest();

        test.log(Status.INFO, "Verifying product title is displayed.");
        boolean titleDisplayed = productPage.isProductTitleDisplayed("Sauce Labs Backpack");
        assertTrue(titleDisplayed, "Product title is not displayed.");
        test.log(Status.PASS, "Product title is displayed.");

        test.log(Status.INFO, "Verifying product price is displayed.");
        boolean priceDisplayed = productPage.isProductPriceDisplayed("Sauce Labs Backpack");
        assertTrue(priceDisplayed, "Product price is not displayed.");
        test.log(Status.PASS, "Product price is displayed.");}

    @Test
    public void testAddToCart() {
        ExtentTest test = TestListener.getTest();
        test.log(Status.INFO, "Adding product to cart.");
        productPage.addProductToCart("Sauce Labs Backpack");

        test.log(Status.INFO, "Checking if product is added to cart.");
        boolean productInCart = productPage.isProductInCart("Sauce Labs Backpack");

        assertTrue(productInCart, "Product was not added to cart.");
        test.log(Status.PASS, "Product is added to cart."); }
}

