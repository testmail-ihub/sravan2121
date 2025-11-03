package utils;

import config.ConfigManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.time.Duration;

/**
 * WebDriverFactory is responsible for creating and managing WebDriver instances
 * based on the browser type defined in config.properties.
 */
public class WebDriverFactory {

    private static WebDriver driver;

    /**
     * Initializes and returns a WebDriver instance based on the browser type.
     */
    public static WebDriver createDriver() {
        if (driver == null) {
            String browser = ConfigManager.getInstance().getBrowser().toLowerCase();
            System.out.println("🧭 Launching browser: " + browser);

            switch (browser) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--start-maximized");
                    driver = new ChromeDriver(chromeOptions);
                    break;

                case "firefox":
                    driver = new FirefoxDriver();
                    driver.manage().window().maximize();
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    driver.manage().window().maximize();
                    break;

                default:
                    throw new IllegalArgumentException("❌ Unsupported browser: " + browser);
            }

            // Apply default timeouts from config
            int implicitWait = ConfigManager.getInstance().getImplicitWait();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWait));
        }

        return driver;
    }

    /**
     * Quits the WebDriver instance and cleans up resources.
     */
    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("🧹 Browser closed and driver cleaned up.");
        }
    }

    /**
     * Returns the current active WebDriver instance.
     */
    public static WebDriver getDriver() {
        if (driver == null) {
            createDriver();
        }
        return driver;
    }
}