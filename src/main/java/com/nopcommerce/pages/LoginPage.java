package com.nopcommerce.pages;

import com.nopcommerce.base.BasePage;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    //Locators
    private final By inputUserName = By.id("user-name");
    private final By inputPassword = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By cartIcon = By.id("shopping_cart_container");



    // --- Actions ---

    public LoginPage enterUsername (String username) {
        type(inputUserName, username);
        return this;
    }
    public LoginPage enterPassword (String password){
        type(inputPassword, password);
        return this;
    }

    public LoginPage clickLoginButton() {
        click(loginButton);
        return this;
    }

    public void loginAs(String username, String password) {
        enterUsername(username)
                .enterPassword(password)
                .clickLoginButton();
        dismissChromePopupIfPresent(); // ← cierra el popup si aparece
    }


    // --- Verifications ---
    public boolean isLoggedIn(){
        return isDisplayed(cartIcon);
    }

    public boolean isErrorDisplayed(){
        return isDisplayed(errorMessage);
    }

    public String getErrorMessage(){
        return getText(errorMessage);
    }


}
