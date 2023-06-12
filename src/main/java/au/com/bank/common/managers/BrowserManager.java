package au.com.bank.common.managers;

import au.com.bank.common.utility.Constants;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.MoveTargetOutOfBoundsException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class BrowserManager {
    private static final long DEFAULT_WAIT_IN_MINUTES_PRESENCE = 1;
    private static final String ARGUMENTS_0_SCROLL_INTO_VIEW_TRUE = "arguments[0].scrollIntoView(true);";

    private BrowserManager() {
        // prevent instantiation
    }
    private static final String WAITING_FOR_INVISIBILITY_OF_LOCATOR = "Waiting For Invisibility of Locator ";

    private static final String JS_LOAD = "return document.readyState==\"complete\";";

    private static final Logger LOGGER = Logger.getLogger(BrowserManager.class);

    private static final int DEFAULT_WAIT_IN_SECONDS_VISIBILITY = 45;
    private static final int POLL_EVERY_VISIBILITY = 3;
    private static final int SCROLL_TO_COORDINATE = 1500;
    private static final int POLL_EVERY_INVISIBILITY = 2;

    public static void waitOnClickable(By locator) {
        LOGGER.debug("Waiting For Clickability of Locator " + locator.toString());
        (new FluentWait<WebDriver>(DriverManager.getDriver())).pollingEvery(Duration.ofSeconds(POLL_EVERY_VISIBILITY))
                .withTimeout(Duration.ofSeconds(DEFAULT_WAIT_IN_SECONDS_VISIBILITY))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.elementToBeClickable(locator),
                        ExpectedConditions.jsReturnsValue(JS_LOAD)));
    }

    public static void waitOnPresenceSeconds(By locator, int time) {
        (new FluentWait<WebDriver>(DriverManager.getDriver())).pollingEvery(Duration.ofSeconds(POLL_EVERY_VISIBILITY))
                .withTimeout(Duration.ofSeconds(time)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator),
                        ExpectedConditions.jsReturnsValue(JS_LOAD)));
    }

    public static void waitOnInVisible(WebDriver driver, By locator, int seconds) {
        LOGGER.debug(WAITING_FOR_INVISIBILITY_OF_LOCATOR + locator.toString());
        (new FluentWait<WebDriver>(driver)).pollingEvery(Duration.ofSeconds(POLL_EVERY_INVISIBILITY))
                .withTimeout(Duration.ofSeconds(seconds)).ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.invisibilityOfElementLocated(locator),
                        ExpectedConditions.jsReturnsValue(JS_LOAD)));
    }

    public static void scrollLocatorToBottom(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        LOGGER.debug("Locator Scrolled to bottom of page");
        js.executeScript("arguments[0].scrollIntoView(false);", DriverManager.getDriver().findElement(locator));
    }

    public static void waitForJavaScriptLoad() {
        LOGGER.debug("Waiting for Java script Load to Complete");
        try {
            new FluentWait<WebDriver>(DriverManager.getDriver()).pollingEvery(Duration.ofSeconds(POLL_EVERY_VISIBILITY))
                    .withTimeout(Duration.ofSeconds(180)).until(ExpectedConditions.jsReturnsValue(JS_LOAD));
        } catch (org.openqa.selenium.TimeoutException e) {
            LOGGER.error(e.getMessage());
        }
    }
    public static void moveToElement(By locator) {
        BrowserManager.waitOnPresence(DriverManager.getDriver(), locator);
        WebElement element = DriverManager.getDriver().findElement(locator);
        Actions actions = new Actions(DriverManager.getDriver());
        actions.moveToElement(element);
        try {
            actions.perform();
        } catch (JavascriptException | StaleElementReferenceException | ElementNotInteractableException
                 | MoveTargetOutOfBoundsException e) {
            BrowserManager.waitOnPresence(DriverManager.getDriver(), locator);
            waitForJavaScriptLoad();
            BrowserManager.scrollViaJS(locator);
        }
        LOGGER.debug("Moving to Locator " + locator.toString());
    }
    public static void scrollViaJS(By locator) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript(ARGUMENTS_0_SCROLL_INTO_VIEW_TRUE, DriverManager.getDriver().findElement(locator));
    }
    public static void waitOnPresence(WebDriver driver, By locator) {
        LOGGER.debug("Waiting For Presence of Locator " + locator.toString());
        (new FluentWait<WebDriver>(driver)).pollingEvery(Duration.ofSeconds(POLL_EVERY_VISIBILITY))
                .withTimeout(Duration.ofMinutes(DEFAULT_WAIT_IN_MINUTES_PRESENCE))
                .ignoring(NoSuchElementException.class).ignoring(StaleElementReferenceException.class)
                .until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(locator),
                        ExpectedConditions.jsReturnsValue(JS_LOAD)));
    }

    public static void genericWait(int sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            LOGGER.error(e);
            Thread.currentThread().interrupt();
        }
    }
    public static void scrollTillLocatorIsFound(By locator) {
        List<WebElement> elements = DriverManager.getDriver().findElements(locator);
        int counter = 0;
        int coorinate = SCROLL_TO_COORDINATE;
        do {
            if (elements.isEmpty()) {
                BrowserManager.scrollViaJSToCoordinate(coorinate);
                elements = DriverManager.getDriver().findElements(locator);
                coorinate = +100;
                counter++;
                LOGGER.debug("Locator not found on Scroll :" + (counter + 1));
            } else {
                BrowserManager.waitOnPresence(DriverManager.getDriver(), locator);
                Actions builder = new Actions(DriverManager.getDriver());
                builder.moveToElement(DriverManager.getDriver().findElement(locator));
                LOGGER.debug("Moving to Locator : " + locator);
                try {
                    builder.perform();
                } catch (JavascriptException | MoveTargetOutOfBoundsException | StaleElementReferenceException
                         | ElementNotInteractableException e) {
                    scrollLocatorToBottom(locator);
                }
                break;
            }
        } while (counter <= Constants.TOTAL_LOOP_COUNT.getIntValue());
        BrowserManager.waitOnPresence(DriverManager.getDriver(), locator);
    }
    public static void scrollViaJSToCoordinate(int coordinate) {
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("window.scrollTo(0, " + coordinate + ");");
    }
    public static void scrollLocatorToTop(By locator) {
        BrowserManager.waitOnPresenceSeconds(locator, 60);
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        LOGGER.debug("Locator Scrolled to top of page");
        js.executeScript(ARGUMENTS_0_SCROLL_INTO_VIEW_TRUE, DriverManager.getDriver().findElement(locator));
    }
}
