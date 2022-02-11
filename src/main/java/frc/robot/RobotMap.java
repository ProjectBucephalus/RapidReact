package frc.robot;


import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;

public class RobotMap {

	static TalonFX leftDriveA = new TalonFX(Constants.kLeftDriveACanID);
	static TalonFX leftDriveB = new TalonFX(Constants.kLeftDriveBCanID);
	static TalonFX leftDriveC = new TalonFX(Constants.kLeftDriveCCanID);
	//public static MotorControllerGroup leftDriveMotors = new MotorControllerGroup(leftDriveA, leftDriveB, leftDriveC);


	static TalonFX rightDriveA = new TalonFX(Constants.kRightDriveACanID);
	static TalonFX rightDriveB = new TalonFX(Constants.kRightDriveBCanID);
	static TalonFX rightDriveC = new TalonFX(Constants.kRightDriveCCanID);
	//public static MotorControllerGroup rightDriveMotors = new MotorControllerGroup(rightDriveA, rightDriveB, rightDriveC);

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
	static Solenoid frontIntakeSolenoid = new Solenoid(PneumaticsModuleType.REVPH, Constants.kFrontIntakeSolenoidChannel);
	
	public static TalonFX getFrontIntakeESC() {
		return frontIntakeMotor;
	}

	public static Solenoid getFrontIntakeSolenoid() {
		return frontIntakeSolenoid;
	}

	static TalonFX leftWinch = new TalonFX(Constants.kLeftWinchCanID);
	static TalonFX rightWinch = new TalonFX(Constants.kRightWinchCanID);

	public static TalonFX getLeftWinch() {
		return leftWinch;
	}

	public static TalonFX getRightWinch() {
		return rightWinch;
	}

	static TalonSRX indexerA = new TalonSRX(Constants.kIndexerACanID);
	static TalonSRX indexerB = new TalonSRX(Constants.kIndexerBCanID);
	static TalonSRX feedA = new TalonSRX(Constants.kFeedACanID);
	static TalonSRX feedB = new TalonSRX(Constants.kFeedBCanID);


	public static TalonSRX getIndexerA() {
		return indexerA;
	}
	public static TalonSRX getIndexerB() {
		return indexerB;
	}
	public static TalonSRX getFeedA() {
		return feedA;
	}
	public static TalonSRX getFeedB() {
		return feedB;
	}

}
