package com.nopcommerce.tests;

import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.CartPage;
import com.nopcommerce.pages.CheckoutPage;
import com.nopcommerce.pages.InventoryPage;
import com.nopcommerce.pages.LoginPage;
import com.nopcommerce.utils.ConfigReader;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import io.qameta.allure.Description;

public class CheckoutTest extends BaseTest {
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

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
        cartPage.clickCheckout();
        checkoutPage = new CheckoutPage();
    }

    @Test (description = "Verificar llenado del formulario de checkout",
    groups = {"smoke","regression"})
    @Severity(SeverityLevel.BLOCKER)
    @Story("Como usuario quiero completar el formulario de checkout correctamente para finalizar mi compra")
    @Description("Este test verifica que el usuario pueda completar correctamente el formulario de checkout con datos válidos y finalizar la orden exitosamente")
    public void testFillCheckoutForm(){
        checkoutPage.fillCheckoutForm("Alan","Santos","12345");
        checkoutPage.clickContinue();
        Assert.assertFalse(checkoutPage.isErrorDisplayed(), "Se muestra un error al llenar el formulario de checkout con datos válidos");
        checkoutPage.clickFinish();
        Assert.assertTrue(checkoutPage.isOrderCompleted(), "El mensaje de orden completada no se muestra después de finalizar el checkout");
        Assert.assertEquals(checkoutPage.getOrderCompletedMessage(),ConfigReader.get("message.order.completed"), "El mensaje de orden completada no es el esperado");
    }

    @Test(description = "Verificar que se muestre un error al dejar campo de nombre vacío en el formulario de checkout",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar mensajes de validación cuando dejo campos obligatorios vacíos en el checkout")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente cuando el campo nombre está vacío en el formulario de checkout")
    public void testCheckoutFormEmptyFirstName(){
        checkoutPage.fillCheckoutForm("","Santos","12345");
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "No se muestra un error al dejar el campo de nombre vacío en el formulario de checkout");
        Assert.assertEquals(checkoutPage.getErrorMessage(),ConfigReader.get("error.checkout.firstname"), "El mensaje de error no es el esperado al dejar el campo de nombre vacío en el formulario");
    }

    @Test(description = "Verificar que se muestre un error al dejar campo de apellido vacío en el formulario de checkout",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar mensajes de validación cuando dejo campos obligatorios vacíos en el checkout")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente cuando el campo nombre está vacío en el formulario de checkout")
    public void testCheckoutFormEmptyLastName(){
        checkoutPage.fillCheckoutForm("Alan","","12345");
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "No se muestra un error al dejar el campo de apellido vacío en el formulario de checkout");
        Assert.assertEquals(checkoutPage.getErrorMessage(),ConfigReader.get("error.checkout.lastname"), "El mensaje de error no es el esperado al dejar el campo de apellido vacío en el formulario");
    }

    @Test(description = "Verificar que se muestre un error al dejar campo de código postal vacío en el formulario de checkout",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar mensajes de validación cuando dejo campos obligatorios vacíos en el checkout")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente cuando el campo código postal está vacío en el formulario de checkout")
    public void testCheckoutFormEmptyPostalCode(){
        checkoutPage.fillCheckoutForm("Alan","Santos","");
        checkoutPage.clickContinue();
        Assert.assertTrue(checkoutPage.isErrorDisplayed(), "No se muestra un error al dejar el campo de código postal vacío en el formulario de checkout");
        Assert.assertEquals(checkoutPage.getErrorMessage(),ConfigReader.get("error.checkout.postalcode"), "El mensaje de error no es el esperado al dejar el campo de código postal vacío en el formulario");
    }

}

