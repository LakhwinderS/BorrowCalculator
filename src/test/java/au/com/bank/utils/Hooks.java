package au.com.bank.utils;

import au.com.bank.common.managers.DriverManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import org.openqa.selenium.WebDriverException;

public class Hooks {
    private static final Logger LOGGER = Logger.getLogger(Hooks.class);

    @Before()
    public void setup(){
        LOGGER.info("Driver started");
        DriverManager.getDriver();

    }
    @After()
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {

            try {
                LOGGER.info(scenario.getName() + " is Failed");
                final byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png","errorcreenshot"); // ... and embed it in
            } catch (WebDriverException e) {
                e.printStackTrace();
            }

        } else {
            try {
                LOGGER.info(scenario.getName() + " is pass");
                scenario.attach(((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES), "image/png","errorcreenshot");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        DriverManager.closeDriver();
    }

}
