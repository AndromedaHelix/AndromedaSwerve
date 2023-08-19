/**
 * Written by Juan Pablo Gutiérrez
 */

package com.team6647.andromedaSwerve.andromedaModule;

import com.andromedalib.motorControllers.SuperSparkMax;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.SuperCANCoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.team6647.andromedaSwerve.utils.AndromedaModuleConstants;
import com.team6647.andromedaSwerve.utils.SwerveConstants;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.DriverStation;

public class NeoAndromedaModule {
    private int moduleNumber;

    private SuperSparkMax driveMotor;
    private SuperSparkMax steeringMotor;
    private SuperCANCoder steeringEncoder;

    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    private SparkMaxPIDController driveController;
    private SparkMaxPIDController turningController;

    private PIDController steeringController;

    public NeoAndromedaModule(int moduleNumber, AndromedaModuleConstants constants) {
        this.moduleNumber = moduleNumber;

        if (SwerveConstants.andromedaProfile.motorConfig.equals("Falcon config")) {
            DriverStation.reportError("Neo AndromedaModule " + moduleNumber
                    + " is using Falcon config. Please change your profile config selection to avoid unwanted behaviours",
                    true);
        }

        this.driveMotor = new SuperSparkMax(constants.driveMotorID, GlobalIdleMode.brake,
                SwerveConstants.andromedaProfile.driveMotorInvert,
                SwerveConstants.andromedaProfile.driveContinuousCurrentLimit);
        this.steeringMotor = new SuperSparkMax(constants.steeringMotorID, GlobalIdleMode.Coast,
                SwerveConstants.andromedaProfile.steeringMotorInvert,
                SwerveConstants.andromedaProfile.angleContinuousCurrentLimit);
        this.steeringEncoder = new SuperCANCoder(constants.absCanCoderID,
                SwerveConstants.andromedaProfile.cancoderConfig);
        this.angleOffset = constants.angleOffset;

        this.driveController = driveMotor.getPIDController();
        this.turningController = steeringMotor.getPIDController();

        steeringController = new PIDController(SwerveConstants.andromedaProfile.turningKp,
                SwerveConstants.andromedaProfile.turningKi, SwerveConstants.andromedaProfile.turningKf);

        steeringController.enableContinuousInput(-180, 180);

        turningController.setP(SwerveConstants.andromedaProfile.turningKp);
        turningController.setI(SwerveConstants.andromedaProfile.turningKi);
        turningController.setD(SwerveConstants.andromedaProfile.turningKi);

        driveController.setP(SwerveConstants.andromedaProfile.driveKp);
        driveController.setI(SwerveConstants.andromedaProfile.driveKi);
        driveController.setD(SwerveConstants.andromedaProfile.driveKd);

        resetAbsolutePosition();

        driveMotor.setPositionConversionFactor(SwerveConstants.andromedaProfile.driveGearRatio);
        driveMotor.setVelocityConversionFactor(SwerveConstants.andromedaProfile.driveGearRatio / 60);
        steeringMotor.setPositionConversionFactor(SwerveConstants.andromedaProfile.steeringGearRatio);
        steeringMotor.setVelocityConversionFactor(SwerveConstants.andromedaProfile.steeringGearRatio / 60);

        lastAngle = getAngle();

    }

    public int getModuleNumber() {
        return moduleNumber;
    }

    public void setDesiredState(SwerveModuleState desiredState, boolean isOpenLoop) {
        desiredState = SwerveModuleState.optimize(desiredState, getState().angle);

        setAngle(desiredState);
        setSpeed(desiredState, isOpenLoop);
    }

    private void setAngle(SwerveModuleState desiredState) {
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (SwerveConstants.maxSpeed * 0.01))
                ? lastAngle
                : desiredState.angle;

        setSparkAngle(angle.getDegrees());

        lastAngle = angle;
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / SwerveConstants.maxSpeed;

            driveMotor.set(percentOutput);
        } else {
            driveController.setReference(desiredState.speedMetersPerSecond, ControlType.kVelocity);
        }
    }

    public void resetAbsolutePosition() {
        steeringMotor.setPosition(0);
        double encoderPosition = steeringEncoder.getAbsolutePosition() - angleOffset.getDegrees();
        steeringMotor.setPosition(encoderPosition);
    }

    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(
                steeringMotor.getPosition());
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(driveMotor.getVelocity(), getAngle());
    }

    public SwerveModulePosition getPosition() {
        return new SwerveModulePosition(
                driveMotor.getPosition(),
                getAngle());
    }

    /**
     * 
     * @param targetAngleInDegrees target angle from WPI's swerve kinematics
     *                             optimize method
     */
    public void setSparkAngle(double targetAngleInDegrees) {

        double currentSparkAngle = getAngle().getDegrees();

        double sparkRelativeTargetAngle = reboundValue(targetAngleInDegrees, currentSparkAngle);

        turningController.setReference(sparkRelativeTargetAngle,
                ControlType.kPosition);
    }

    private double reboundValue(double value, double anchor) {
        double lowerBound = anchor - 180;
        double upperBound = anchor + 180;

        if (value < lowerBound) {
            value = upperBound
                    + ((value - lowerBound) % (upperBound - lowerBound));
        } else if (value > upperBound) {
            value = lowerBound
                    + ((value - upperBound) % (upperBound - lowerBound));
        }

        return value;
    }

    /* Telemetry */

    public double[] getTemp() {
        return new double[] { steeringMotor.getMotorTemperature(), driveMotor.getMotorTemperature() };
    }
}
