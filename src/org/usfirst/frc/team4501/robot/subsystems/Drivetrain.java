package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.Robot;
import org.usfirst.frc.team4501.robot.RobotMap;
import org.usfirst.frc.team4501.robot.commands.Drive;
import org.usfirst.frc.team4501.robot.commands.SmoothDrive;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/**
 *
 */
public class Drivetrain extends Subsystem {
	public static Drivetrain instance;

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	Talon talon1, talon2, talon3, talon4;
	RobotDrive drive;

	DoubleSolenoid driveSol;


	public Drivetrain() {
		instance = this;

		talon1 = new Talon(RobotMap.TALON_1);
		talon2 = new Talon(RobotMap.TALON_2);

		driveSol = new DoubleSolenoid(RobotMap.DRIVESOL1, RobotMap.DRIVESOL2);

		drive = new RobotDrive(talon1, talon2);

	}

	public void driveTime(double forward, double rotate) {
		drive.arcadeDrive(-forward, -rotate);
		drive.setSafetyEnabled(false);
	}
	
	public void arcadeDrive(double forward, double rotate) {
		drive.arcadeDrive(forward, rotate);
	}

	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
		setDefaultCommand(new SmoothDrive());

	}

	public void shiftHigh() {
		driveSol.set(DoubleSolenoid.Value.kForward);
	}

	public void shiftLow() {
		driveSol.set(DoubleSolenoid.Value.kReverse);
	}
	
}
