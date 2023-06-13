/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.util;

import com.andromedalib.shuffleboard.BaseTelemetryManager;

/**
 * Telemetry manager class. Change functions and parameters to suit your needs.
 * It is automatically updated by the {@link SuperRobot} class
 */
public class TelemetryManager extends BaseTelemetryManager {

    private static TelemetryManager instance;

    private TelemetryManager() {
    }

    public static TelemetryManager getInstance() {
        if (instance == null) {
            instance = new TelemetryManager();
        }
        return instance;
    }

    public void initTelemetry() {
    }

    public void updateTelemetry() {
    }
}
