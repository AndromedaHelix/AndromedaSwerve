package com.team6647.andromedaSwerve.utils.shufflleboard.tabs;

import com.andromedalib.shuffleboard.ShuffleboardTabBase;

import com.team6647.andromedaSwerve.systems.AndromedaSwerve;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

public class AndromedaSwerveInfo extends ShuffleboardTabBase {
    AndromedaSwerve andromedaSwerve = AndromedaSwerve.getInstance();

    GenericEntry heading;
    GenericEntry[] temps = new GenericEntry[8];
    GenericEntry[] angles = new GenericEntry[4];
    GenericEntry[] position = new GenericEntry[4];

    int module = 1;
    int i = 0;
    boolean tempsEnabled;

    public AndromedaSwerveInfo(ShuffleboardTab tab, boolean tempsEnabled) {
        this.tempsEnabled = tempsEnabled;
        if (tempsEnabled) {
            andromedaSwerve.getModulesTemp().forEach((temp) -> {
                temps[i] = tab.add("Module " + module + " " + i, temp).getEntry();
                if ((i + 1) % 2 == 0) {
                    module = (module % 4) + 1;
                }
                i++;
            });
        }

        andromedaSwerve.getModules().forEach((module) -> {
            angles[module.getModuleNumber()] = tab
                    .add("Module Angle " + module.getModuleNumber(), module.getState().angle.getDegrees())
                    .getEntry();
        });

        andromedaSwerve.getModules().forEach((module) -> {
            position[module.getModuleNumber()] = tab
                    .add("Module Position" + module.getModuleNumber(), module.getPosition().distanceMeters)
                    .getEntry();
        });

        heading = tab.add("Heading", andromedaSwerve.getAngle().getDegrees()).withWidget(BuiltInWidgets.kGyro)
                .getEntry();
    }

    @Override
    public void updateTelemetry() {
        if (tempsEnabled) {
            i = 0;
            andromedaSwerve.getModulesTemp().forEach((temp) -> {
                temps[i].setDouble(temp);
                i++;
            });
        }

        andromedaSwerve.getModules().forEach((module) -> {
            angles[module.getModuleNumber()].setDouble(module.getState().angle.getDegrees());
        });

        andromedaSwerve.getModules().forEach((module) -> {
            position[module.getModuleNumber()].setDouble(module.getPosition().distanceMeters);
        });

        heading.setDouble(andromedaSwerve.getAngle().getDegrees());
    }

}
