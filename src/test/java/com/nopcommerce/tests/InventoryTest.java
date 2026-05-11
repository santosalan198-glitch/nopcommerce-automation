package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.InventoryPage;
import com.nopcommerce.pages.LoginPage;
import com.nopcommerce.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class InventoryTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage();
        loginPage.loginAs(
                ConfigReader.get("standard.user"),
                ConfigReader.get("standard.password")
        );
        inventoryPage = new InventoryPage();
    }

    @Test(description = "Verificar que la página de inventario se muestre correctamente",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar correctamente la página de inventario para poder navegar entre los productos")
    @Description("Este test verifica que la página de inventario se cargue correctamente después del login")
    public void testInventoryPageDisplayed() {
        Assert.assertTrue(inventoryPage.isInventoryPageDisplayed(), "La página de inventario no se muestra correctamente");
    }

    @Test(description = "Verificar que los productos se carguen en la página de inventario",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar todos los productos disponibles en el inventario para revisar las opciones de compra")
    @Description("Este test verifica que el número de productos cargados en la página de inventario sea el esperado")
    public void testProductCountIsSix(){
        Assert.assertEquals(inventoryPage.getProductCount(),6,"El número de productos en la página de inventario no es el esperado");
    }

    // CASOS DE FILTROS

    @Test(description = "Verificar filtro por nombre A-Z",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero ordenar los productos alfabéticamente para encontrar productos fácilmente")
    @Description("Este test verifica que los productos se ordenen correctamente de A a Z al aplicar el filtro correspondiente")
    public void testFilterByNameAZ(){
        inventoryPage.selectFilter("Name (A to Z)");
        List<String> actualNames = inventoryPage.getProductNames();
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames);
        Assert.assertEquals(actualNames, sortedNames, "Los productos no están ordenados de A a Z correctamente");
    }

    @Test (description = "Verificar filtro por nombre Z-A",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero ordenar los productos en orden descendente para visualizar diferentes opciones rápidamente")
    @Description("Este test verifica que los productos se ordenen correctamente de Z a A al aplicar el filtro correspondiente")
    public void testFilterByNameZA(){
        inventoryPage.selectFilter("Name (Z to A)");
        List<String> actualNames = inventoryPage.getProductNames();
        List<String> sortedNames = new ArrayList<>(actualNames);
        Collections.sort(sortedNames, Collections.reverseOrder());
        Assert.assertEquals(actualNames, sortedNames, "Los productos no están ordenados de Z a A correctamente");
    }
    @Test (description = "Verificar filtro por precio de menor a mayor",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero ordenar los productos por precio ascendente para encontrar productos más económicos")
    @Description("Este test verifica que los productos se ordenen correctamente por precio de menor a mayor")
    public void testFilterByPriceLowHigh(){
        inventoryPage.selectFilter("Price (low to high)");
        List<Double> actualPrices = inventoryPage.getProductPrices();
        List<Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices);
        Assert.assertEquals(actualPrices, sortedPrices, "Los productos no están ordenados por precio de menor a mayor correctamente");
    }

    @Test (description = "Verificar filtro por precio de mayor a menor",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero ordenar los productos por precio descendente para visualizar primero los productos más caros")
    @Description("Este test verifica que los productos se ordenen correctamente por precio de mayor a menor")
    public void testFilterByPriceHighLow(){
        inventoryPage.selectFilter("Price (high to low)");
        List<Double> actualPrices = inventoryPage.getProductPrices();
        List <Double> sortedPrices = new ArrayList<>(actualPrices);
        Collections.sort(sortedPrices, Collections.reverseOrder());
        Assert.assertEquals(actualPrices, sortedPrices, "Los productos no están ordenados por precio de mayor a menor correctamente");
    }
    // CASOS DE CARRITO
    @Test (description = "Verificar que se puedan agregar productos al carrito",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero agregar productos al carrito para realizar una compra")
    @Description("Este test verifica que los productos seleccionados puedan agregarse correctamente al carrito de compras")
    public void testAddProductsToCart(){
        inventoryPage.addProductsByIndex(0, 2, 5);
        Assert.assertEquals(inventoryPage.getCartCount(), 3, "El número de productos en el carrito no es el esperado después de agregar productos");
    }


}
