package com.testmail.pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class DashboardPage extends BasePage {

    @FindBy(id = "userGreeting")
    private WebElement userGreetingElement;

    public DashboardPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public String getUserGreeting() {
        wait.until(ExpectedConditions.visibilityOf(userGreetingElement));
        return userGreetingElement.getText();
    }
}
