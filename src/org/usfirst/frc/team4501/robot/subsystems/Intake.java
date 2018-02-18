package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.Robot;
import org.usfirst.frc.team4501.robot.RobotMap;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class Intake extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Talon italon1, italon2;
	
	DoubleSolenoid intakeSol;
	
	RobotDrive intakeSystem;


	
	public Intake() {
		
		italon1 = new Talon(RobotMap.INTAKETALON_1);
		italon2 = new Talon(RobotMap.INTAKETALON_2);
		
		intakeSystem = new RobotDrive(italon1, italon2);
		
		intakeSol = new DoubleSolenoid(RobotMap.INTAKESOL1 , RobotMap.INTAKESOL2);
	}

	
		
//		pot = new AnalogPotentiometer(RobotMap.SENSORS.POTENTIOMETERA, RobotMap.SENSORS.POTENTIOMETERB, RobotMap.SENSORS.POTENTIOMETERC);


	
	public void intake(double move, double rotate) {
		intakeSystem.tankDrive(-move, -rotate);
		intakeSystem.setSafetyEnabled(false);
	}
	
	public void intakeOpen() {
		intakeSol.set(DoubleSolenoid.Value.kForward);
	}
		 
	public void intakeClose() {
		intakeSol.set(DoubleSolenoid.Value.kReverse);
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	
    
    }
}

