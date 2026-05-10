package com.nopcommerce.pages;

import com.nopcommerce.base.BasePage;
import org.openqa.selenium.By;

public class CheckoutPage extends BasePage {

    // Locators
    private final By firstNameInput = By.id("first-name");
    private final By lastNameInput = By.id("last-name");
    private final By postalCodeInput = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By cancelButton = By.id("cancel");
    private final By errorMessage = By.cssSelector("h3[data-test='error']");
    private final By finishButton = By.id("finish");
    private final By precioTotal = By.className("summary_total_label");
    private final By messageComplete = By.className("complete-header");


    // --- Actions ---
    public void fillCheckoutForm(String firstName, String lastName, String zipCode){
        type(firstNameInput, firstName);
        type(lastNameInput, lastName);
        type(postalCodeInput, zipCode);
    }

    public void clickContinue(){
        click(continueButton);
    }
     public boolean isErrorDisplayed(){
        return isDisplayed(errorMessage);
     }

     public String getErrorMessage(){
        return getText(errorMessage);
     }

     public String getOrderTotal(){
        return getText(precioTotal);
     }

     public void clickCancel(){
        click(cancelButton);
     }

     public void clickFinish(){
        click(finishButton);
     }

     public boolean isOrderCompleted(){
        return isDisplayed(messageComplete);
     }

    public String getOrderCompletedMessage() {
        return getText(messageComplete);
    }

}
