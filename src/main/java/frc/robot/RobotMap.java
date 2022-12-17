package frc.robot;


import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

/**
 * Put docs here // TODO
 */
public class RobotMap {

	// static TalonFX leftDriveA = new TalonFX(Constants.kLeftDriveACanID);
	// static TalonFX leftDriveB = new TalonFX(Constants.kLeftDriveBCanID);
	// static TalonFX leftDriveC = new TalonFX(Constants.kLeftDriveCCanID);


	// static TalonFX rightDriveA = new TalonFX(Constants.kRightDriveACanID);
	// static TalonFX rightDriveB = new TalonFX(Constants.kRightDriveBCanID);
	// static TalonFX rightDriveC = new TalonFX(Constants.kRightDriveCCanID);

	static WPI_TalonFX leftDriveATalon = new WPI_TalonFX(Constants.kLeftDriveACanID);
	static WPI_TalonFX leftDriveBTalon = new WPI_TalonFX(Constants.kLeftDriveBCanID);
	static WPI_TalonFX rightDriveATalon = new WPI_TalonFX(Constants.kRightDriveACanID);
	static WPI_TalonFX rightDriveBTalon = new WPI_TalonFX(Constants.kRightDriveBCanID);
	static TalonFX rightdrive = new TalonFX(2);
	public static WPI_TalonFX getLeftDriveA() {
		return leftDriveATalon;
	}

	public static WPI_TalonFX getRightDriveA() {
		return rightDriveATalon;
	}

	public static WPI_TalonFX getLeftDriveB() {
		return leftDriveBTalon;
	}

	public static WPI_TalonFX getRightDriveB() {
		return rightDriveBTalon;
	}

}
