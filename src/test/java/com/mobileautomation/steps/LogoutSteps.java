package com.mobileautomation.steps;

import com.mobileautomation.pages.LoginPage;
import com.mobileautomation.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class LogoutSteps {

    @When("the user logs out")
    public void theUserLogsOut() {
        ProductsPage productsPage = new ProductsPage();
        productsPage.openMenu();
        productsPage.tapLogout();
    }

    @Then("the user should be returned to the login screen")
    public void theUserShouldBeReturnedToTheLoginScreen() {
        Assertions.assertTrue(new LoginPage().isVisibleLoginScreen(), "Login screen is not visible after logout.");
    }
}

