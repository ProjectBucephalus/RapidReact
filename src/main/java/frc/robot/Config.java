package frc.robot;

import frc.robot.DriverInterface.VersionType;

public class Config {

    //Version number. Update every pull request, release, or major change!
    public static final String version = "1.3";

    //Version type
    public static final VersionType versionType = VersionType.RELEASE;
    
    //1 = normal, -1 = inverted
    public static final double kInvertDir = 1;

    //Motor phases - false = not inverted
    public static final boolean kLeftDrivePhase = false;
    public static final boolean kRightDrivePhase = false;

        //Minimum speed before shooting balls (RPM)
        public static final double kShooterMinSpeed = 100;

        //Shooter idle mode
        public static final boolean kShooterIdleMode = false;

    /*
     * Motor currents:
     * 
     * (In amps)
     * 
     * Drive defult: 40A
     * Shooter defult: 40A
    */

    public static final int kDriveCurrentLimit = 40;

    /* 
     *  Motor modes
     * 
     * Break/Coast
    */


    //Tip threshold , -180 to 180
    public static final double kTipThreshold = 25;
    //-1 to 1
    public static final double kTipCorrectionPower = 0.4;
    
    //Stall threshold, -1, to 1, 0 if to disabled
    public static final double kStallThreshold = 0;
    //-1 to 1
    public static final double kStallCorrectionPower =  0.9;

    //SQRT for gyro
    public static final double kDriveGyroTurnK = 0.06;
    //Degrees
    public static final double kDriveGyroTurnThresh = 3.0;
    //Degrees/second
    public static final double kDriveGyroRateThresh = 3.0;

    //Xbox
    public static final int kXbox1Port = 1;

    //Joystick
    public static final int kJoystick1Port = 0;

    //Use stallSense with normal teleop drive
    public static final boolean kUseStallSenseTeleopDrive = false;

    //Default value for debug output
    public static final boolean kDebugOutputDefault = false;

    //Default value for verbose output
    public static final boolean kVerboseOutputDefault = true;

    //Minimum pressure for Compressor cutoff
    public static final double kCompressorMin = 110;

    //Max pressure for the Compressor cutoff
    public static final double kCompressorMax = 120;

    //Intake speed
    public static final double kIntakeSpeed = 1;

    //Climber stowed position
    public static final double kClimberStowedPos = 0; //in encoder units (2048 EPR)

    //Climber 'Up' position
    public static final double kClimberUpPos = 386791; //in encoder units (2048 EPR)

    //Climber 'Hooked' position
    public static final double kClimberHookedPos = 126726; //in encoder units (2048 EPR)
    //Climber 'Finished' position
    public static final double kClimberFinishedPos = 126726; //in encoder units (2048 EPR)

    //Climber hysteresis
    public static final double kClimberHysteresis = 1000;

    public static final double kLimelightOffsetAmmmount = 50; 
    /**
     * Shooter Modifer for the (limelight)
     * 
     * This value should be set to 1 at a comp. This is for linear tuning, which can easily be done by anyone. For illinear tuning (i.e its off by different amounts at different points go to Adam, don't mess with the equation.)
     * */
    public static final double kLimelightShooterSpeedModiferPercentage = 1;//1.010375;
    public static final double kLimelightTuningOffset = 0;
    
    //y = 3077.128 + (1786 - 3077.128)/(1 + (x/55.07565)^6.788735)

    public static final double kLimelightATerm = 1722.884;
    public static final double kLimelightBTerm = 5.779675;
    public static final double kLimelightCTerm = 603.8564;
    public static final double kLimelightDTerm = 445984300;

    public static final double kLimelightAcceptableAngle =3.5;

}
