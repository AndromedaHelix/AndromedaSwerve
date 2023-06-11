/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647;

import com.andromedalib.robot.SuperRobotContainer;
import com.team6647.andromedaSwerve.systems.AndromedaSwerve;

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
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}
