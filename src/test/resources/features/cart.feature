@cart
Feature: Cart

  @regression
  Scenario: Add to cart keeps the same product name
    Given the user is logged in
    And the user captures the product name
    And the user adds the product to the cart
    And the user opens the cart
    Then the cart should contain the same product name

