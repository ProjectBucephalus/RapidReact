package frc.robot;

import frc.robot.DriverInterface.VersionType;

public class Config {

    //Version number. Update every pull request, release, or major change!
    public static final String version = "1.0.0";

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
    public static final double kCompressorMin = 100;

    //Max pressure for the Compressor cutoff
    public static final double kCompressorMax = 120;

    //Intake speed
    public static final double kIntakeSpeed = 1;

    //Climber stowed position
    public static final double kClimberStowedPos = 0; //in encoder units (2048 EPR)

    //Climber 'Up' position
    public static final double kClimberUpPos = 215000; //in encoder units (2048 EPR)

    //Climber 'Hooked' position
    public static final double kClimberHookedPos = -5000; //in encoder units (2048 EPR)

    //Climber 'Finished' position
    public static final double kClimberFinishedPos = 100000; //in encoder units (2048 EPR)

    //Climber hysteresis
    public static final double kClimberHysteresis = 1000;

    public static final double kLimelightOffsetAmmmount = 50; 


        
}
