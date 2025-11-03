package com.example.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(id = "welcomeMessage")
    private WebElement welcomeMessage;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /**
     * Checks whether the welcome message is displayed, indicating a successful login.
     * @return true if the welcome message is visible, false otherwise
     */
    public boolean isLoginSuccessful() {
        try {
            return welcomeMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
