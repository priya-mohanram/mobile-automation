package com.mobileautomation.pages;

import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

public class ProductsPage extends BasePage {

    private static final By PRODUCTS_TITLE = AppiumBy.accessibilityId("test-PRODUCTS");
    private static final By MENU_BUTTON = AppiumBy.accessibilityId("test-Menu");
    private static final By LOGOUT_BUTTON = AppiumBy.accessibilityId("test-LOGOUT");
    private static final By PRODUCT_NAME = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"Sauce Labs Backpack\")"
    );
    private static final By ADD_TO_CART_BUTTON = AppiumBy.androidUIAutomator(
            "new UiSelector().text(\"ADD TO CART\").instance(0)"
    );
    // Provisional iOS locators: require validation in XCUITest environment with real accessibility IDs.
    private static final By PRODUCT_NAME_IOS = AppiumBy.xpath(
            "//*[@text='Sauce Labs Backpack' or @label='Sauce Labs Backpack' or @name='Sauce Labs Backpack']"
    );
    private static final By ADD_TO_CART_BUTTON_IOS = AppiumBy.xpath(
            "//*[@text='ADD TO CART' or @label='ADD TO CART' or @name='ADD TO CART']"
    );
    private static final By CART_BUTTON = AppiumBy.accessibilityId("test-Cart");

    public boolean isProductsScreenVisible() {
        return isVisible(PRODUCTS_TITLE);
    }

    public void openMenu() {
        tap(MENU_BUTTON);
    }

    public void tapLogout() {
        tap(LOGOUT_BUTTON);
    }
    public String getProductName() {
        return getText(resolveProductNameLocator());
    }

    public void addToCart() {
        tap(resolveAddToCartLocator());
    }

    public void openCart() {
        tap(CART_BUTTON);
    }

    private By resolveProductNameLocator() {
        if (isAndroid()) {
            return PRODUCT_NAME;
        }
        return PRODUCT_NAME_IOS;
    }

    private By resolveAddToCartLocator() {
        if (isAndroid()) {
            return ADD_TO_CART_BUTTON;
        }
        return ADD_TO_CART_BUTTON_IOS;
    }
}
