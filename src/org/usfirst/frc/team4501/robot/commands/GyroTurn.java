package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class GyroTurn extends Command implements PIDOutput {

	double angle;
	double kP = 0.01;
	double kI = 0.035;
	double kD = 0;
	double kF = 0;
	double kToleranceDegrees = 2;
	double kMaxRange = 0.6;
	boolean finished = false;

	PIDController turnController;
	double rotate;

	public GyroTurn(double angle) {
		// Use requires() here to declare subsystem dependencies
		// e.g. requires(chassis);
		requires(Robot.driveTrain);
		this.angle = angle;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.ahrs.reset();
		turnController = new PIDController(kP, kI, kD, kF, Robot.ahrs, (PIDOutput) this);
		turnController.setInputRange(-180.0f, 180.0f);
		turnController.setOutputRange(-kMaxRange, kMaxRange);
		turnController.setAbsoluteTolerance(kToleranceDegrees);
		turnController.setContinuous(true);
		turnController.setSetpoint(angle);
		turnController.enable();
		finished = false;
		
		/* Add the PID Controller to the Test-mode dashboard, allowing manual */
		/* tuning of the Turn Controller's P, I and D coefficients. */
		/* Typically, only the P value needs to be modified. */
		LiveWindow.addActuator("Gyro Turn", "RotateController", turnController);
	}
	
	int counter = 0;

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		System.out.printf("angle=%7.2g rotate=%7.2g\n", Robot.ahrs.getAngle(), rotate);
		Robot.driveTrain.driveTime(0, rotate);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		double err = Math.abs(Robot.ahrs.getAngle() - angle );
		if(err <= kToleranceDegrees) {
			finished = true;
			
			System.out.println("IS FINISHED");
		}
		return finished;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.driveTrain.driveTime(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}

	@Override
	public void pidWrite(double output) {
		rotate = output;
	}
}
