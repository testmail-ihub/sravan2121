package tests;

import config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import utils.WebDriverFactory;

import static org.testng.Assert.assertTrue;

public class LoginTest {

    private WebDriver driver;
    private LoginPage loginPage;
    private ConfigManager configManager;

    @BeforeMethod
    public void setup() {
        configManager = new ConfigManager();
        driver = WebDriverFactory.getDriver(configManager.getProperty("browser"));
        driver.get(configManager.getProperty("url"));
        loginPage = new LoginPage(driver);
    }

    @Test
    public void testSuccessfulLogin() {
        loginPage.enterUsername(configManager.getProperty("username"));
        loginPage.enterPassword(configManager.getProperty("password"));
        loginPage.clickLoginButton();
        assertTrue(driver.getCurrentUrl().contains("dashboard"), "Login was not successful");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
