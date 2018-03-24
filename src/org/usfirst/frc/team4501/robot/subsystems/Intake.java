package org.usfirst.frc.team4501.robot.subsystems;

import org.usfirst.frc.team4501.robot.RobotMap;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {

	// Put methods for controlling this subsystem
	// here. Call these from Commands.

	Talon italon1, italon2;

	DoubleSolenoid intakeSol;
	DoubleSolenoid thirdWheel;
	DoubleSolenoid liftSol;

	RobotDrive intakeSystem;

	public Intake() {

		italon1 = new Talon(RobotMap.INTAKETALON_1);
		italon2 = new Talon(RobotMap.INTAKETALON_2);

		// liftSol = new DoubleSolenoid(RobotMap.LIFTSOL1, RobotMap.LIFTSOL2);

		intakeSystem = new RobotDrive(italon1, italon2);

		 intakeSol = new DoubleSolenoid(RobotMap.INTAKESOL1 , RobotMap.INTAKESOL2);
		 thirdWheel = new DoubleSolenoid(RobotMap.THIRDWHEEL1 , RobotMap.THIRDWHEEL2);
	}

	// pot = new AnalogPotentiometer(RobotMap.SENSORS.POTENTIOMETERA,
	// RobotMap.SENSORS.POTENTIOMETERB, RobotMap.SENSORS.POTENTIOMETERC);

	public void intake(double move, double rotate) {
		intakeSystem.tankDrive(-move, -rotate);
		intakeSystem.setSafetyEnabled(false);
	}

	public void intakeOpen() {
		intakeSol.set(DoubleSolenoid.Value.kReverse);
		thirdWheel.set(DoubleSolenoid.Value.kReverse);
	}

	public void intakeClose() {
		intakeSol.set(DoubleSolenoid.Value.kForward);
		thirdWheel.set(DoubleSolenoid.Value.kForward);
	}

	public void liftUp() {
		liftSol.set(DoubleSolenoid.Value.kForward);
	}

	public void liftDown() {
		liftSol.set(DoubleSolenoid.Value.kReverse);
	}

	@Override
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());

	}
}
