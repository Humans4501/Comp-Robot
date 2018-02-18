/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4501.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4501.robot.commands.AutoCenterGroup;
import org.usfirst.frc.team4501.robot.commands.AutoLeftorRightGroup;
import org.usfirst.frc.team4501.robot.commands.DriveAutoTimed;
import org.usfirst.frc.team4501.robot.commands.DriveUntilCollision;
import org.usfirst.frc.team4501.robot.commands.ExampleCommand;
import org.usfirst.frc.team4501.robot.commands.VisionPID;
import org.usfirst.frc.team4501.robot.subsystems.Intake;
import org.usfirst.frc.team4501.robot.subsystems.Conveyor;
import org.usfirst.frc.team4501.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4501.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4501.robot.subsystems.Shooter;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Robot instance;
//	RobotDrive myDrive = new RobotDrive(RobotMap.TALON_1, RobotMap.TALON_2);

	
	NetworkTable table;
	
	public static final ExampleSubsystem kExampleSubsystem = new ExampleSubsystem();
	public static final Drivetrain driveTrain = new Drivetrain();

	public static final Intake intake = new Intake();
	public static final Shooter shooter = new Shooter();
	public static final Conveyor conveyor = new Conveyor();

	public static OI oi;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		instance = this;
		oi = new OI();
		m_chooser.addDefault("Default Auto", new ExampleCommand());
		m_chooser.addDefault("Testing Collision", new DriveUntilCollision());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putNumber("CurrXACCL:", 0);
		SmartDashboard.putNumber("CurrYACCL:", 0);
		
		NetworkTable.setIPAddress("10.95.1.55");
		table = NetworkTable.getTable("limelight");
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		
//		m_chooser.addObject("Test Timed Auto", new DriveAutoTimed(1));
//		m_chooser.addObject("Test VisionPID", new VisionPID());
//		
//		m_chooser.addObject("Center", new AutoCenterGroup());
//		m_chooser.addObject("Left/Right", new AutoLeftorRightGroup());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * <p>
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		 * switch(autoSelected) { case "My Auto": autonomousCommand = new
		 * MyAutoCommand(); break; case "Default Auto": default: autonomousCommand = new
		 * ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		
		//L I M E L I G H T
//				double tx = table.getNumber("tx", 0);
//				double ty = table.getNumber("ty", 0);
//				double targetArea = table.getNumber("ta", 0);
//				double targetSkew = table.getNumber("ts", 0);
//				double targetView = table.getNumber("tv", 0);
//
//				SmartDashboard.putNumber("targetView", targetView);
//				SmartDashboard.putNumber("tx", tx);
//				SmartDashboard.putNumber("ty", ty);
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
//	public void setArcadeDrive(double move, double turn) {
//		//TO DO: CHANGE 0 BACK TO TURN SO IT MOVES AND TURNS AT THE SAME TIME
//		myDrive.arcadeDrive(move, turn);
//	}
}
