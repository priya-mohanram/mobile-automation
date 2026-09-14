package com.mobileautomation.driver;

import com.mobileautomation.config.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public final class DriverFactory {

    private static final ThreadLocal<AppiumDriver> DRIVER = new ThreadLocal<>();
    private static final String DEFAULT_APPIUM_SERVER_URL = "http://127.0.0.1:4723";

    private DriverFactory() {
        // Utility class
    }

    public static void initializeDriver() {
        if (DRIVER.get() != null) {
            throw new IllegalStateException("Driver is already initialized for this thread.");
        }

        ConfigManager configManager = new ConfigManager();
        URL appiumServerUrl = resolveAppiumServerUrl(configManager);
        DRIVER.set(createDriver(configManager, appiumServerUrl));
    }

    public static AppiumDriver getDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver == null) {
            throw new IllegalStateException("Driver is not initialized for this thread. Call initializeDriver() first.");
        }
        return driver;
    }

    public static void quitDriver() {
        AppiumDriver driver = DRIVER.get();
        if (driver != null) {
            driver.quit();
            DRIVER.remove();
        }
    }

    private static AppiumDriver createDriver(ConfigManager configManager, URL appiumServerUrl) {
        String platform = configManager.getPlatform().trim().toLowerCase();

        return switch (platform) {
            case "android" -> createAndroidDriver(configManager, appiumServerUrl);
            case "ios" -> createIOSDriver(configManager, appiumServerUrl);
            default -> throw new IllegalStateException(
                    "Unsupported platform: " + configManager.getPlatform() + ". Supported values are 'android' and 'ios'."
            );
        };
    }

    private static AndroidDriver createAndroidDriver(ConfigManager configManager, URL appiumServerUrl) {
        validateAutomationName(configManager.getAutomationName(), "UiAutomator2", "android");

        UiAutomator2Options options = new UiAutomator2Options()
                .setPlatformName(configManager.getPlatform())
                .setDeviceName(configManager.getDeviceName())
                .setUdid(configManager.getUdid())
                .setApp(configManager.getApp())
                .setAutomationName(configManager.getAutomationName())
                .setAppWaitActivity(configManager.getAppWaitActivity());

        return new AndroidDriver(appiumServerUrl, options);
    }

    private static IOSDriver createIOSDriver(ConfigManager configManager, URL appiumServerUrl) {
        validateAutomationName(configManager.getAutomationName(), "XCUITest", "ios");

        XCUITestOptions options = new XCUITestOptions()
                .setPlatformName(configManager.getPlatform())
                .setDeviceName(configManager.getDeviceName())
                .setUdid(configManager.getUdid())
                .setApp(configManager.getApp())
                .setAutomationName(configManager.getAutomationName());

        return new IOSDriver(appiumServerUrl, options);
    }

    private static URL resolveAppiumServerUrl(ConfigManager configManager) {
        String appiumServerUrl = System.getenv("APPIUM_SERVER_URL");
        if (appiumServerUrl == null || appiumServerUrl.trim().isEmpty()) {
            appiumServerUrl = DEFAULT_APPIUM_SERVER_URL;
            try {
                appiumServerUrl = configManager.getProperty("appiumServerUrl");
            } catch (IllegalStateException ignored) {
                // Keep default when property is intentionally not provided.
            }
        }

        try {
            return URI.create(appiumServerUrl.trim()).toURL();
        } catch (IllegalArgumentException | MalformedURLException exception) {
            throw new IllegalStateException("Invalid Appium server URL: " + appiumServerUrl, exception);
        }
    }

    private static void validateAutomationName(String actual, String expected, String platform) {
        if (!expected.equalsIgnoreCase(actual)) {
            throw new IllegalStateException(
                    "Invalid automationName for " + platform + ": '" + actual + "'. Expected '" + expected + "'."
            );
        }
    }
}
