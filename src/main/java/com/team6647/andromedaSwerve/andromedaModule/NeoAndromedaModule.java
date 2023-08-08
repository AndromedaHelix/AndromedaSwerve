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
import edu.wpi.first.math.kinematics.SwerveModuleState;

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

       /*  double val = steeringController.calculate(getState().angle.getDegrees(), angle.getDegrees());

        steeringMotor.set(val);  */// PIDCONTROLLER

        turningController.setReference(angle.getDegrees(), ControlType.kPosition); // SPARKMAX PID

        lastAngle = angle;
    }

    private void setSpeed(SwerveModuleState desiredState, boolean isOpenLoop) {
        if (isOpenLoop) {
            double percentOutput = desiredState.speedMetersPerSecond / SwerveConstants.maxSpeed;

            driveMotor.set(percentOutput);
        } else {
            // TODO set Closed loop
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

    /* Telemetry */

    public double[] getTemp() {
        return new double[] { steeringMotor.getMotorTemperature(), driveMotor.getMotorTemperature() };
    }
}
