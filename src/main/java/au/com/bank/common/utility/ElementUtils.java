package au.com.bank.common.utility;

import au.com.bank.common.managers.BrowserManager;
import au.com.bank.common.managers.DriverManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.security.SecureRandom;
import java.util.List;

public class ElementUtils {
    private static final Logger LOGGER = Logger.getLogger(ElementUtils.class);

    public static void clickJS(By locator) {
        try {
            elemClick(locator, true);
        } catch (ElementClickInterceptedException | JavascriptException e) {
            LOGGER.info("Click Intercepted for Locator; Trying using JS Click on Locator" + locator);
            BrowserManager.scrollLocatorToBottom(locator);
            try {
                clickViaJS(locator);
            } catch (ElementClickInterceptedException | JavascriptException e1) {
                LOGGER.info("Click Intercepted for JS Click; Trying using Web Element Click on Locator" + locator);
                BrowserManager.moveToElement(locator);
                clickViaJS(locator);
            }
        }
    }
    public static void clickViaJS(By ele) {
        BrowserManager.waitOnClickable(ele);
        LOGGER.info(ele + " is Clicked");
        DriverManager.getDriver().findElement(ele).click();
    }
    public static void sendKeysIfNotNull(By locator, String value) {
        if (!StringUtils.isNullorEmpty(value)) {
            ElementUtils.enterValueWithNoLog(locator, value);
        }
    }

    public static void enterValueWithNoLog(By ele, String value) {
        BrowserManager.waitOnClickable(ele);
        enterValueWithNoLog(DriverManager.getDriver().findElement(ele), value);
    }
    public static void enterValueWithNoLog(WebElement ele, String value) {
        ele.clear();
        ele.click();
        ele.sendKeys(value);
    }
    private static void elemClick(By locator, boolean isJSClick) {
        boolean staleElement = true;
        int count = 0;
        while (staleElement) {
            if (Constants.TOTAL_CLICK_COUNTER.getIntValue() == count) {
                BrowserManager.waitOnClickable(locator);
                DriverManager.getDriver().findElement(locator).click();
                break;
            }
            try {
                if (isJSClick) {
                    Actions builder = new Actions(DriverManager.getDriver());
                    builder.moveToElement(DriverManager.getDriver().findElement(locator))
                            .click(DriverManager.getDriver().findElement(locator));
                    builder.perform();
                    LOGGER.info("Action Click on locator " + locator);
                } else {
                    clickExtracted(locator);
                }
                staleElement = false;
            } catch (Exception e) {
                staleElement = true;
                LOGGER.debug(e);
                try {
                    BrowserManager.genericWait(1);
                } catch (Exception e1) {
                    LOGGER.debug(e1);
                }
                BrowserManager.scrollTillLocatorIsFound(locator);
            }
            count++;
        }
    }
    public static String getText(By locator) {
        BrowserManager.scrollTillLocatorIsFound(locator);
        BrowserManager.waitOnPresenceSeconds(locator,60);
        return getTextWithoutWaits(locator).trim();
    }
    public static String getTextWithoutWaits(By locator) {
        WebElement ele = DriverManager.getDriver().findElement(locator);
        printLog(ele, null, null);
        return ele.getText();
    }
    protected static void printLog(WebElement ele, Keys key, String value) {
        if (key != null) {
            LOGGER.info(ele.toString() + " value=[" + key + "]");
        } else if (!StringUtils.isNullorEmpty(value)) {
            LOGGER.info(ele.toString() + " value=[" + value + "]");
        } else {
            LOGGER.info(ele);
        }
    }
    public static String selectDropDownByVisibleText(By locator, String dropDownText) {
        if (!StringUtils.isNullorEmpty(dropDownText)) {
            clickJS(locator);
            BrowserManager.waitForJavaScriptLoad();
            Select dropdown = new Select((DriverManager.getDriver().findElement(locator)));
            List<WebElement> options = dropdown.getOptions();

            if (dropDownText.equalsIgnoreCase(Constants.ANYVALUE_REPLACEMENT.getStringValue())) {
                SecureRandom rand = new SecureRandom();
                dropDownText = getText((By) options.get(rand.nextInt(options.size() - 1) + 1));
            }
            for (WebElement optn : options) {
                String text = optn.getText();
                if (text.contentEquals(dropDownText)) {
                    dropdown.selectByVisibleText(text);
                    BrowserManager.waitForJavaScriptLoad();
                    break;
                }
            }
            LOGGER.info("Select Locator " + locator.toString() + " and choosing choice by text - : " + dropDownText);
            clickJS(locator);
        }
        return dropDownText;
    }
    private static void clickExtracted(By locator) {
        BrowserManager.waitOnClickable(locator);
        BrowserManager.moveToElement(locator);
        LOGGER.info("Click on locator" + locator);
        try {
            DriverManager.getDriver().findElement(locator).click();
        } catch (ElementNotInteractableException e) {
            BrowserManager.scrollLocatorToTop(locator);
            try {
                DriverManager.getDriver().findElement(locator).click();
            } catch (ElementNotInteractableException e1) {
                BrowserManager.scrollLocatorToBottom(locator);
                DriverManager.getDriver().findElement(locator).click();
            }
        }
    }
    public static By getLocatorByXPath(String locatorString, String valueToReplace) {
        if (null != valueToReplace) {
            if (valueToReplace.contains(Constants.BLANK_VALUE.getStringValue())) {
                valueToReplace = StringUtils.removeBlankReference(valueToReplace);
            }
            return By.xpath(StringUtils.format(locatorString, valueToReplace));
        }
        return By.xpath(locatorString);
    }
}
