@logout
Feature: Logout

  @regression
  Scenario: Successful logout
    Given the user is logged in
    And the user logs out
    Then the user should be returned to the login screen

