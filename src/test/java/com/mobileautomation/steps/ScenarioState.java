package com.mobileautomation.steps;

public class ScenarioState {

    private String capturedProductName;

    public String getCapturedProductName() {
        return capturedProductName;
    }

    public void setCapturedProductName(String capturedProductName) {
        this.capturedProductName = capturedProductName;
    }

    public void clear() {
        capturedProductName = null;
    }
}

