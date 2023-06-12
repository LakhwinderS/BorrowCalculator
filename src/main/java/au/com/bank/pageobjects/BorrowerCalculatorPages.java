package au.com.bank.pageobjects;

import au.com.bank.common.datareader.CommonEnvConfigReader;
import au.com.bank.common.managers.BrowserManager;
import au.com.bank.common.managers.DriverManager;
import au.com.bank.common.utility.ElementUtils;
import dev.failsafe.internal.util.Assert;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;


public class BorrowerCalculatorPages {
    private static final Logger LOGGER = Logger.getLogger(BorrowerCalculatorPages.class);

    private String applicationType = "//*[@for='application_type_(.?)']";
  //  private By applicationTypeLocator =By.xpath("//*[@for='application_type_single']");
    private By dependents= By.xpath("//*[@title='Number of dependants']");
    private By propertyTpeLivin = By.xpath("//*[@for='borrow_type_home']");
    private By propertyTpeRental = By.xpath("//*[@for='borrow_type_investment']");
    private By income = By.xpath("//*[@aria-labelledby='q2q1']");
    private By otherIncome  = By.xpath("//*[@aria-labelledby='q2q2']");
    private By livingExpenses =By.xpath("//*[@aria-labelledby='q3q1']");
    private By currentHomeLoan= By.xpath("//*[@aria-labelledby='q3q2']");
    private By otherLoan= By.xpath("//*[@aria-labelledby='q3q3']");
    private By otherCommitment = By.xpath("//*[@aria-labelledby='q3q4']");
    private By creditLimit = By.xpath("//*[@aria-labelledby='q3q5']");
    private By borrowCalculatorBtn= By.id("btnBorrowCalculater");
   private By estimateLoanValue = By.id("borrowResultTextAmount");
   private By startOver = By.xpath("//*[@class='borrow__result__action']/span/following-sibling::div/button");
    public void navigateToBankURL() {
        LOGGER.info("Navigate to the bank loan estimation page");
        DriverManager.getDriver().get(CommonEnvConfigReader.getEnvConfig().getUrl());

    }

    public void enterIncomeDeatail(String applicationTypeValue, String dependentsValue, String propertTypeValue, String incomeValue, String otherIncomeValue, String livingExpensesValue, String currentHomeLoanValue, String otherLoanValue, String otherCommitmentValue, String creditCardLimitValue) {
        LOGGER.info("Enter the Income and expenses Details");
        By applicationTypeLocator = ElementUtils.getLocatorByXPath(applicationType,applicationTypeValue.toLowerCase());
        BrowserManager.waitOnPresenceSeconds(applicationTypeLocator,60);
        ElementUtils.clickJS(applicationTypeLocator);
        ElementUtils.selectDropDownByVisibleText(dependents,dependentsValue);
        if(propertTypeValue.equals("LiveIn")){
            ElementUtils.clickJS(propertyTpeLivin);
        }
        else{
            ElementUtils.clickJS(propertyTpeRental);
        }
        ElementUtils.sendKeysIfNotNull(income,incomeValue);
        ElementUtils.sendKeysIfNotNull(otherIncome,otherIncomeValue);
        ElementUtils.sendKeysIfNotNull(livingExpenses,livingExpensesValue);
        ElementUtils.sendKeysIfNotNull(currentHomeLoan,currentHomeLoanValue);
        ElementUtils.sendKeysIfNotNull(otherLoan,otherLoanValue);
        ElementUtils.sendKeysIfNotNull(otherCommitment,otherCommitmentValue);
        ElementUtils.sendKeysIfNotNull(creditLimit,creditCardLimitValue);


    }

    public void clickOnBorrowerBtn() {
        LOGGER.info("Click on Work out how much I could borrow  button");
        ElementUtils.clickJS(borrowCalculatorBtn);
    }

    public void verifyEstimateAmount(String estimateAmount) {
        LOGGER.info("Verify the Estimate Borrower Amount");
        String updatedactualAmount =null;
        BrowserManager.genericWait(3);
        String actualAmount = ElementUtils.getText(estimateLoanValue);
        System.out.println("value of Actual Amount"+ actualAmount);
        if(actualAmount.contains("$")){
            updatedactualAmount = actualAmount.replace("$", "");
        }
        Assert.isTrue(updatedactualAmount.equals(estimateAmount), "not matched");
    }

    public void clickOnStartOver() {
        LOGGER.info("Click on Start Over  button");
        ElementUtils.clickJS(startOver);
    }
    public void verifyBlankForm() {
        LOGGER.info("Verify Blank form");
        String incomeAmount = ElementUtils.getText(income);
        String newIncomeAmount = incomeAmount.replace("$","");
        Assert.isTrue(newIncomeAmount.isEmpty(), "Value should be blank");

    }
}
