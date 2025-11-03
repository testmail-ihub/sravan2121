package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * LoginPage represents the Login screen of the banking application.
 * It provides methods to perform login actions and validate login-related elements.
 */
public class LoginPage {

    private WebDriver driver;

    // ---------- Page Elements ----------
    @FindBy(id = "username")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "loginBtn")
    private WebElement loginButton;

    @FindBy(css = ".error-message")
    private WebElement errorMessage;

    // ---------- Constructor ----------
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ---------- Page Actions ----------

    /**
     * Enters the username into the username field.
     *
     * @param username - user login name
     */
    public void enterUsername(String username) {
        usernameField.clear();
        usernameField.sendKeys(username);
    }

    /**
     * Enters the password into the password field.
     *
     * @param password - user password
     */
    public void enterPassword(String password) {
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    /**
     * Clicks on the Login button.
     */
    public void clickLogin() {
        loginButton.click();
    }

    /**
     * Performs the full login action.
     *
     * @param username - user login name
     * @param password - user password
     * @return DashboardPage instance after successful login
     */
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new DashboardPage(driver);  // Navigates to next page
    }

    /**
     * Returns the displayed error message if login fails.
     */
    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Returns true if the login page is displayed.
     */
    public boolean isLoginPageDisplayed() {
        return usernameField.isDisplayed() && passwordField.isDisplayed();
    }
}