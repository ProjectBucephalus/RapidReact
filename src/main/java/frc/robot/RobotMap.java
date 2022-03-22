package frc.robot;


import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;

public class RobotMap {

	static TalonFX leftDriveA = new TalonFX(Constants.kLeftDriveACanID);
	static TalonFX leftDriveB = new TalonFX(Constants.kLeftDriveBCanID);
	static TalonFX leftDriveC = new TalonFX(Constants.kLeftDriveCCanID);


	static TalonFX rightDriveA = new TalonFX(Constants.kRightDriveACanID);
	static TalonFX rightDriveB = new TalonFX(Constants.kRightDriveBCanID);
	static TalonFX rightDriveC = new TalonFX(Constants.kRightDriveCCanID);

	public static TalonFX getLeftDriveA() {
		return leftDriveA;
	}

	public static TalonFX getRightDriveA() {
		return rightDriveA;
	}

	public static TalonFX getLeftDriveB() {
		return leftDriveB;
	}

	public static TalonFX getRightDriveB() {
		return rightDriveB;
	}

	public static TalonFX getLeftDriveC() {
		return leftDriveC;
	}

	public static TalonFX getRightDriveC() {
		return rightDriveC;
	}

	static TalonFX shooterBottom = new TalonFX(Constants.kShooterBottomCanID);
	static TalonFX shooterTop = new TalonFX(Constants.kShooterTopCanID);

	public static TalonFX getShooterBottom() {
		return shooterBottom;
	}

	public static TalonFX getShooterTop() {
		return shooterTop;
	}

	static Compressor compressor = new Compressor(Constants.kPCMCanID, PneumaticsModuleType.REVPH);

	public static Compressor getCompressor() {
		return compressor;
	}

	static TalonFX frontIntakeMotor = new TalonFX(Constants.kFrontIntakeEscCanID);
	static DoubleSolenoid frontIntakeSolenoid = new DoubleSolenoid(Constants.kPCMCanID, PneumaticsModuleType.REVPH, Constants.kFrontIntakeSolenoidAChannel, Constants.kFrontIntakeSolenoidBChannel);

	static TalonFX backIntakeMotor = new TalonFX(Constants.kBackIntakeEscCanID);
	public static TalonFX getBackIntakeESC() {
		return backIntakeMotor;
	}
	
	public static TalonFX getFrontIntakeESC() {
		return frontIntakeMotor;
	}

	public static DoubleSolenoid getFrontIntakeSolenoid() {
		return frontIntakeSolenoid;
	}

	static TalonFX leftWinch = new TalonFX(Constants.kLeftWinchCanID);
	static TalonFX rightWinch = new TalonFX(Constants.kRightWinchCanID);
	static CANifier climberSensors = new CANifier(Constants.kClimberCanifierCanID);

	static DoubleSolenoid climberSolenoid = new DoubleSolenoid(Constants.kPCMCanID, PneumaticsModuleType.REVPH, Constants.kClimberSolenoidAChannel, Constants.kClimberSolenoidBChannel);

	public static TalonFX getLeftWinch() {
		return leftWinch;
	}

	public static TalonFX getRightWinch() {
		return rightWinch;
	}

	public static CANifier getClimberSensors() {
		return climberSensors;
	}

	public static DoubleSolenoid getClimberSolenoid() {
		return climberSolenoid;
	}

	static PowerDistribution pdh = new PowerDistribution(Constants.kPDHCanID, ModuleType.kRev);

	public static PowerDistribution getPDH() {
		return pdh;
	}

	static VictorSPX feedA = new VictorSPX(Constants.kFeedBCanID);
	static VictorSPX feedB = new VictorSPX(Constants.kFeedACanID);
	static VictorSPX indexerA = new VictorSPX(Constants.kIndexerACanID);

	public static VictorSPX getFeedA() {
		return feedA;
	}
	public static VictorSPX getFeedB() {
		return feedB;
	}
	public static VictorSPX getIndexerA() {
		return indexerA;
	}

	public static DigitalInput ballSense = new DigitalInput(0);
}
