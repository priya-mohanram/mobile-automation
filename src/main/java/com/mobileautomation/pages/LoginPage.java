package com.mobileautomation.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class LoginPage extends BasePage {

    private static final By USERNAME_INPUT = AppiumBy.accessibilityId("test-Username");
    private static final By PASSWORD_INPUT = AppiumBy.accessibilityId("test-Password");
    private static final By LOGIN_BUTTON = AppiumBy.accessibilityId("test-LOGIN");
    private static final By ERROR_MESSAGE = AppiumBy.accessibilityId("test-Error message");

    public void enterUsername(String username) {
        enterText(USERNAME_INPUT, username);
    }

    public void enterPassword(String password) {
        enterText(PASSWORD_INPUT, password);
    }

    public void tapLogin() {
        tap(LOGIN_BUTTON);
    }

    public boolean isVisibleLoginScreen() {
        return isVisible(USERNAME_INPUT);
    }

    public boolean isErrorMessageVisible() {
        return isVisible(ERROR_MESSAGE);
    }
}
