/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.systems;

import java.util.ArrayList;

import org.littletonrobotics.junction.Logger;

import com.andromedalib.sensors.SuperNavx;
import com.team6647.andromedaSwerve.andromedaModule.AndromedaModule;
import com.team6647.andromedaSwerve.andromedaModule.GyroIO;
import com.team6647.andromedaSwerve.andromedaModule.GyroIOInputsAutoLogged;
import com.team6647.andromedaSwerve.andromedaModule.ModuleIO;
import com.team6647.andromedaSwerve.andromedaModule.ModuleIOInputsAutoLogged;
import com.team6647.andromedaSwerve.utils.AndromedaMap;
import com.team6647.andromedaSwerve.utils.SwerveConstants;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AndromedaSwerve extends SubsystemBase {
  private static AndromedaSwerve instance;
  private static AndromedaModule[] modules;

  private static SuperNavx navx = SuperNavx.getInstance();

  private final GyroIO gyroIO;
  private final GyroIOInputsAutoLogged gyroInputs = new GyroIOInputsAutoLogged();
  private final ModuleIO[] moduleIOs = new ModuleIO[4]; // FL, FR, BL, BR
  private final ModuleIOInputsAutoLogged[] moduleInputs = new ModuleIOInputsAutoLogged[] {
      new ModuleIOInputsAutoLogged(),
      new ModuleIOInputsAutoLogged(), new ModuleIOInputsAutoLogged(),
      new ModuleIOInputsAutoLogged() };

  private AndromedaSwerve(GyroIO gyroIO, ModuleIO flModuleIO, ModuleIO frModuleIO,
      ModuleIO blModuleIO, ModuleIO brModuleIO) {

    this.gyroIO = gyroIO;
    moduleIOs[0] = flModuleIO;
    moduleIOs[1] = frModuleIO;
    moduleIOs[2] = blModuleIO;
    moduleIOs[3] = brModuleIO;

    modules = new AndromedaModule[] {
        new AndromedaModule(0, AndromedaMap.mod1Const),
        new AndromedaModule(1, AndromedaMap.mod2Const),
        new AndromedaModule(2, AndromedaMap.mod3Const),
        new AndromedaModule(3, AndromedaMap.mod4Const),
    };

    Timer.delay(1.0);
    resetAbsoluteModules();
  }

  public static AndromedaSwerve getInstance(GyroIO gyroIO, ModuleIO flModuleIO, ModuleIO frModuleIO,
      ModuleIO blModuleIO, ModuleIO brModuleIO) {
    if (instance == null) {
      instance = new AndromedaSwerve(gyroIO, flModuleIO, frModuleIO, blModuleIO, brModuleIO);
    }
    return instance;
  }

  @Override
  public void periodic() {
    gyroIO.updateInputs(gyroInputs);
    Logger.getInstance().processInputs("Drive/Gyro", gyroInputs);
    for (int i = 0; i < 4; i++) {
      moduleIOs[i].updateInputs(moduleInputs[i]);
      Logger.getInstance().processInputs("Drive/Module" + Integer.toString(i),
          moduleInputs[i]);
    }

    // Update angle measurements
    Rotation2d[] turnPositions = new Rotation2d[4];
    for (int i = 0; i < 4; i++) {
      turnPositions[i] = new Rotation2d(moduleInputs[i].turnAbsolutePositionRad);
    }
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

  public ArrayList<Double> getModulesTemp() {

    ArrayList<Double> temps = new ArrayList<>();

    for (AndromedaModule andromedaModule : modules) {
      for (int i = 0; i < 2; i++) {
        temps.add(andromedaModule.getTemp()[i]);
      }
    }
    return temps;
  }
}
