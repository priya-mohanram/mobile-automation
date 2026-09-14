@login
Feature: Login

  @smoke @regression
  Scenario: Successful login
    Given the user is on the login screen
    When the user logs in with username "standard_user" and password "secret_sauce"
    Then the login should be successful

  @regression
  Scenario: Unsuccessful login with invalid credentials
    Given the user is on the login screen
    When the user logs in with username "invalid_user" and password "invalid_password"
    Then an authentication error should be displayed


