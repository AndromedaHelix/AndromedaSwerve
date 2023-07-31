package com.team6647.andromedaSwerve.andromedaModule;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.Pigeon2;

import edu.wpi.first.math.util.Units;

/* Navx GyroIO Implementation */
public class GyroIOPigeon implements GyroIO {

    private final Pigeon2 gyro;
    private final double[] xyzDps = new double[3];

    public GyroIOPigeon() {
        gyro = new Pigeon2(0);
    }

    public void updatedInputs(GyroIOInputs inputs) {
        gyro.getRawGyro(xyzDps);
        inputs.isConnected = gyro.getLastError().equals(ErrorCode.OK);
        inputs.positionRad = Units.degreesToRadians(gyro.getYaw());
        inputs.velocityRadPerSec = Units.degreesToRadians(xyzDps[2]);

    }

}
