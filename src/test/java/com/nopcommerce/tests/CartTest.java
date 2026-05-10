package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.CartPage;
import com.nopcommerce.pages.InventoryPage;
import com.nopcommerce.pages.LoginPage;
import com.nopcommerce.utils.ConfigReader;
import io.qameta.allure.Severity;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class CartTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage();
        loginPage.loginAs(
                ConfigReader.get("standard.user"),
                ConfigReader.get("standard.password")
        );
        inventoryPage = new InventoryPage();
        inventoryPage.addProductsByIndex(1,2,3);
        cartPage = new CartPage();
        cartPage.goToCart();
    }

    @Test (description = "Verificar que la pagina del carrito cargo",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero verificar que la pagina del carrito se muestra correctamente para poder revisar los items que he agregado")
    @Description ("Este test verifica que la pagina del carrito se muestra correctamente despues de agregar productos al carrito")
    public void testAddProductToCart(){
        Assert.assertTrue(cartPage.isCartPageDisplayed(), "La página del carrito no se muestra correctamente");
    }

    @Test (description = "Verificar que el numero de items en el carrito es correto",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero verificar la cantidad de productos en el carrito para confirmar que los items agregados son correctos")
    @Description ("Este test verifica que el numero de productos mostrados en el carrito coincida con la cantidad esperada")
    public void testCartItemCount(){
        Assert.assertEquals(cartPage.getCartItemCount(),3,"El número de items en el carrito no es el esperado");
    }

    @Test (description = "Verificar que los nombres de los items en el carrito son correctos",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar correctamente los productos agregados al carrito para validar mi compra")
    @Description ("Este test verifica que los nombres de los productos agregados se muestren correctamente en el carrito")
    public void testCartItemNames(){
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Bolt T-Shirt"), "El item Sauce Labs Bolt T-Shirt no se encuentra en el carrito");
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Fleece Jacket"), "El item Sauce Labs Fleece Jacket no se encuentra en el carrito");
        Assert.assertTrue(cartPage.getCartItemNames().contains("Sauce Labs Bike Light"), "El item Sauce Labs Bike Light no se encuentra en el carrito");
    }

    @Test (description = "Verificar que se pueda eliminar un item del carrito",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero eliminar productos del carrito para modificar mi selección de compra")
    @Description ("Este test verifica que un producto pueda eliminarse correctamente del carrito y que la cantidad de items se actualice")
    public void testRemoveItemFromCart(){
        cartPage.removeItemFromCart("Sauce Labs Bolt T-Shirt");
        Assert.assertEquals(cartPage.getCartItemCount(),2,"El número de items en el carrito no es el esperado después de eliminar un item");
        Assert.assertFalse(cartPage.getCartItemNames().contains("Sauce Labs Bolt T-Shirt"), "El item Sauce Labs Bolt T-Shirt aún se encuentra en el carrito después de eliminarlo");
    }

    @Test (description = "Verificar que se puede eliminar todos los productos y el carrito quede vacío",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero vaciar el carrito para eliminar todos los productos seleccionados")
    @Description ("Este test verifica que todos los productos puedan eliminarse correctamente y que el carrito quede vacío")
    public void testEmptyCart(){
        cartPage.removeItemFromCart("Sauce Labs Bolt T-Shirt");
        cartPage.removeItemFromCart("Sauce Labs Fleece Jacket");
        cartPage.removeItemFromCart("Sauce Labs Bike Light");
        Assert.assertTrue(cartPage.isCartEmpty(), "El carrito no está vacío después de eliminar todos los items");
    }
}
