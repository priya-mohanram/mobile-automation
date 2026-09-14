package com.mobileautomation.steps;

import com.mobileautomation.pages.CartPage;
import com.mobileautomation.pages.ProductsPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CartSteps {

    private final ScenarioState scenarioState;

    public CartSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @When("the user opens the cart")
    public void theUserOpensTheCart() {
        new ProductsPage().openCart();
    }

    @Then("the cart should contain the same product name")
    public void theCartShouldContainTheSameProductName() {
        String expectedProductName = scenarioState.getCapturedProductName();
        Assertions.assertNotNull(expectedProductName, "Product name was not captured before adding to cart.");

        String cartItemName = new CartPage().getCartItemName();
        Assertions.assertEquals(expectedProductName, cartItemName, "Cart item name does not match selected product name.");
    }
}

