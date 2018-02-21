package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This command will run the winch until the limit switch is triggered.
 */
public class WinchRaiseArm extends Command {
	private boolean done;

    public WinchRaiseArm() {
    	requires(Robot.winch);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	while (!done) {
    		Robot.winch.setWinchSpeed(0.5);
    		done = Robot.winch.isLimitSwitchSet();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	 return done = true;
    }

    // Called once after isFinished returns true
    protected void end() {
    
    	Robot.winch.setWinchSpeed(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
