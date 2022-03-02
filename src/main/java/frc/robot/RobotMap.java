package frc.robot;


import com.ctre.phoenix.CANifier;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.Solenoid;
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
	static Solenoid frontIntakeSolenoid = new Solenoid(Constants.kPCMCanID, PneumaticsModuleType.REVPH, Constants.kFrontIntakeSolenoidChannel);

	static TalonFX backIntakeMotor = new TalonFX(Constants.kBackIntakeEscCanID);
	public static TalonFX getBackIntakeESC() {
		return backIntakeMotor;
	}
	
	public static TalonFX getFrontIntakeESC() {
		return frontIntakeMotor;
	}

	public static Solenoid getFrontIntakeSolenoid() {
		return frontIntakeSolenoid;
	}

	static TalonFX leftWinch = new TalonFX(Constants.kLeftWinchCanID);
	static TalonFX rightWinch = new TalonFX(Constants.kRightWinchCanID);
	static CANifier climberSensors = new CANifier(Constants.kClimberCanifierCanID);

	public static TalonFX getLeftWinch() {
		return leftWinch;
	}

	public static TalonFX getRightWinch() {
		return rightWinch;
	}

	public static CANifier getClimberSensors() {
		return climberSensors;
	}

	static PowerDistribution pdh = new PowerDistribution(Constants.kPDHCanID, ModuleType.kRev);

	public static PowerDistribution getPDH() {
		return pdh;
	}

	static TalonSRX feedA = new TalonSRX(Constants.kFeedACanID);
	static TalonSRX feedB = new TalonSRX(Constants.kFeedBCanID);
	static TalonSRX indexerA = new TalonSRX(Constants.kIndexerACanID);

	public static TalonSRX getFeedA() {
		return feedA;
	}
	public static TalonSRX getFeedB() {
		return feedB;
	}
	public static TalonSRX getIndexerA() {
		return indexerA;
	}


}
