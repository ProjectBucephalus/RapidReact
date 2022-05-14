package frc.robot;


import com.ctre.phoenix.motorcontrol.can.TalonFX;


import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;


/**
 * Put docs here // TODO
 */
public class RobotMap {

	static TalonFX leftDriveA = new TalonFX(Constants.kLeftDriveACanID);
	static TalonFX leftDriveB = new TalonFX(Constants.kLeftDriveBCanID);
	static TalonFX rightDriveA = new TalonFX(Constants.kRightDriveACanID);
	static TalonFX rightDriveB = new TalonFX(Constants.kRightDriveBCanID);

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

	static TalonFX shooterBottom = new TalonFX(Constants.kShooterBottomCanID);
	static TalonFX shooterTop = new TalonFX(Constants.kShooterTopCanID);

	public static TalonFX getShooterBottom() {
		return shooterBottom;
	}

	public static TalonFX getShooterTop() {
		return shooterTop;
	}

	static Compressor compressor = new Compressor(Constants.kPCMCanID, PneumaticsModuleType.CTREPCM);

	public static Compressor getCompressor() {
		return compressor;
	}

	static TalonFX frontIntakeMotor = new TalonFX(Constants.kFrontIntakeEscCanID);
	static DoubleSolenoid frontIntakeSolenoid = new DoubleSolenoid(Constants.kPCMCanID, PneumaticsModuleType.CTREPCM, Constants.kFrontIntakeSolenoidAChannel, Constants.kFrontIntakeSolenoidBChannel);
	//static Solenoid frontIntakeSolenoid = new Solenoid(Constants.kPCMCanID, PneumaticsModuleType.CTREPCM, Constants.kFrontIntakeSolenoidAChannel);

	public static TalonFX getFrontIntakeESC() {
		return frontIntakeMotor;
 	}

	public static DoubleSolenoid  getFrontIntakeSolenoid() {
		return frontIntakeSolenoid;
	}

	static TalonFX indexerA = new TalonFX(Constants.kIndexerACanID);

	public static TalonFX getIndexerA() {
		return indexerA;
	}
}
