package com.team6647;

import com.andromedalib.robot.SuperRobot;

public class Robot extends SuperRobot {

    private RobotContainer container;

    @Override
    public void robotInit() {
        container = RobotContainer.getInstance();
        super.setRobotContainer(container);

        super.robotInit();
    }
}
