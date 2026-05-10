package com.nopcommerce.pages;

import com.nopcommerce.base.BasePage;
import com.nopcommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

     // Locators
     private final By cartTitle = By.className("title");
     private final By cartItems = By.className("cart_item");
     private final By checkoutButton = By.id("checkout");
     private final By nameLocator = By.className("inventory_item_name");
     private final By iconCart = By.id("shopping_cart_container");

     // --- Actions ---
     public boolean isCartPageDisplayed(){
         return isDisplayed(cartTitle);
     }

     public int getCartItemCount() {
         return driver.findElements(cartItems).size();
     }

     public List<String> getCartItemNames() {
         List<String> names = new ArrayList<>();
         List<WebElement> elements = driver.findElements(nameLocator);
         for (WebElement element : elements) {
             names.add(element.getText());
         }
         return names;
     }

    public void removeItemFromCart(String itemNameToRemove) {
        List<WebElement> items = driver.findElements(cartItems);
        for (WebElement item : items) {
            By removeLocator = By.cssSelector("button.cart_button");
            String name = item.findElement(nameLocator).getText();
            if (name.equals(itemNameToRemove)) {
                item.findElement(removeLocator).click();
                break;
            }
        }
    }

     public void clickCheckout(){
         click(checkoutButton);
     }

     public boolean isCartEmpty(){
         return getCartItemCount() == 0;
     }

    public void goToCart() {
        click(iconCart);
        WaitUtils.waitForVisible(cartTitle); // espera a que cargue la página del carrito
    }
}
