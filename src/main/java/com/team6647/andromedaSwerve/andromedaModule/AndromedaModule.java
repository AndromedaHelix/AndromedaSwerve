/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.andromedaModule;

import com.andromedalib.motorControllers.SuperTalonFX;
import com.andromedalib.motorControllers.IdleManager.GlobalIdleMode;
import com.ctre.phoenix.sensors.CANCoder;

import edu.wpi.first.math.controller.PIDController;

public class AndromedaModule {
    private SuperTalonFX driveMotor;
    private SuperTalonFX steeringMotor;
    private CANCoder steeringEncoder;

    private PIDController turningPID;

    public AndromedaModule(int driveID, int steeringID, int absEncoderID){
        this.driveMotor = new SuperTalonFX(driveID, GlobalIdleMode.brake, false);
        this.steeringMotor = new SuperTalonFX(steeringID, GlobalIdleMode.brake, false);
        this.steeringEncoder = new CANCoder(absEncoderID);

        
    }
}
