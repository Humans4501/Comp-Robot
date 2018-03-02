package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class WinchHoldToRaiseArm extends Command {
	private static final double RAISE_ARM_WINCH_SPEED = 0.5;

	private boolean done;

	public WinchHoldToRaiseArm() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
		requires(Robot.winch);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.winch.resetLimitSwitch();
		done = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.winch.runWinch(RAISE_ARM_WINCH_SPEED);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
        return true;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
