package com.mobileautomation.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class ConfigManager {

	private static final String DEFAULT_CONFIG_FILE = "config.properties";
	private static final String DEFAULT_CONFIG_FILE_IOS = "config-ios.properties";
    private static final String CONFIG_FILE_SYSTEM_PROPERTY = "config.file";

	private final Properties properties = new Properties();

	public ConfigManager() {
		String explicitPath = System.getProperty(CONFIG_FILE_SYSTEM_PROPERTY);
		if (explicitPath != null && !explicitPath.trim().isEmpty()) {
			load(explicitPath.trim());
			return;
		}

		String platform = System.getProperty("platform","android");
		if("ios".equalsIgnoreCase(platform)) {
			loadFromClasspath(DEFAULT_CONFIG_FILE_IOS);
		}
		else{
			loadFromClasspath(DEFAULT_CONFIG_FILE);
		}

	}

	private void load(String configFilePath) {
		Path path = Paths.get(configFilePath);
		if (!Files.exists(path)) {
			throw new IllegalStateException("Configuration file not found: " + path.toAbsolutePath());
		}

		try (InputStream inputStream = Files.newInputStream(path)) {
			properties.load(inputStream);
		} catch (IOException exception) {
			throw new IllegalStateException(
					"Failed to load configuration file: " + path.toAbsolutePath(),
					exception
			);
		}
	}

	private void loadFromClasspath(String resourceName) {
		String normalizedResource = Objects.requireNonNull(resourceName, "Resource name cannot be null.").trim();
		if (normalizedResource.isEmpty()) {
			throw new IllegalArgumentException("Resource name cannot be empty.");
		}

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		if (classLoader == null) {
			classLoader = ConfigManager.class.getClassLoader();
		}

		InputStream resourceStream = classLoader.getResourceAsStream(normalizedResource);
		if (resourceStream == null) {
			throw new IllegalStateException("Configuration resource not found in classpath: " + normalizedResource);
		}

		try (InputStream inputStream = resourceStream) {
			properties.load(inputStream);
		} catch (IOException exception) {
			throw new IllegalStateException(
					"Failed to load configuration resource from classpath: " + normalizedResource,
					exception
			);
		}
	}

	public String getProperty(String key) {
		String normalizedKey = Objects.requireNonNull(key, "Property key cannot be null.").trim();
		if (normalizedKey.isEmpty()) {
			throw new IllegalArgumentException("Property key cannot be empty.");
		}

		String value = properties.getProperty(normalizedKey);
		if (value == null || value.trim().isEmpty()) {
			throw new IllegalStateException("Missing required configuration property: " + normalizedKey);
		}
		return value.trim();
	}

	public String getPlatform() {
		return getProperty("platform");
	}

	public String getDeviceName() {
		return getProperty("deviceName");
	}

	public String getUdid() {
		return getProperty("udid");
	}

	public String getApp() {
		String appPathFromEnv = System.getenv("APP_PATH");
		if (appPathFromEnv != null && !appPathFromEnv.trim().isEmpty()) {
			return appPathFromEnv.trim();
		}
		try {
			return getProperty("app");
		} catch (IllegalStateException exception) {
			throw new IllegalStateException(
					"App path is not configured. Set environment variable APP_PATH or provide non-empty 'app' in configuration.",
					exception
			);
		}
	}

	public String getAutomationName() {
		return getProperty("automationName");
	}

	public String getAppWaitActivity() {
		return getProperty("appWaitActivity");
	}
}
