package frc.robot;


import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.I2C;
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
	
	private final static I2C.Port i2cPort = I2C.Port.kOnboard;

	private static final ColorSensorV3 colourSensor = new ColorSensorV3(i2cPort);

	public static ColorSensorV3 getColourSensor() {
		return colourSensor;
	}

}
