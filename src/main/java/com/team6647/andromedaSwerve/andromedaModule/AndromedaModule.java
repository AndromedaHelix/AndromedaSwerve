/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.andromedaModule;

import com.andromedalib.math.Conversions;
import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.andromedalib.sensors.SuperCANCoder;
import com.team6647.andromedaSwerve.utils.AndromedaModuleConstants;
import com.team6647.util.Constants.SwerveConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;

public class AndromedaModule {
    private int moduleNumber;

    private SuperTalonFX driveMotor;
    private SuperTalonFX steeringMotor;
    private SuperCANCoder steeringEncoder;
    private AndromedaModuleConstants constants;

    private Rotation2d angleOffset;

    public AndromedaModule(int moduleNumber, AndromedaModuleConstants constants) {
        this.moduleNumber = moduleNumber;
        
        this.driveMotor = new SuperTalonFX(constants.driveMotorID, GlobalIdleMode.brake, false, SwerveConstants.andromedaProfile.driveMotorConfiguration);
        this.steeringMotor = new SuperTalonFX(constants.steeringMotorID, GlobalIdleMode.Coast, false, SwerveConstants.andromedaProfile.turningMotorConfiguration);
        this.steeringEncoder = new SuperCANCoder(constants.absCanCoderID, SwerveConstants.andromedaProfile.cancoderConfig);

        this.angleOffset = constants.angleOffset;

        resetAbsolutePosition();
    }

    public int getModuleNumber(){
        return moduleNumber;
    }

    public void resetAbsolutePosition(){
        steeringMotor.setSelectedSensorPosition(0);
        double encoderPosition = Conversions.degreesToFalcon(steeringEncoder.getDegrees() - angleOffset.getDegrees(), SwerveConstants.andromedaProfile.steeringGearRatio);
        steeringMotor.setSelectedSensorPosition(encoderPosition);
    }

  
}
