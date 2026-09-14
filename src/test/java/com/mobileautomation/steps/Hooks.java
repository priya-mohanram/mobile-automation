package com.mobileautomation.steps;

import com.mobileautomation.driver.DriverFactory;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;

public class Hooks {

    private final ScenarioState scenarioState;

    public Hooks(ScenarioState scenarioState) {
        this.scenarioState = scenarioState;
    }

    @Before
    public void setUp() {
        DriverFactory.initializeDriver();
        scenarioState.clear();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = DriverFactory.getDriver().getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "failure-screenshot");
            } catch (RuntimeException ignored) {
                // Skip screenshot attachment when the driver is unavailable or capture fails during cleanup.
            }
        }

        DriverFactory.quitDriver();
        scenarioState.clear();
    }
}
