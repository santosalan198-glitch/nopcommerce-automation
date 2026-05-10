package com.nopcommerce.pages;
import com.nopcommerce.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import java.util.ArrayList;
import java.util.List;

public class InventoryPage extends BasePage {


    // Locators
    private final By productTitle = By.className("title");
    private final By productItems = By.className("inventory_item");
    private final By addToCartButtons = By.cssSelector("button.btn_inventory");
    private final By cartIcon = By.id("shopping_cart_container");
    private final By productName = By.className("inventory_item_name");
    private final By productPrice = By.className("inventory_item_price");
    private final By productDescription = By.className("inventory_item_desc");
    private final By sortDropdown = By.className("product_sort_container");
    private final By sortOptionAZ = By.cssSelector("option[value='az']");
    private final By sortOptionZA = By.cssSelector("option[value='za']");
    private final By sortOptionLowHigh = By.cssSelector("option[value='lohi']");
    private final By sortOptionHighLow = By.cssSelector("option[value='hilo']");
    private final By removeButtons = By.cssSelector("button.btn_secondary");


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

    public void addProductToCart(int quantity){
        List <WebElement> buttons = driver.findElements(addToCartButtons);
        if (quantity > buttons.size()) {
            throw new IllegalArgumentException("Solo hay " + buttons.size() + "productos disponibles");
            // Limitar la cantidad al número de productos disponibles
        }
        for (int i = 0; i < quantity; i++){
            buttons.get(i).click();
        }
    }
    public void addProductsByIndex(int... indexes) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        for (int index : indexes) {
            if (index >= buttons.size()) {
                throw new IllegalArgumentException(
                        "Posicion " + index + " no existe"
                );
            }
            buttons.get(index).click();
        }
    }
    public int getCartCount(){
        return Integer.parseInt(driver.findElement(cartIcon).getText());
    }
}
