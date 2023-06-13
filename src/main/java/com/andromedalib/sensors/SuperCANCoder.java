/**
 * Written by Juan Pablo Gutiérrez
 */

package com.andromedalib.sensors;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.CANCoderConfiguration;

public class SuperCANCoder extends CANCoder {

    /**
     * SuperCANCoder constructor
     * 
     * @param deviceID      CANCoder DeviceID
     * @param configuration Configuration
     */
    public SuperCANCoder(int deviceID, CANCoderConfiguration configuration) {
        super(deviceID);

        this.configFactoryDefault();
        this.configAllSettings(configuration);
    }
}
