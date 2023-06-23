/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.util.shuffleboard;

import com.team6647.andromedaSwerve.systems.AndromedaSwerve;

/**
 * Telemetry manager class. Change functions and parameters to suit your needs.
 * It is automatically updated by the {@link SuperRobot} class
 * Handles selectors
 */
public class TelemetryManager {

    private static TelemetryManager instance;

    private ShuffleboardManager interactions;

    /**
     * Private Constructor
     */
    private TelemetryManager() {

    }

    public static TelemetryManager getInstance() {
        if (instance == null) {
            instance = new TelemetryManager();
        }
        return instance;
    }

    public void initTelemetry() {
        interactions = ShuffleboardManager.getInstance();
    }

    public void updateTelemetry() {
        interactions.updateTelemetry();
    }
}
