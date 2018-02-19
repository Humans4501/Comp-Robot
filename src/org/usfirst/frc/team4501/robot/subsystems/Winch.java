package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.RobotMap;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Winch extends Subsystem {
	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	Talon talon1;
	DigitalInput limitSwitch;

	public Winch() {
		talon1 = new Talon(RobotMap.WINCHTALON);
		limitSwitch = new DigitalInput(RobotMap.WINCH_LIMIT_SWITCH);
	}

	public void setWinchSpeed(double WinchSpeed) {
		talon1.set(WinchSpeed);
	}
	
	public boolean isLimitSwitchSet() {
		return !limitSwitch.get();
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
