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

    public final double driveKp;
    public final double driveKi;
    public final double driveKd;
    public final double driveKf;

    public final boolean driveMotorInvert;
    public final boolean steeringMotorInvert;
    public final boolean canCoderInvert;

    public AndromedaProfileConfig(double steeringGearRatio, double driveGearRatio, double wheelDiameter,
            double turningKp, double turningKi, double turningKd, double turningKf, double driveKp, double driveKi,
            double driveKd, double driveKf,
            boolean driveMotorInvert,
            boolean steeringMotorInvert, boolean cancoderInvert) {
        this.steeringGearRatio = steeringGearRatio;
        this.driveGearRatio = driveGearRatio;
        this.wheelDiameter = wheelDiameter;
        this.wheelCircumference = wheelDiameter * Math.PI;
        this.turningKp = turningKp;
        this.turningKi = turningKi;
        this.turningKd = turningKd;
        this.turningKf = turningKf;
        this.driveKp = driveKp;
        this.driveKi = driveKi;
        this.driveKd = driveKd;
        this.driveKf = driveKf;
        this.driveMotorInvert = driveMotorInvert;
        this.steeringMotorInvert = steeringMotorInvert;
        this.canCoderInvert = cancoderInvert;
    }

    /* Base Andromeda Swerve configuration profile */
    public static AndromedaProfileConfig andromedaSwerveConfig() {
        double wheelDiameter = Units.inchesToMeters(4.0);

        double steeringGearRatio = ((150.0 / 7.0) / 1.0);
        double driveGearRatio = (6.75 / 1.0);

        double turningKp = 0.0; // <- TODO Modify
        double turningKi = 0.0;
        double turningKd = 0.0;
        double turningKf = 0.0;

        double driveKp = 0.0; // <- TODO Modify
        double driveKi = 0.0;
        double driveKd = 0.0;
        double driveKf = 0.0;

        boolean driveMotorInvert = false;
        boolean angleMotorInvert = true;
        boolean canCoderInvert = false;

        return new AndromedaProfileConfig(steeringGearRatio, driveGearRatio, wheelDiameter, turningKp, turningKi,
                turningKd, turningKf, driveKp, driveKi, driveKd, driveKf, driveMotorInvert, angleMotorInvert,
                canCoderInvert);
    }
}
