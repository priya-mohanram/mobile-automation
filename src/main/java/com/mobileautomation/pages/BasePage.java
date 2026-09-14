package com.mobileautomation.pages;

import com.mobileautomation.driver.DriverFactory;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;

public abstract class BasePage {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(15);

    protected final AppiumDriver driver;
    private final WebDriverWait wait;

    protected BasePage() {
        this(DEFAULT_TIMEOUT);
    }

    protected BasePage(Duration timeout) {
        if (timeout == null || timeout.isNegative() || timeout.isZero()) {
            throw new IllegalArgumentException("Timeout must be a positive duration.");
        }
        this.driver = DriverFactory.getDriver();
        this.wait = new WebDriverWait(driver, timeout);
    }

    protected WebElement waitForVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected WebElement waitForClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public void tap(By locator) {
        try {
            waitForClickable(locator).click();
        } catch (TimeoutException exception) {
            throw new IllegalStateException("Timed out waiting to tap element: " + locator, exception);
        }
    }

    public void enterText(By locator, String text) {
        try {
            WebElement element = waitForVisible(locator);
            element.clear();
            element.sendKeys(text);
        } catch (TimeoutException exception) {
            throw new IllegalStateException("Timed out waiting to enter text in element: " + locator, exception);
        }
    }

    public boolean isVisible(By locator) {
        try {
            return waitForVisible(locator).isDisplayed();
        } catch (TimeoutException exception) {
            return false;
        }
    }

    public String getText(By locator) {
        try {
            return waitForVisible(locator).getText();
        } catch (TimeoutException exception) {
            throw new IllegalStateException("Timed out waiting to get text from element: " + locator, exception);
        }
    }

    protected boolean isAndroid() {
        Object platformName = driver.getCapabilities().getCapability("platformName");
        if (platformName == null) {
            throw new IllegalStateException("Driver capability 'platformName' is missing.");
        }

        String normalized = platformName.toString().trim().toLowerCase(Locale.ROOT);
        if ("android".equals(normalized)) {
            return true;
        }
        if ("ios".equals(normalized)) {
            return false;
        }

        throw new IllegalStateException("Unsupported platform in 'platformName' capability: " + platformName);
    }
}

