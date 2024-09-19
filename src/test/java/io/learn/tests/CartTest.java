package io.learn.tests;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import io.learn.listeners.TestListener;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CartTest extends BaseTest {

    @Test(priority = 1)
    public void testAddingProductToCart() {
        ExtentTest test = TestListener.getTest();
        test.log(Status.INFO, "Adding product to cart.");
        productPage.addProductToCart("Sauce Labs Backpack");

        test.log(Status.INFO, "Navigating to cart.");
        cartPage = productPage.goToCart();

        test.log(Status.INFO, "Verifying product is in the cart.");
        boolean productInCart = cartPage.isProductInCart("Sauce Labs Backpack");
        assertTrue(productInCart, "Product is not in the cart.");
        test.log(Status.PASS, "Product is in the cart.");
    }

    @Test(priority = 2)
    public void testRemoveFromCart() {
        ExtentTest test = TestListener.getTest();

        test.log(Status.INFO, "Removing product from cart.");
        cartPage.removeProductFromCart("Sauce Labs Backpack");

        test.log(Status.INFO, "Verifying product is removed from the cart.");
        boolean productRemoved = !cartPage.isProductInCart("Sauce Labs Backpack");
        assertTrue(productRemoved, "Product is still in the cart after removal.");
        test.log(Status.PASS, "Product is removed from the cart.");
    }
}
