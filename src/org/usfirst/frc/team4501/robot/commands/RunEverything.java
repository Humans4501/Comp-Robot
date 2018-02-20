package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RunEverything extends Command {
	
	AHRS ahrs;
	
	double lastX;
	double lastY;
	
	final static double kCollisionThreshold_DeltaG = 0.5;


    public RunEverything() {
        
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.intake);
        requires(Robot.shooter);
        requires(Robot.conveyor);
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
        		
        		Robot.driveTrain.driveTime(0, 0);
        		Robot.intake.intake(1,1);
            	Robot.shooter.shoot(1, 1);
            	Robot.conveyor.convey(1, 1);
            	
            	break;
        	}
        	
        	lastX = currX;
        	lastY = currY;
        	
        	Robot.driveTrain.driveTime(0.85, 0);
    	}
        	
    	
    }

    // Called once after timeout
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }

	@Override
	protected boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}
}
