/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.util;

import com.team6647.andromedaSwerve.utils.AndromedaProfileConfig;
import com.team6647.andromedaSwerve.utils.AndromedaProfileConfig.AndromedaProfiles;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class Constants {
        // TODO CLEAN Up
        public static class OperatorConstants {
                public static final int kDriverControllerPort = 0;
                public static final int kDriverControllerPort2 = 1;

                public static final CommandXboxController driverController1 = new CommandXboxController(
                                OperatorConstants.kDriverControllerPort);
                public static final CommandXboxController driverController2 = new CommandXboxController(
                                OperatorConstants.kDriverControllerPort2);
        }

        /* Represents Swerve-wise constants */
        public static final class SwerveConstants {

                public static final double deadband = 0.1;
                public static final AndromedaProfileConfig andromedaProfile = AndromedaProfileConfig
                                .getConfig(AndromedaProfiles.ANDROMEDA_CONFIG);

                /* Drivetrain Constants */
                public static final double trackWidth = Units.inchesToMeters(18.5); // TODO: This must be tuned to
                                                                                    // specific
                                                                                    // robot
                public static final double wheelBase = Units.inchesToMeters(18.5); // TODO: This must be tuned to
                                                                                   // specific
                                                                                   // robot
                public static final double wheelCircumference = andromedaProfile.wheelCircumference;
                public static final SwerveDriveKinematics swerveKinematics = new SwerveDriveKinematics(
                                new Translation2d(wheelBase / 2.0, trackWidth / 2.0),
                                new Translation2d(wheelBase / 2.0, -trackWidth / 2.0),
                                new Translation2d(-wheelBase / 2.0, -trackWidth / 2.0),
                                new Translation2d(-wheelBase / 2.0, trackWidth / 2.0));

                /*
                 * Drive Motor Characterization Values
                 * Divide SYSID values by 12 to convert from volts to percent output for CTRE
                 */
                public static final double driveKS = (0.32 / 12); // TODO TUNE
                public static final double driveKV = (1.51 / 12);
                public static final double driveKA = (0.27 / 12);

                /* Swerve Profiling Values */
                /** Meters per Second */
                public static final double maxSpeed = 4.5;
                public static final double maxAcceleration = 3.5;
                /** Radians per Second */
                public static final double maxAngularVelocity = 10.0;
                public static final double maxAngularAcceleration = 3.5;
        }
}
