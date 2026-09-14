package com.mobileautomation.steps;

import com.mobileautomation.pages.LoginPage;
import com.mobileautomation.pages.ProductsPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class LoginSteps {

    private static final String STANDARD_USERNAME = "standard_user";
    private static final String STANDARD_PASSWORD = "secret_sauce";

    @Given("the user is on the login screen")
    public void theUserIsOnTheLoginScreen() {
        Assertions.assertTrue(new LoginPage().isVisibleLoginScreen(), "Login screen is not visible.");
    }

    @Given("the user is logged in")
    public void theUserIsLoggedIn() {
        theUserIsOnTheLoginScreen();
        theUserLogsInWithUsernameAndPassword(STANDARD_USERNAME, STANDARD_PASSWORD);
    }

    @When("the user logs in with username {string} and password {string}")
    public void theUserLogsInWithUsernameAndPassword(String username, String password) {
        LoginPage loginPage = new LoginPage();
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.tapLogin();
    }

    @Then("the login should be successful")
    public void theLoginShouldBeSuccessful() {
        Assertions.assertTrue(new ProductsPage().isProductsScreenVisible(), "Products screen is not visible after login.");
    }

    @Then("an authentication error should be displayed")
    public void anAuthenticationErrorShouldBeDisplayed() {
        Assertions.assertTrue(new LoginPage().isErrorMessageVisible(), "Expected authentication error message was not displayed.");
    }
}


