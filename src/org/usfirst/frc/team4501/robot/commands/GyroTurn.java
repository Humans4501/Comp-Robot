package org.usfirst.frc.team4501.robot.commands;

import org.usfirst.frc.team4501.robot.Robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

/**
 *
 */
public class GyroTurn extends Command implements PIDOutput {
	
	double angle;
	double kP = 1;
	double kI = 0;
	double kD = 0;
	double kF = 0;
	double kToleranceDegrees = 2;
	
	AHRS ahrs;

	PIDController turnController;
	
	
	
    public GyroTurn(double angle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.driveTrain);
    	this.angle = angle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {

		try {
			/***********************************************************************
			 * navX-MXP: - Communication via RoboRIO MXP (SPI, I2C, TTL UART) and USB. - See
			 * http://navx-mxp.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * navX-Micro: - Communication via I2C (RoboRIO MXP or Onboard) and USB. - See
			 * http://navx-micro.kauailabs.com/guidance/selecting-an-interface.
			 * 
			 * Multiple navX-model devices on a single robot are supported.
			 ************************************************************************/
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX MXP:  " + ex.getMessage(), true);
		}
		 turnController = new PIDController(kP, kI, kD, kF, ahrs, (PIDOutput) this);
		
		 turnController.setInputRange(-180.0f, 180.0f);
		 turnController.setOutputRange(-1.0, 1.0);
  		turnController.setAbsoluteTolerance(kToleranceDegrees);
		 turnController.setContinuous(true);
		 turnController.setSetpoint(angle);
		/* Add the PID Controller to the Test-mode dashboard, allowing manual */
		/* tuning of the Turn Controller's P, I and D coefficients. */
		/* Typically, only the P value needs to be modified. */
	      LiveWindow.addActuator("Gyro Turn", "RotateController", turnController);
	}
    

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
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

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		Robot.driveTrain.driveTime(0, output);
		
	}
}
