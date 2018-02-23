package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class GyroTurnSubsystem extends PIDSubsystem {
	static double kP = 0.01;
	static double kI = 0.035;
	static double kD = 0;
	static double kF = 0;
	static double kToleranceDegrees = 2;
	static double kMaxOutputRange = 0.6;
	
	public double rotateSpeed;
	
	double angle;
	boolean done = true;
	
	public GyroTurnSubsystem() {
		super(kP, kI, kD);
		setInputRange(-180.0f, 180.0f);
		setOutputRange(-kMaxOutputRange, kMaxOutputRange);
		setAbsoluteTolerance(kToleranceDegrees);
		
		SmartDashboard.putData("GyroTurnPID", getPIDController());
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
		rotateSpeed = 0;
		done = false;
		Robot.ahrs.reset();
		setSetpoint(angle);
	}

	public boolean isTurningDone() {
		double deltaAngle = Math.abs(Robot.ahrs.getYaw() - angle);
		if (deltaAngle <= kToleranceDegrees) {
			done = true;
		}
		return done;
	}
	
	@Override
	protected double returnPIDInput() {
		return Robot.ahrs.getYaw();
	}

	@Override
	protected void usePIDOutput(double output) {
		rotateSpeed = done ? 0 : output;
	}

	@Override
	protected void initDefaultCommand() {
	}
}
