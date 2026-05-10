package com.nopcommerce.pages;
import com.nopcommerce.base.BasePage;
import com.nopcommerce.utils.WaitUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {


    // Locators
    private final By productTitle = By.className("title");
    private final By productItems = By.className("inventory_item");
    private final By addToCartButtons = By.cssSelector("button.btn_inventory");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By productName = By.className("inventory_item_name");
    private final By productPrice = By.className("inventory_item_price");
    private final By sortDropdown = By.className("product_sort_container");



    // --- Actions ---
    public boolean isInventoryPageDisplayed(){
        return isDisplayed(productTitle);
    }

    public int getProductCount() {
        return driver.findElements(productItems).size();
    }

    public void selectFilter(String filterOption){
        Select dropdown = new Select(driver.findElement(sortDropdown));
        dropdown.selectByVisibleText(filterOption);
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = driver.findElements(productName);
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        List<WebElement> elements = driver.findElements(productPrice);
        for (WebElement element : elements) {
            prices.add(Double.parseDouble(element.getText().replace("$", "")));
        }
        return prices;
    }

    public void addProductsByIndex(int... indexes) {
        WaitUtils.waitForVisible(By.cssSelector("button.btn_inventory"));
        List<WebElement> buttons = driver.findElements(addToCartButtons);

        if (indexes.length > buttons.size()) {
            throw new IllegalArgumentException(
                    "Solo hay " + buttons.size() + " productos disponibles"
            );
        }

        for (int index : indexes) {
            // Cuenta Remove buttons antes del clic
            int removeCountBefore = driver.findElements(
                    By.cssSelector("button.btn_secondary")).size();

            buttons.get(index).click();

            // Espera a que aumente el número de Remove buttons
            final int expectedCount = removeCountBefore + 1;
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(d -> d.findElements(
                            By.cssSelector("button.btn_secondary")).size() == expectedCount);
        }
    }
    public int getCartCount(){
        return Integer.parseInt(driver.findElement(cartBadge).getText());
    }
}
