/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647;

import com.andromedalib.robot.SuperRobotContainer;
import com.team6647.andromedaSwerve.commands.SwerveDriveCommand;
import com.team6647.andromedaSwerve.systems.AndromedaSwerve;
import com.team6647.util.Constants.OperatorConstants;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class RobotContainer extends SuperRobotContainer {
  private static RobotContainer instance;

  /* Systems */
  private AndromedaSwerve andromedaSwerve;

  private RobotContainer() {
  }

  public static RobotContainer getInstance() {
    if (instance == null) {
      instance = new RobotContainer();
    }

    return instance;
  }

  @Override
  public void initSubsystems() {
    andromedaSwerve = AndromedaSwerve.getInstance();
  }

  @Override
  public void configureBindings() {

    andromedaSwerve.setDefaultCommand(
        new SwerveDriveCommand(
            andromedaSwerve,
            () -> -OperatorConstants.driverController1.getLeftY(),
            () -> -OperatorConstants.driverController1.getLeftX(),
            () -> -OperatorConstants.driverController1.getRightX(),
            () -> OperatorConstants.driverController1.leftStick().getAsBoolean()));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
