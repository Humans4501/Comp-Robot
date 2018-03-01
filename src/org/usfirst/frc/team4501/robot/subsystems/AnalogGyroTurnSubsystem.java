package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnalogGyroTurnSubsystem extends PIDSubsystem {
	static double kP = 0.03;
	static double kI = 0.02;
	static double kD = 0;
	static double kF = 0;
	static double kToleranceDegrees = 2;
	static double kMaxOutputRange = .8;
	
	public double rotateSpeed;
	
	public double destinationAngle;
	boolean done = true;
	
	public AnalogGyroTurnSubsystem() {
		super(kP, kI, kD);
		setInputRange(-180.0f, 180.0f);
		setOutputRange(-kMaxOutputRange, kMaxOutputRange);
		setAbsoluteTolerance(kToleranceDegrees);
		
		SmartDashboard.putData("AnalogGyroTurnPID", getPIDController());
	}
	
	public void setRelativeAngle(double angle) {
		destinationAngle = safeGetAngle() + angle;
		rotateSpeed = 0;
		done = false;
		setSetpoint(destinationAngle);
	}

	public boolean isTurningDone() {
		double deltaAngle = Math.abs(safeGetAngle() - destinationAngle);
		if (deltaAngle <= kToleranceDegrees) {
			done = true;
		}
		return done;
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.analogGyro.getAngle();
	}

	@Override
	protected void usePIDOutput(double output) {
		rotateSpeed = done ? 0 : output;
	}

	@Override
	protected void initDefaultCommand() {
	}
	
	public double safeGetAngle() {
		return (Robot.analogGyro != null) ? Robot.analogGyro.getAngle() : 0;
	}
	
}
