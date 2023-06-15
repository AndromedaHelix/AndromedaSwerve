/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.systems;

import com.andromedalib.sensors.SuperNavx;
import com.team6647.andromedaSwerve.andromedaModule.AndromedaModule;
import com.team6647.andromedaSwerve.utils.AndromedaMap;
import com.team6647.util.Constants.SwerveConstants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AndromedaSwerve extends SubsystemBase {
  private static AndromedaSwerve instance;
  private static AndromedaModule[] modules;

  private static SuperNavx navx = SuperNavx.getInstance();

  private AndromedaSwerve() {

    modules = new AndromedaModule[] {
        new AndromedaModule(0, AndromedaMap.mod1Const),
        new AndromedaModule(1, AndromedaMap.mod2Const),
        new AndromedaModule(2, AndromedaMap.mod3Const),
        new AndromedaModule(3, AndromedaMap.mod4Const),
    };

    Timer.delay(1.0);
    resetAbsoluteModules();
  }

  public static AndromedaSwerve getInstance() {
    if (instance == null) {
      instance = new AndromedaSwerve();
    }
    return instance;
  }

  @Override
  public void periodic() {
    for (AndromedaModule andromedaModule : modules) {
      SmartDashboard.putNumber("Mod " + andromedaModule.getModuleNumber() + " Angle",
          andromedaModule.getState().angle.getDegrees());
      SmartDashboard.putNumber("Mod " + andromedaModule.getModuleNumber() + " Velocity",
          andromedaModule.getState().speedMetersPerSecond);
    }

    SmartDashboard.putNumber("Angle", navx.getClampedYaw().getDegrees());
  }

  public void resetAbsoluteModules() {
    for (AndromedaModule andromedaModule : modules) {
      andromedaModule.resetAbsolutePosition();
    }
  }

  public void drive(Translation2d translation, double rotation, boolean fieldRelative, boolean isOpenLoop) {
    SwerveModuleState[] swerveModuleStates = SwerveConstants.swerveKinematics.toSwerveModuleStates(
        fieldRelative
            ? ChassisSpeeds.fromFieldRelativeSpeeds(translation.getX(), translation.getY(), rotation,
                navx.getClampedYaw())
            : new ChassisSpeeds(translation.getX(), translation.getY(), rotation));

    setModuleStates(swerveModuleStates, isOpenLoop);
  }

  private void setModuleStates(SwerveModuleState[] desiredStates, boolean isOpenLoop) {
    SwerveDriveKinematics.desaturateWheelSpeeds(desiredStates, SwerveConstants.maxSpeed);

    for (AndromedaModule andromedaModule : modules) {
      andromedaModule.setDesiredState(desiredStates[andromedaModule.getModuleNumber()], isOpenLoop);
    }
  }
}
