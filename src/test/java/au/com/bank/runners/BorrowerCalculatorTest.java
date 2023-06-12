package au.com.bank.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/resources/features"},
        glue="au.com.bank",
        snippets= CucumberOptions.SnippetType.CAMELCASE,
        monochrome = true,
        publish =  false,
        plugin = {"pretty", "html:target/cucumber-Reports/cucumber-Pretty","html:target/cucumber-html-report",
                "json:target/cucumber-reports/CucumberTestReport.json", "rerun:target/cucumber-reports/rerun.txt" }
)
public class BorrowerCalculatorTest {

}
