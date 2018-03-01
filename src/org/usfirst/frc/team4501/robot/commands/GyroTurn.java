package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * Uses the GyroTurnSubsystem to turn to the specified angle.
 * 
 * According to <a href="https://www.pdocs.kauailabs.com/navx-mxp/examples/rotate-to-angle-2/">
 * this page</a>, it's best to rotate in place. Hence we use tankDrive instead of arcadeDrive.
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
		Robot.gyroTurn.setRelativeAngle(angle);
		Robot.gyroTurn.enable();
		
		while (Robot.ahrs.isCalibrating()) {
			System.out.println("waiting for NAVX calibration");
		}
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.printf("angle=%.2f destAngle=%.2f rotateSpeed=%.2f\n",
				Robot.gyroTurn.safeGetAngle(), Robot.gyroTurn.destinationAngle, Robot.gyroTurn.rotateSpeed);
		Robot.driveTrain.tankDrive(-Robot.gyroTurn.rotateSpeed, Robot.gyroTurn.rotateSpeed);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return Robot.gyroTurn.isTurningDone();
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.tankDrive(0, 0);
		Robot.gyroTurn.disable();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
