package com.mobileautomation.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class CartPage extends BasePage {

    private static final By ITEM_TITLE = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Sauce Labs Backpack\")"
    );
    // Provisional iOS locator: require validation in XCUITest environment with real accessibility IDs.
    private static final By ITEM_TITLE_IOS = AppiumBy.xpath(
            "//*[@text='Sauce Labs Backpack' or @label='Sauce Labs Backpack' or @name='Sauce Labs Backpack']"
    );

    public String getCartItemName() {
        return getText(resolveItemTitleLocator());
    }

    private By resolveItemTitleLocator() {
        if (isAndroid()) {
            return ITEM_TITLE;
        }
        return ITEM_TITLE_IOS;
    }
}
