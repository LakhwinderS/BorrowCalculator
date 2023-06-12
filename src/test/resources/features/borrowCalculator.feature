@borrowerCalculator
Feature: Borrower Calculator
  As a  user
  I want to test borrower calculator for my home applications
Background:
 @sanity
  Scenario Outline: User get the loan borrowing estimation
   Given User navigates to Application URL
   And I enter the following details "<Application type>" , "<Dependants>" , "<Property Type>", "<Income>" , "<Other Income>", "<Living Expenses>", "<Other Home Repayment>", "<Other Loan Repayment>" , "<Other Commitments>" , "<Total Credit>"
    When I click  work out how much I could borrow button
    Then I should see borrowing estimates "<Estimate Amount>"


    Examples:
      |Application type|Dependants|Property Type  |Income |Other Income|Living Expenses|Other Home Repayment|Other Loan Repayment|Other Commitments|Total Credit|Estimate Amount|
      |Single          |0         |LiveIn         |100000  |10000       |500            |0                   |100                 |0                |10000       |491,000        |


  @Regression
  Scenario Outline: User clears the from StartOver Button
    Given User navigates to Application URL
    And I enter the following details "<Application type>" , "<Dependants>" , "<Property Type>", "<Income>" , "<Other Income>", "<Living Expenses>", "<Other Home Repayment>", "<Other Loan Repayment>" , "<Other Commitments>" , "<Total Credit>"
    When I click  work out how much I could borrow button
    And I should see borrowing estimates "<Estimate Amount>"
    And I click on Start over button
    Then I should see the blank form.

    Examples:
      |Application type|Dependants|Property Type  |Income |Other Income|Living Expenses|Other Home Repayment|Other Loan Repayment|Other Commitments|Total Credit|Estimate Amount|
      |Single          |0         |LiveIn         |100000  |10000       |500            |0                   |100                 |0                |10000       |491,000        |
