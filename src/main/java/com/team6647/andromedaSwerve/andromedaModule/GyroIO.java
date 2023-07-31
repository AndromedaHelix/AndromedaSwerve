package com.team6647.andromedaSwerve.andromedaModule;

import org.littletonrobotics.junction.AutoLog;

public interface GyroIO {
    
    @AutoLog
    public static class GyroIOInputs{
        public boolean isConnected = false;
        public double positionRad = 0.0;
        public double velocityRadPerSec = 0.0;
    }

    public default void updateInputs(GyroIOInputs inputs) {}

}
