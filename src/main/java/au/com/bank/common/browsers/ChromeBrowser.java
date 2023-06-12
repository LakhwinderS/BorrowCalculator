package au.com.bank.common.browsers;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;

public class ChromeBrowser {
    private static boolean isChromeSetup = false;

    private static final String DOWNLOADPATH = System.getProperty("user.dir") + "\\src\\main\\resources";

    public static void closeChromeDriverSetup() {
        isChromeSetup = false;
    }

    public static WebDriver getDriver() {
        if (!isChromeSetup) {
            WebDriverManager.chromedriver().setup();
            isChromeSetup = true;
        }
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--test-type");
        options.addArguments("--allow-running-insecure-content");
        options.addArguments("no-sandbox");
        options.addArguments("disable-extensions");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-web-security");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-notification");
        options.addArguments("--remote-allow-origins=*");
        HashMap<String, Object> chromePrefs = new HashMap<>();
        chromePrefs.put("download.default_directory", DOWNLOADPATH);
        options.setExperimentalOption("prefs", chromePrefs);
        options.setPageLoadStrategy(PageLoadStrategy.NONE);

        return new ChromeDriver(options);
    }

    private ChromeBrowser() {
        // prevent implicit instantiation
    }

}
