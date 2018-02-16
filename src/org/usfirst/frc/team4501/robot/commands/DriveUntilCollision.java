package org.usfirst.frc.team4501.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4501.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

/**
 *
 */
public class DriveUntilCollision extends Command {
	
	AHRS ahrs;
	
	double lastX;
	double lastY;
	
	final static double kCollisionThreshold_DeltaG = 0.5;


    public DriveUntilCollision() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	try {
    	ahrs = new AHRS(SPI.Port.kMXP);
    	
    	} catch (RuntimeException e) {
    		DriverStation.reportError("Error Instantiating navX-MXP: " + e.getMessage(), true);
    	}
    	
    }
     

    // Called just before this Command runs the first time
    protected void initialize() {
    	                  
    	lastX = ahrs.getWorldLinearAccelX();
    	lastY = ahrs.getWorldLinearAccelY();
    	
    	System.out.println("Running Collision Command");
    	
    	
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	boolean done = false;

    	while(!done) {
    		
    		double currX = ahrs.getWorldLinearAccelX();
        	double currY = ahrs.getWorldLinearAccelY();
        	
        	double deltaX = currX-lastX;
        	double deltaY = currY-lastY;
        	
        	System.out.printf("currX = %.2g lastX= %.2g\n", currX, lastX);
    		System.out.printf("currY = %.2g lastY= %.2g\n", currY, lastY);
    		System.out.println("HIT");
        	
        	if(Math.abs(deltaX)>kCollisionThreshold_DeltaG || Math.abs(deltaY)>kCollisionThreshold_DeltaG) {
        		
 
        		//TODO: STOP MOTOR DRIVE
        		break;
        	}
        	
        	lastX = currX;
        	lastY = currY;
        	
        	//TODO: ADD MOTOR DRIVE
    	}
    	
    	
    
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
