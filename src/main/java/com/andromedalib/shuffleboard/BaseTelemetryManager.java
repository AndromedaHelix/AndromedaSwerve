/**
 * Written by Juan Pablo Gutiérrez
 */
package com.andromedalib.shuffleboard;

/**
 * Telemetry manager class. Change functions and parameters to suit your needs.
 * It is automatically updated by the {@link SuperRobot} class
 */
public class BaseTelemetryManager {

    private static BaseTelemetryManager instance;

    public static BaseTelemetryManager getInstance() {
        if (instance == null) {
            instance = new BaseTelemetryManager();
        }
        return instance;
    }

    public void initTelemetry() {
    }

    public void updateTelemetry() {
    }
}
