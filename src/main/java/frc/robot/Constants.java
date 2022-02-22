
package frc.robot;

public class Constants {

    public static int kLeftDriveACanID = 1;
    public static int kLeftDriveBCanID = 2;
	public static int kLeftDriveCCanID = 41;

    public static int kRightDriveACanID = 3;
    public static int kRightDriveBCanID = 4;
	public static int kRightDriveCCanID = 7;

	public static int kShooterBottomCanID = 12;
	public static int kShooterTopCanID = 13;

	public static final int kLeftWinchCanID = 113;
	public static final int kRightWinchCanID = 114;


	public static int kPCMCanID = 50;

	public static int kFrontIntakeEscCanID = 11;

	public static int kFrontIntakeSolenoidChannel = 0;

	public static final int kIndexerACanID = 15;
	public static final int kIndexerBCanID = 16;
	public static final int kFeedACanID = 17;
	public static final int kFeedBCanID = 18;


    public static double kDriveMaxSpeed = 4.18;
	public static double kDriveMaxAccel = 4.0;
	public static double kDriveMaxTurnSpeed = 12.12;
	public static double kDriveMaxTurnAccel = 24.24;
    
	public static final double kVisionTurnKp = 0.038;//was 0.024; // 0.012 for 2 centre nitrile
	public static final double kDriveTurnStictionConstant = 0.0; // FIXME
	public static final double kGyroTurnKp = 0.007; // 0.004 for 2 centre nitrile
	public static final double kGyroDriveTurnKp = 0.012;  // FIXME
	public static final double kEncoderDriveKp = 0.7;  // FIXME
	public static final double kDriveEncoderConversionFactor = 0.000023077; //1; //0.00089291;

	public static final double kShooterP = 0.25;
	public static final double kShooterI = 0.00014;

	public static final double kClimberWinchP = 0.0623;


	
	//Vision
	public static final int KVisionCommandID = 9; //Vision command button id on joystick
	public static final double kA1 = 60; // Angle of the limelight from the robot chassie (degrees)
	public static final double kH1 = 0.65; //Height of the limelight lense (metres)
	public static final double kH2 = 2.61; //height of the middle of the target (metres)
}