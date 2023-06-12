package au.com.bank.common.managers;

import au.com.bank.common.browsers.ChromeBrowser;

import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.apache.log4j.Logger;


public class DriverManager {
    private static final Logger LOGGER = Logger.getLogger(DriverManager.class);
    private static WebDriver driver = null;


    private DriverManager() {
        // prevent implicit instantiation
    }

    /**
     * Creates a new web browser session
     *
     * @return
     */
    public static synchronized WebDriver getDriver() {
        if (driver == null) {
            try {
                driver = ChromeBrowser.getDriver();
                driver.manage().window().maximize();
            } catch (Exception e) {
                LOGGER.error(e);
                LOGGER.error(e.getCause());
                LOGGER.error(e.getMessage());
            }
        }
        return driver;
    }

    public static void closeDriver() {
        if (driver != null) {
            try {
                driver.close();
            } catch (Exception e) {
                LOGGER.error(e);
                LOGGER.error(e.getCause());
                LOGGER.error(e.getMessage());
            }
            driver.quit();
            driver = null;
        }
    }

}
