package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses the GyroTurnSubsystem to turn to the specified angle.
 */
public class GyroTurn extends Command {
	double angle;

	public GyroTurn(double angle) {
		requires(Robot.driveTrain);
		requires(Robot.gyroTurn);
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.gyroTurn.setAngle(angle);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.driveTrain.arcadeDrive(0, Robot.gyroTurn.rotateSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gyroTurn.isTurningDone();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
