/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.andromedaModule;

import com.andromedalib.math.Conversions;
import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.SuperCANCoder;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.team6647.andromedaSwerve.utils.AndromedaModuleConstants;
import com.team6647.andromedaSwerve.utils.AndromedaState;
import com.team6647.util.Constants.SwerveConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class AndromedaModule {
    private int moduleNumber;

    private SuperTalonFX driveMotor;
    private SuperTalonFX steeringMotor;
    private SuperCANCoder steeringEncoder;

    private Rotation2d angleOffset;
    private Rotation2d lastAngle;

    public AndromedaModule(int moduleNumber, AndromedaModuleConstants constants) {
        this.moduleNumber = moduleNumber;

        this.driveMotor = new SuperTalonFX(constants.driveMotorID, GlobalIdleMode.brake, false,
                SwerveConstants.andromedaProfile.driveMotorConfiguration);
        this.steeringMotor = new SuperTalonFX(constants.steeringMotorID, GlobalIdleMode.Coast, false,
                SwerveConstants.andromedaProfile.turningMotorConfiguration);
        this.steeringEncoder = new SuperCANCoder(constants.absCanCoderID,
                SwerveConstants.andromedaProfile.cancoderConfig);

        this.angleOffset = constants.angleOffset;

        resetAbsolutePosition();

        lastAngle = getState().angle;
    }

    public void setDesiredState(SwerveModuleState desiredState) {
        desiredState = AndromedaState.optimize(desiredState, getState().angle);
        setAngle(desiredState);
        setSpeed(desiredState);
    }

    private void setAngle(SwerveModuleState desiredState) {
        Rotation2d angle = (Math.abs(desiredState.speedMetersPerSecond) <= (SwerveConstants.maxSpeed * 0.01))
                ? lastAngle
                : desiredState.angle;

        steeringMotor.set(ControlMode.Position,
                Conversions.degreesToFalcon(angle.getDegrees(), SwerveConstants.andromedaProfile.steeringGearRatio));
        lastAngle = angle;
    }

    private void setSpeed(SwerveModuleState desiredState) {
        double percentOutput = desiredState.speedMetersPerSecond / SwerveConstants.maxSpeed;
        driveMotor.set(ControlMode.PercentOutput, percentOutput);
    }

    public SwerveModuleState getState() {
        return new SwerveModuleState(driveMotor.getVelocity(SwerveConstants.wheelCircumference,
                SwerveConstants.andromedaProfile.driveGearRatio), getAngle());
    }

    private void resetAbsolutePosition() {
        steeringMotor.setSelectedSensorPosition(0);
        double encoderPosition = Conversions.degreesToFalcon(steeringEncoder.getDegrees() - angleOffset.getDegrees(),
                SwerveConstants.andromedaProfile.steeringGearRatio);
        steeringMotor.setSelectedSensorPosition(encoderPosition);
    }

    private Rotation2d getAngle() {
        return Rotation2d.fromDegrees(steeringMotor.getAngle(SwerveConstants.andromedaProfile.driveGearRatio));
    }

    public SwerveModuleState getPosition() {
        return new SwerveModuleState(driveMotor.getPosition(SwerveConstants.wheelCircumference,
                SwerveConstants.andromedaProfile.driveGearRatio), getAngle());
    }
}
