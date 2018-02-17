package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.Robot;
import org.usfirst.frc.team4501.robot.RobotMap;
import org.usfirst.frc.team4501.robot.commands.Drive;

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

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
	
	Talon talon1, talon2, talon3, talon4;
	RobotDrive drive;
	
	Solenoid sol;
	
	double gyroData;
	
	double targetOffsetAngle_Horizontal;
	double targetOffsetAngle_Vertical;
	double targetArea;
	double targetSkew;
	
	PIDController turnController;
	
	double rotateToAngleRate;
    static final double kP = 0.03;
	static final double kI = 0.00;
	static final double kD = 0.00;
	static final double kF = 0.00;
	static final double kToleranceDegrees = 2.0f;    
	static final double kTargetAngleDegrees = 90.0f;
	
	int expiryMsec;
	
	ADXRS450_Gyro gyro;
	
    AHRS ahrs;
    
	public Drivetrain() {
		
		talon1 = new Talon(RobotMap.TALON_1);
		talon2 = new Talon(RobotMap.TALON_2);
		
		sol = new Solenoid(RobotMap.SOLENOID);
		
		drive = new RobotDrive(talon1,talon2);
		
		turnController = new PIDController(kP, kI, kD, kF, ahrs, (PIDOutput) this);
		
		


		
		 try {
				/***********************************************************************
				 * navX-MXP:
				 * - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB.            
				 * - See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
				 * 
				 * navX-Micro:
				 * - Communication via I2C (RoboRIO MXP or Onboard) and USB.
				 * - See http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
				 * 
				 * Multiple navX-model devices on a single robot are supported.
				 ************************************************************************/
	            ahrs = new AHRS(SPI.Port.kMXP); 
	        } catch (RuntimeException ex ) {
	            DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
	        }
	        
	        turnController.setInputRange(-180.0f,  180.0f);
	        turnController.setOutputRange(-1.0, 1.0);
	        turnController.setAbsoluteTolerance(kToleranceDegrees);
	        turnController.setContinuous(true);
	        turnController.disable();
	        
	        /* Add the PID Controller to the Test-mode dashboard, allowing manual  */
	        /* tuning of the Turn Controller's P, I and D coefficients.            */
	        /* Typically, only the P value needs to be modified.                   */
	        LiveWindow.addActuator("DriveSystem", "RotateController", turnController);        
	    }
		
	
	
	
	public void driveTime(double forward, double rotate) {
		drive.arcadeDrive(-forward, -rotate);
		drive.setSafetyEnabled(false);
	}
	
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        //setDefaultCommand(new MySpecialCommand());
    	setDefaultCommand(new Drive());
    	
    	
    }
    
    public void shiftHigh() {
    	sol.set(true);
    }
    
    public void shiftLow() {
    	sol.set(false);
}
    
    public void resetGyro() {
		gyro.reset();
	}
	
	public double gyroData() {
		gyroData = gyro.getAngle();
		return gyroData;
	}
	
	 public void turnTo() {
   	  ahrs.reset();
         drive.setSafetyEnabled(true);
         
             boolean rotateToAngle = false;
             
             SmartDashboard.putNumber("gyro angle", ahrs.getAngle());
             
             turnController.setSetpoint(90.0);
             rotateToAngle = true;
             
             double currentRotationRate;
             if ( rotateToAngle ) {
                 turnController.enable();
                 currentRotationRate = rotateToAngleRate;
             } else {
                 turnController.disable();
                 currentRotationRate = 0;
             }
            // if(ahrs.getAngle() >89 && ahrs.getAngle()< 91 ) {
           	//  currentRotationRate = 0;
             //}
           	  drive.arcadeDrive(0, currentRotationRate);
             
           	  
           	  
             Timer.delay(0.005);	// wait for a motor update time
         }
   

    
}