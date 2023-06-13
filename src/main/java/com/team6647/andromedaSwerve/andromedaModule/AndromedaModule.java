/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.andromedaModule;

import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.ctre.phoenix.sensors.CANCoder;
import com.team6647.andromedaSwerve.utils.AndromedaModuleConstants;

public class AndromedaModule {
    private int moduleNumber;

    private SuperTalonFX driveMotor;
    private SuperTalonFX steeringMotor;
    private CANCoder steeringEncoder;
    private AndromedaModuleConstants constants;

    public AndromedaModule(int moduleNumber, AndromedaModuleConstants constants) {
        this.moduleNumber = moduleNumber;
        
        this.driveMotor = new SuperTalonFX(constants.driveMotorID, GlobalIdleMode.brake, false);
        this.steeringMotor = new SuperTalonFX(constants.steeringMotorID, GlobalIdleMode.Coast, false);
        this.steeringEncoder = new CANCoder(constants.absCanCoderID);
    }
}
