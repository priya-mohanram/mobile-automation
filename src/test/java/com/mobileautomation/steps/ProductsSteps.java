package com.mobileautomation.steps;

import com.mobileautomation.pages.ProductsPage;
import io.cucumber.java.en.When;

public class ProductsSteps {

    private final ScenarioState scenarioState;

    public ProductsSteps(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @When("the user captures the product name")
    public void theUserCapturesTheProductName() {
        scenarioState.setCapturedProductName(new ProductsPage().getProductName());
    }

    @When("the user adds the product to the cart")
    public void theUserAddsTheProductToTheCart() {
        new ProductsPage().addToCart();
    }
}

