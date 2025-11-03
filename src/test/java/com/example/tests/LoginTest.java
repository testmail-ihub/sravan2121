package com.example.tests;

import com.example.pages.HomePage;
import com.example.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {
        // Initialize page objects
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);

        // Perform login (replace with valid credentials)
        loginPage.loginAs("yourUsername", "yourPassword");

        // Verify successful login
        boolean loginSuccessful = homePage.isLoginSuccessful();
        Assert.assertTrue(loginSuccessful, "Login should be successful and welcome message displayed.");
    }
}
