/**
 * Created by Juan Pablo Gutiérrez 17/03/2022
 * 
 * Team 6647
 * Be bold, be voltec
 */

package com.team6647;

import edu.wpi.first.wpilibj.RobotBase;

public final class Main {
  private Main() {}

  public static void main(String... args) {
    RobotBase.startRobot(Robot::new);
  }
}
