package com.team6647.andromedaSwerve.andromedaModule;

import org.littletonrobotics.junction.AutoLog;

public interface ModuleIO {
    @AutoLog
    public static class ModuleIOInputs {
        public double drivePositionRad = 0.0;
        public double driveVelocityRadPerSec = 0.0;
        public double driveAppliedVolts = 0.0;

        public double turnAbsolutePositionRad = 0.0;
        public double turnPositionRad = 0.0;
        public double turnVelocityRadPerSec = 0.0;
        public double turnAppliedVolts = 0.0;
    }

    /** Updates the set of loggable inputs. */
    public default void updateInputs(ModuleIOInputs inputs) {
    }

    public default void setDriveVoltage(double volts) {
    }

    public default void setTurnVoltage(double volts) {
    }

    public default void setDriveBrakeMode(boolean enable) {
    }

    public default void setTurnBrakeMode(boolean enable) {
    }
}
