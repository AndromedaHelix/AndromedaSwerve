/**
 * Written by Juan Pablo Gutiérrez
 */
package com.team6647.andromedaSwerve.systems;

import com.team6647.andromedaSwerve.andromedaModule.AndromedaModule;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class AndromedaSwerve extends SubsystemBase {
  private static AndromedaSwerve instance;
  private static AndromedaModule[] modules;

  public AndromedaSwerve() {
  }

  public static AndromedaSwerve getInstance() {
    if (instance == null) {
      instance = new AndromedaSwerve();
    }
    return instance;
  }

  @Override
  public void periodic() {
  }
}
