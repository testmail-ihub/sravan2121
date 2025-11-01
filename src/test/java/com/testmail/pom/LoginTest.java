package com.testmail.pom;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);
        DashboardPage dashboardPage = new DashboardPage(driver);

        loginPage.login("validUsername", "validPassword"); // Replace with actual credentials

        Assert.assertTrue(dashboardPage.getUserGreeting().contains("Welcome"), "Login failed: User greeting not found.");
    }
}
