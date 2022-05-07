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

	static Compressor compressor = new Compressor(Constants.kPCMCanID, PneumaticsModuleType.REVPH);

	public static Compressor getCompressor() {
		return compressor;
	}

	static TalonFX frontIntakeMotor = new TalonFX(Constants.kFrontIntakeEscCanID);
	static DoubleSolenoid frontIntakeSolenoid = new DoubleSolenoid(Constants.kPCMCanID, PneumaticsModuleType.REVPH, Constants.kFrontIntakeSolenoidAChannel, Constants.kFrontIntakeSolenoidBChannel);
	
	public static TalonFX getFrontIntakeESC() {
		return frontIntakeMotor;
	}

	public static DoubleSolenoid getFrontIntakeSolenoid() {
		return frontIntakeSolenoid;
	}

	static PowerDistribution pdh = new PowerDistribution(Constants.kPDHCanID, ModuleType.kRev);

	public static PowerDistribution getPDH() {
		return pdh;
	}
	static VictorSPX indexerA = new VictorSPX(Constants.kIndexerACanID);

	public static VictorSPX getIndexerA() {
		return indexerA;
	}
}
