package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class ExampleStepDefinitions {
    @Given("the user is on the banking app login page")
    public void userOnLoginPage() {
        // Selenium code to navigate to login page
    }

    @When("the user enters valid credentials")
    public void userEntersCredentials() {
        // Selenium code to enter credentials
    }

    @Then("the user should be logged in successfully")
    public void userLoggedIn() {
        // Selenium code to verify login
    }
}
