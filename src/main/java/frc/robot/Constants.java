
package frc.robot;

public class Constants {

    public static int kLeftDriveACanID = 1;
    public static int kLeftDriveBCanID = 2;
	public static int kLeftDriveCCanID = 3;

    public static int kRightDriveACanID = 5;
    public static int kRightDriveBCanID = 6;
	public static int kRightDriveCCanID = 7;

	public static int kShooterBottomCanID = 9;
	public static int kShooterTopCanID = 10;

	public static final int kIndexerACanID = 13;
	public static final int kIndexerBCanID = 14;
	public static final int kFeedACanID = 15;
	public static final int kFeedBCanID = 16;

	public static final int kLeftWinchCanID = 19;
	public static final int kRightWinchCanID = 20;


	public static final int kPCMCanID = 50;
	public static final int kPDHCanID = 51;

	public static int kFrontIntakeEscCanID = 11;

	public static int kFrontIntakeSolenoidChannel = 0;

    public static double kDriveMaxSpeed = 4.18;
	public static double kDriveMaxAccel = 4.0;
	public static double kDriveMaxTurnSpeed = 12.12;
	public static double kDriveMaxTurnAccel = 24.24;
	public static final int kBackIntakeEscCanID = 12;
    
	public static final double kVisionTurnKp = 0.029;//was 0.024; // 0.012 for 2 centre nitrile
	public static final double kDriveTurnStictionConstant = 0.0; // FIXME
	public static final double kGyroTurnKp = 0.007; // 0.004 for 2 centre nitrile
	public static final double kGyroDriveTurnKp = 0.012;  // FIXME
	public static final double kEncoderDriveKp = 0.7;  // FIXME
	public static final double kDriveEncoderConversionFactor = 0.000023077; //1; //0.00089291;

	public static final double kShooterP = 0.25;
	public static final double kShooterI = 0.00014;

	public static final double kClimberWinchP = 0.00823;

	public static final double kIdleCurrent = 1; //Ampres
	public static final double kIdleVoltageCutoff = 12.2; //volts

}