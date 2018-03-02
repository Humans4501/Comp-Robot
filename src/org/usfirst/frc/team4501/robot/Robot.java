/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4501.robot;

import org.usfirst.frc.team4501.robot.commands.AutoCenterLeftGroupFront;
import org.usfirst.frc.team4501.robot.commands.AutoCenterLeftGroupSide;
import org.usfirst.frc.team4501.robot.commands.AutoCenterRightGroupFront;
import org.usfirst.frc.team4501.robot.commands.AutoCenterRightGroupSide;
import org.usfirst.frc.team4501.robot.commands.AutoLeftFront;
import org.usfirst.frc.team4501.robot.commands.AutoLeftSide;
import org.usfirst.frc.team4501.robot.commands.AutoRightFront;
import org.usfirst.frc.team4501.robot.commands.AutoRightSide;
import org.usfirst.frc.team4501.robot.commands.LeftToRightFront;
import org.usfirst.frc.team4501.robot.commands.LeftToRightSide;
import org.usfirst.frc.team4501.robot.commands.RightToLeftFront;
import org.usfirst.frc.team4501.robot.commands.RightToLeftSide;
import org.usfirst.frc.team4501.robot.commands.SmoothDrive;
import org.usfirst.frc.team4501.robot.subsystems.AnalogGyroTurnSubsystem;
import org.usfirst.frc.team4501.robot.subsystems.Conveyor;
import org.usfirst.frc.team4501.robot.subsystems.Drivetrain;
import org.usfirst.frc.team4501.robot.subsystems.Intake;
import org.usfirst.frc.team4501.robot.subsystems.Shooter;
import org.usfirst.frc.team4501.robot.subsystems.Winch;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	public static Robot instance;

	NetworkTable table;

	public static final Drivetrain driveTrain = new Drivetrain();
	public static final AnalogGyroTurnSubsystem analogGyroTurn = new AnalogGyroTurnSubsystem(); 

	public static final Intake intake = new Intake();
	public static final Shooter shooter = new Shooter();
	public static final Conveyor conveyor = new Conveyor();
	public static final Winch winch = new Winch();

	public static OI oi;
	public static Gyro analogGyro;
	public static BuiltInAccelerometer builtInAccelerometer;

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
		analogGyro = new ADXRS450_Gyro();
		analogGyro.calibrate();
		builtInAccelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G);

	
		

		m_chooser.addDefault("Center Right Front", new AutoCenterRightGroupFront());
		m_chooser.addDefault("Center Right Side", new AutoCenterRightGroupSide());
		m_chooser.addDefault("Center Left Front", new AutoCenterLeftGroupFront());
		m_chooser.addDefault("Center Left Side", new AutoCenterLeftGroupSide());
		m_chooser.addDefault("Right Front", new AutoRightFront());
		m_chooser.addDefault("Right Side", new AutoRightSide());
		m_chooser.addDefault("Left Front", new AutoLeftFront());
		m_chooser.addDefault("Left Side", new AutoLeftSide());
		m_chooser.addDefault("Left to Right Front", new LeftToRightFront());
		m_chooser.addDefault("Left to Right Side", new LeftToRightSide());
		m_chooser.addDefault("Right to Left Front", new RightToLeftFront());
		m_chooser.addDefault("Right to Left Side", new RightToLeftSide());

		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", m_chooser);
		SmartDashboard.putNumber("CurrXACCL:", 0);
		SmartDashboard.putNumber("CurrYACCL:", 0);

//		NetworkTable.setIPAddress("10.95.1.55");
//		table = NetworkTable.getTable("limelight");
		// chooser.addObject("My Auto", new MyAutoCommand());

		// m_chooser.addObject("Test Timed Auto", new DriveAutoTimed(1));
		// m_chooser.addObject("Test VisionPID", new VisionPID());
		//
		// m_chooser.addObject("Center", new AutoCenterGroup());
		// m_chooser.addObject("Left/Right", new AutoLeftorRightGroup());
	}

	@Override
	public void robotPeriodic() {
		super.robotPeriodic();
		
		// AnalogGyro
		SmartDashboard.putNumber("AnalogGyro_Angle", analogGyro.getAngle());

		// L I M E L I G H T
		// double tx = table.getNumber("tx", 0);
		// double ty = table.getNumber("ty", 0);
		// double targetArea = table.getNumber("ta", 0);
		// double targetSkew = table.getNumber("ts", 0);
		// double targetView = table.getNumber("tv", 0);
		//
		// SmartDashboard.putNumber("targetView", targetView);
		// SmartDashboard.putNumber("tx", tx);
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
		// SmartDashboard.putNumber("ty", ty);
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
		// Start smooth driving.
		Scheduler.getInstance().add(new SmoothDrive());
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
}
