/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.utils;

import edu.wpi.first.math.util.Units;

/* Represents an entire profile configuration for a module */
public final class AndromedaProfileConfig {

    public final double steeringGearRatio;
    public final double driveGearRatio;

    public final double wheelDiameter;
    public final double wheelCircumference;

    public final double turningKp;
    public final double turningKi;
    public final double turningKd;
    public final double turningKf;
    public final boolean driveMotorInvert;
    public final boolean steeringMotorInvert;
    public final boolean canCoderInvert;

    public AndromedaProfileConfig(double steeringGearRatio, double driveGearRatio, double wheelDiameter,
            double turningKp, double turningKi, double turningKd, double turningKf, boolean driveMotorInvert,
            boolean steeringMotorInvert, boolean cancoderInvert) {
        this.steeringGearRatio = steeringGearRatio;
        this.driveGearRatio = driveGearRatio;
        this.wheelDiameter = wheelDiameter;
        this.wheelCircumference = wheelDiameter * Math.PI;
        this.turningKp = turningKp;
        this.turningKi = turningKi;
        this.turningKd = turningKd;
        this.turningKf = turningKf;
        this.driveMotorInvert = driveMotorInvert;
        this.steeringMotorInvert = steeringMotorInvert;
        this.canCoderInvert = cancoderInvert;
    }

    public static AndromedaProfileConfig andromedaSwerveConfig() {
        double wheelDiameter = Units.inchesToMeters(4.0);

        double steeringGearRatio = ((150.0 / 7.0) / 1.0);
        double driveGearRatio = (6.75 / 1.0);

        double turningKp = 0.0;
        double turningKi = 0.0;
        double turningKd = 0.0;
        double turningKf = 0.0;

        boolean driveMotorInvert = false;
        boolean angleMotorInvert = true;
        boolean canCoderInvert = false;

        return new AndromedaProfileConfig(steeringGearRatio, driveGearRatio, wheelDiameter, turningKp, turningKi,
                turningKd, turningKf, driveMotorInvert, angleMotorInvert, canCoderInvert);
    }
}
