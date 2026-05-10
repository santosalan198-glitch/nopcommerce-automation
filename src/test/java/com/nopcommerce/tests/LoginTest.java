package com.nopcommerce.tests;
import com.nopcommerce.base.BaseTest;
import com.nopcommerce.pages.LoginPage;
import com.nopcommerce.utils.ConfigReader;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import io.qameta.allure.Description;
public class LoginTest extends BaseTest{
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage();
    }

    // DATA PROVIDER PARA CASOS POSITIVOS

    @DataProvider (name = "validUsers")
    public Object[][] validUsers() {
        return new Object[][]{
                {ConfigReader.get("standard.user"), ConfigReader.get("standard.password")},
                {ConfigReader.get("performance.user"), ConfigReader.get("performance.password")},
                {ConfigReader.get("problem.user"), ConfigReader.get("problem.password")},
        };
    }

    @Test(dataProvider = "validUsers",
            description = "Login exitoso con diferentes usuarios válidos",
            groups = {"regression"})
    @Severity(SeverityLevel.BLOCKER)
    @Story("Como usuario quiero iniciar sesión correctamente para acceder a la aplicación")
    @Description("Este test verifica que diferentes usuarios válidos puedan iniciar sesión exitosamente en la aplicación")
    public void testSuccessfulLogin(String username, String password) {
        loginPage.loginAs(username, password);
        Assert.assertTrue(loginPage.isLoggedIn(), "El usuario" + username + " debería haber iniciado sesión exitosamente");
    }

    // CASOS NEGATIVOS

    @Test(description = "Login falla con usuario bloqueado",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero visualizar mensajes claros cuando mi acceso esté restringido")
    @Description("Este test verifica que un usuario bloqueado no pueda iniciar sesión y que se muestre el mensaje de error correspondiente")
    public void testLockedUser() {
        loginPage.loginAs(
                ConfigReader.get("locked.user"),
                ConfigReader.get("locked.password")
        );
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Debería mostrarse un mensaje de error para el usuario bloqueado");
        Assert.assertEquals(loginPage.getErrorMessage(),
                ConfigReader.get("error.locked"),"El mensaje de error no coincide con el esperado para el usuario bloqueado");
        Assert.assertFalse(loginPage.isLoggedIn(),"El usuario bloqueado no debería poder iniciar sesión");
    }

    @Test(description = "Login falla con credenciales incorrectas",
    groups = {"regression"})
    @Severity(SeverityLevel.CRITICAL)
    @Story("Como usuario quiero recibir validaciones cuando ingreso credenciales incorrectas")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente al ingresar un usuario o contraseña inválidos")
    public void testLoginWithInvalidCredentials() {
        loginPage.loginAs("wrong_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Debería mostrarse un mensaje de error para credenciales incorrectas");
        Assert.assertEquals(loginPage.getErrorMessage(),
                ConfigReader.get("error.invalid.credentials"),"El mensaje de error no coincide con el esperado para credenciales incorrectas");
    }

    @Test(description = "Login falla con ambos campos vacios",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero recibir validaciones cuando dejo campos obligatorios vacíos")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente cuando los campos usuario y contraseña están vacíos")
    public void testLoginWithEmptyCredentials() {
        loginPage.loginAs("", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Debería mostrarse un mensaje de error para campos vacíos");
        Assert.assertEquals(loginPage.getErrorMessage(),
                ConfigReader.get("error.empty.username"),"El mensaje de error no coincide con el esperado para campos vacíos");
    }
    @Test(description = "Login falla con password vacio",
    groups = {"regression"})
    @Severity(SeverityLevel.NORMAL)
    @Story("Como usuario quiero recibir validaciones cuando dejo la contraseña vacía")
    @Description("Este test verifica que se muestre el mensaje de error correspondiente cuando el campo contraseña está vacío")
    public void testLoginWithEmptyPassword() {
        loginPage.loginAs("standard_user", "");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Debería mostrarse un mensaje de error para password vacío");
        Assert.assertEquals(loginPage.getErrorMessage(),
                ConfigReader.get("error.empty.password"),"El mensaje de error no coincide con el esperado para password vacío");
    }

}
