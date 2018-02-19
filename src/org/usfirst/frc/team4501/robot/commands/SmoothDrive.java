package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SmoothDrive extends Command {

	public final double maxPowerPerSecond = 1;
	public final double threshold = 0.4;
	
	public double lastPower;
	public long lastTime;
	
    public SmoothDrive() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	lastTime = System.currentTimeMillis();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	long now = System.currentTimeMillis();
    	long deltaTime = now - lastTime;
    	lastTime = now;
    	
    	double desiredPower = Robot.oi.getTriggers();
    	
    	double calculatedPower;
    	
    	
    	if(desiredPower > lastPower) {
    		calculatedPower = lastPower + maxPowerPerSecond * deltaTime/1000;
    	}
    	else {
    		calculatedPower = desiredPower;
    	}
		calculatedPower = Math.min(calculatedPower, 1);
		calculatedPower = Math.max(calculatedPower, -1);
		
		if( 0 < calculatedPower && calculatedPower < threshold && desiredPower > 0.4) {
    		calculatedPower = 0.4;
    	}
    	
    	
    	Robot.driveTrain.driveTime(-calculatedPower, -Robot.oi.getLeftXboxX());
    	
    	lastPower = calculatedPower;
    	
    	System.out.printf("desiredPower = %.2g calculatedPower = %.2g\n", desiredPower , calculatedPower);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.driveTrain.driveTime(0, 0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
