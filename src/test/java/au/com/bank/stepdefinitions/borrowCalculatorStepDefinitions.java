package au.com.bank.stepdefinitions;

import au.com.bank.pageobjects.BorrowerCalculatorPages;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class borrowCalculatorStepDefinitions {
    BorrowerCalculatorPages borrowerCalculatorPages = new BorrowerCalculatorPages();
    @Given("User navigates to Application URL")
    public void userNavigatesToApplicationURL() {
        borrowerCalculatorPages.navigateToBankURL();
    }

    @When("I enter the following details {string} , {string} , {string}, {string} , {string}, {string}, {string}, {string} , {string} , {string}")
    public void iEnterTheFollowingDetails(String applicationType, String dependents, String propertType, String income, String otherIncome, String livingExpenses, String currentHomeLoan, String otherLoan, String otherCommitment, String creditCardLimit) {
    borrowerCalculatorPages.enterIncomeDeatail(applicationType, dependents, propertType,  income,  otherIncome,  livingExpenses,  currentHomeLoan,  otherLoan,  otherCommitment,  creditCardLimit);
    }


    @Then("I should see borrowing estimates {string}")
    public void iShouldSeeBorrowingEstimates(String estimateValue) {
        borrowerCalculatorPages.verifyEstimateAmount(estimateValue);
    }

    @And("I click on Start over button")
    public void iClickOnStartOverButton() {
        borrowerCalculatorPages.clickOnStartOver();
    }

    @Then("I should see the blank form.")
    public void iShouldSeeTheBlankForm() {
        borrowerCalculatorPages.verifyBlankForm();
    }

    @And("I click  work out how much I could borrow button")
    public void iClickWorkOutHowMuchICouldBorrowButton() {
        borrowerCalculatorPages.clickOnBorrowerBtn();
    }
}
