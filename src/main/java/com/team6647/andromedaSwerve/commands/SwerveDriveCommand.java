/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import com.andromedalib.math.Functions;
import com.team6647.andromedaSwerve.systems.AndromedaSwerve;
import com.team6647.util.Constants.SwerveConstants;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SwerveDriveCommand extends CommandBase {

  AndromedaSwerve swerve;
  DoubleSupplier translationY;
  DoubleSupplier translationX;
  DoubleSupplier rotation;
  BooleanSupplier fieldOriented;

  /** Creates a new SwerveDrive. */
  public SwerveDriveCommand(AndromedaSwerve andromedaSwerve, DoubleSupplier translationX, DoubleSupplier translationY,
      DoubleSupplier rotation, BooleanSupplier fieldOrientedControl) {
    this.swerve = andromedaSwerve;
    this.translationY = translationY;
    this.translationX = translationX;
    this.rotation = rotation;
    this.fieldOriented = fieldOrientedControl;

    addRequirements(swerve);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double translationYVal = Functions.handleDeadband(translationY.getAsDouble(), SwerveConstants.deadband);
    double translationXVal = Functions.handleDeadband(translationX.getAsDouble(), SwerveConstants.deadband);
    double rotationVal = Functions.handleDeadband(rotation.getAsDouble(), SwerveConstants.deadband);

    swerve.drive(
        new Translation2d(translationYVal, translationXVal).times(SwerveConstants.maxSpeed),
        rotationVal * SwerveConstants.maxAngularVelocity,
        !fieldOriented.getAsBoolean(), // DEBUG fieldOriented.getAsBoolean()
        true);
  }

}
