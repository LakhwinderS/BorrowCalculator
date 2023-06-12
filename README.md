Automated Tests driven by Cucumber Feature files

This build uses the following:

   1) Selenium Web Driver
   2) Java
   3) Maven
   4) Cucumber 2.4
   5) Implements Selenium PageObjectModel
   6) Github (source code control)
Prerequisites:
java version 17 installed
maven  installed
eclipse or intellij as IDE is recommended
Running tests locally:
Run/Debug your runner test file as JUnit.
Running tests on Jenkins/Command Line:
Maven Command

mvn -Dcucumber.filter.tags="@sanity" clean test

User will get the reports under Target folder once he will run the tests. This will generate, Html and json report