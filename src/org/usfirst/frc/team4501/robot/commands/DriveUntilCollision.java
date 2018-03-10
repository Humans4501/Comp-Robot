package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveUntilCollision extends Command {
	double lastX;
	double lastY;
	boolean running;

	final static double kCollisionThreshold_DeltaG = 0.5;

	public DriveUntilCollision() {
		// Use requires() here to declare subsystem dependencies
		// eg. requires(chassis);
		requires(Robot.driveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		lastX = Robot.builtInAccelerometer.getX();
		lastY = Robot.builtInAccelerometer.getY();
		running = true;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (running) {
			double currX = Robot.builtInAccelerometer.getX();
			double currY = Robot.builtInAccelerometer.getY();

			double deltaX = currX - lastX;
			double deltaY = currY - lastY;

			System.out.printf("currX=%.2f lastX= %.2g currY=%.2f lastY=%.2f\n", currX, lastX, currY, lastY);

			lastX = currX;
			lastY = currY;

			if (Math.abs(deltaX) > kCollisionThreshold_DeltaG || Math.abs(deltaY) > kCollisionThreshold_DeltaG) {
				System.out.println("HIT");
				running = false;
				return;
			}

			Robot.driveTrain.driveTime(0.85, 0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return !running;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.driveTrain.driveTime(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		running = false;
	}
}
