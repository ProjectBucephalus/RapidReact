
package frc.robot;


public class Constants {
    public static int kLeftDriveACanID = 5;
    public static int kLeftDriveBCanID = 6;

    public static int kRightDriveACanID = 7;
    public static int kRightDriveBCanID = 8;

	public static int kShooterBottomCanID = 16;
	public static int kShooterTopCanID = 17;

	public static final int kPCMCanID = 50;
	public static final int kPDHCanID = 51;

	public static int kFrontIntakeEscCanID = 11;

	public static int kFrontIntakeSolenoidAChannel = 0;
	public static int kFrontIntakeSolenoidBChannel = 1;

	public static final int kIndexerACanID = 18;

	public static double kDriveMaxSpeed = 4.18;
	public static double kDriveMaxAccel = 4.0;
	public static double kDriveMaxTurnSpeed = 12.12;
	public static double kDriveMaxTurnAccel = 24.24;
	public static int kGoalHeightInches = 100;
    public static int kLimelightHeightInches = 50;
	public static final int kBackIntakeEscCanID = 12;
    
	public static final double kVisionTurnKp = 0.012
	;//was 0.024; // 0.012 for 2 centre nitrile
	public static final double kDriveTurnStictionConstant = 0.0; // FIXME
	public static final double kEncoderDriveKp = 0.7;  // FIXME
	public static final double kDriveEncoderConversionFactor = 0.000023077; //1; //0.00089291;

	public static final double kShooterP = 0.365832;
	public static final double kShooterI = 0.00012;
	public static final double kShooterD = 20.0;

	public static final double kIdleCurrent = 1; //Ampres
	public static final double kIdleVoltageCutoff = 12.2; //volts
}