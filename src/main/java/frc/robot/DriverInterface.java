// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Pneumatics;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionTrack;
import frc.robot.subsystems.Subsystems.diagnosticState;
/**
 * Put docs here // TODO
 */
public class DriverInterface {

    private static DriverInterface m_instance;

    private final SendableChooser<String> verboseOutputChooser = new SendableChooser<>();

    private DoubleLogEntry batteryVoltage;
    private DoubleLogEntry totalCurrent;
    private DoubleLogEntry channel0Current;
    private DoubleLogEntry channel1Current;
    private DoubleLogEntry channel2Current;
    private DoubleLogEntry channel3Current;
    private DoubleLogEntry channel4Current;
    private DoubleLogEntry channel5Current;
    private DoubleLogEntry channel6Current;
    private DoubleLogEntry channel7Current;
    private DoubleLogEntry channel8Current;
    private DoubleLogEntry channel9Current;
    private DoubleLogEntry channel10Current;
    private DoubleLogEntry channel11Current;
    private DoubleLogEntry channel12Current;
    private DoubleLogEntry channel13Current;
    private DoubleLogEntry channel14Current;
    private DoubleLogEntry channel15Current;
    private DoubleLogEntry channel16Current;
    private DoubleLogEntry channel17Current;
    private DoubleLogEntry channel18Current;
    private DoubleLogEntry channel19Current;
    private DoubleLogEntry channel20Current;
    private DoubleLogEntry channel21Current;
    private DoubleLogEntry channel22Current;
    private DoubleLogEntry channel23Current;
    private StringLogEntry consoleErrorOutput;


    public DriverInterface() {
        
    }


    public static DriverInterface getInstance() {
        if(m_instance == null) {
            m_instance = new DriverInterface();
        }
        return m_instance;
    }

    public static enum JoystickAxisType{
        X,
        Y,
        THROTTLE,
        ROTATION,
    }

    public static enum MessageType {
        DEBUG,
        STATUS,
        CAUTION,
        WARNING,
        CRITICAL,
        SYSTEM,
    }

    public static enum VersionType {
        BETA,
        RELEASECANDIDATE,
        RELEASE,
    }

    public static enum RobotFowardDirection {
        FRONT,
        BACK,
    }

    public static enum RumblePattern {
        START,
        STOP,
        PULSE_SHORT,
        PULSE_MEDIUM,
        PULSE_LONG,
        DOUBLE_PULSE_SHORT,
        DOUBLE_PULSE_MEDIUM,
        DOUBLE_PULSE_LONG,
        PULSE_CONSTANT_START,
        PUSE_CONSTANT_STOP,
        NONE,
    }

    RumblePattern rumblePattern = RumblePattern.NONE;
    double leftRumbleIntensity = 0;
    double rightRumbleIntensity = 0;
    int rumbleStep = 0;
    long rumbleCycle = 0;

    diagnosticState robotState = diagnosticState.OK;

    RobotFowardDirection robotFowardDirection = RobotFowardDirection.FRONT;


    boolean debugOutput = Config.kDebugOutputDefault;
    boolean verboseOutput = Config.kVerboseOutputDefault;


    Joystick joystick1 = new Joystick(Config.kJoystick1Port);
    XboxController xbox1 = new XboxController(Config.kXbox1Port);

    private double limelightSpeedOffset = 0;
    private boolean oldButtonState = false;

    boolean climbEnabled = false;

    /**
     * Method to set Xbox controller vibrate/rumble
     * @param leftRumble value from 0 to 1 to set left rumble intensity
     * @param rightRumble value from 0 to 1 to set right rumble intensity
     */
    public void setRumble(double leftRumble, double rightRumble) {
        xbox1.setRumble(RumbleType.kLeftRumble, leftRumble);
        xbox1.setRumble(RumbleType.kRightRumble, rightRumble);

    }

    private double joystickAxisReturn = 0; 

    /**
     * Method for getting Joystick Axis values
     * @param axisType
     * @return axis value
     */
    public double getJoystickAxis(JoystickAxisType axisType) {
        switch (axisType) {
            case X:
                joystickAxisReturn = deadZone(joystick1.getX()) * 0.7;
            break;
            case Y:
                joystickAxisReturn = deadZone(joystick1.getY());
            break;
            case THROTTLE:
                joystickAxisReturn = (-joystick1.getThrottle() + 1)/2;
            break;
            case ROTATION:
                joystickAxisReturn = joystick1.getTwist();
            break;
        }
        return joystickAxisReturn;
    }

    public boolean getDebugOutput() {
        return debugOutput;
    }

    public boolean getVerboseOutput() {
        return verboseOutput;
    }

    public void consoleOutput(MessageType messageType, String message) {
        switch(messageType) {
            case DEBUG:
                if(getDebugOutput()) {
                    System.out.println("DEBUG: " + message);
                } 
            break;
            case STATUS:
                if(getVerboseOutput()) {
                    System.out.println("STATUS " + message);
                }
            break;
            case SYSTEM:
                System.out.println("SYSTEM " + message);
            break;
            case CAUTION:
                System.out.println("CAUTION " + message);
                consoleErrorOutput.append("CAUTION " + message, 0);
            break;
            case WARNING:
                System.out.println("WARNING " + message);
                consoleErrorOutput.append("WARNING " + message, 0);
            break;
            case CRITICAL:
                System.out.println("⚠  CRITICAL " + message + " ⚠ ");
                consoleErrorOutput.append("⚠  CRITICAL " + message + " ⚠ ", 0);
            break;
        }
    }

    public void printVersionNumber(VersionType versonType, String version) {
        switch(versonType) {
            case BETA:
                SmartDashboard.putString("Software Version", "BETA " + version);
                consoleOutput(MessageType.SYSTEM, "Software version: BETA " + version);
            break;
            case RELEASECANDIDATE:
                SmartDashboard.putString("Software Version", "Release Candidate " + version);
                consoleOutput(MessageType.SYSTEM, "Software version: Release Candidate " + version);
            break;
            case RELEASE:
                SmartDashboard.putString("Software Version", "Release " + version);
                consoleOutput(MessageType.SYSTEM, "Software version: Release " + version);
            break;

        }
    }

    public double deadZone(double input) {
        if(input <= 0.07 && input >= -0.05) {
            return 0;        
        }
        return input;
    }

    public RobotFowardDirection getRobotFowardDirection() {
        if(joystick1.getRawButtonPressed(2)) {
            if(robotFowardDirection == RobotFowardDirection.FRONT) {
                if(oldButtonState == false) {
                    robotFowardDirection = RobotFowardDirection.FRONT;
                    oldButtonState = true;
                }
            } else {
                if(oldButtonState == false) {
                    robotFowardDirection = RobotFowardDirection.FRONT;
                    oldButtonState = true;
                }
            }
        } else {
            oldButtonState = false;
        }
        return robotFowardDirection;
    }

    public double getX() {
        return deadZone(getJoystickAxis(JoystickAxisType.X) * .7);
    }

    public double getY() {
        return deadZone(getJoystickAxis(JoystickAxisType.Y));
    }

    public boolean getAutoShootCommand() {
        return joystick1.getTrigger();
    }

    public boolean getManualShootCommand() {
        return xbox1.getRightTriggerAxis() >= 0.5;
    }

    /**
     * @deprecated
     * @return if eject button pusheds
     */
    public boolean getShooterEjectCommand() {
        return false;//xbox1.getRightBumper();
    }

    public boolean getVisionAbort() {
        return(!(joystick1.getX() <= 0.5 && joystick1.getX() >= -0.5) || !(joystick1.getY() <= 0.5 && joystick1.getY() >= -0.5));
    }

    public boolean getFrontIntakeCommand() {

        if(SmartDashboard.getBoolean("Foward direction", true)) {
            if(joystick1.getTrigger() || xbox1.getLeftTriggerAxis() >= 0.25) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getRawButton(4) || xbox1.getLeftBumper()) {
                return true;
            } else {
                return false;
            }
        } 
    }

    public boolean getBackIntakeCommand() {

        if(SmartDashboard.getBoolean("Foward direction", true)) {
            if(joystick1.getRawButton(4) || xbox1.getLeftBumper()) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getTrigger() || xbox1.getLeftTriggerAxis() >= 0.25) {
                return true;
            } else {
                return false;
            }
        } 
    }

    public boolean getClimbResetCommand() {
        return xbox1.getXButton();

    }

    public boolean getClimbSolenoidForward() {
        return joystick1.getRawButton(5);
    }
    public boolean getClimbSolenoidReverse() {
        return joystick1.getRawButton(6);
    }

    public boolean getFrontIntakeReverse() {
        if(getRobotFowardDirection() == RobotFowardDirection.FRONT) {
            if(joystick1.getRawButton(12)) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getRawButton(11)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean getBackIntakeReverse() {
        if(getRobotFowardDirection() == RobotFowardDirection.FRONT) {
            if(joystick1.getRawButton(11)) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getRawButton(12)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public boolean getIndexerManualOverride() {
        return (xbox1.getRightY() > 0.25 || xbox1.getRightY() < -0.25);
    }

    public double getIndexerManual() {
        return -xbox1.getRightY();
    }

    public boolean getLimelightCommand() {
        return joystick1.getRawButton(3);
    }

    public boolean getVisionCommand(){
        return joystick1.getRawButton(9);
    }
    public void update() {
        getShooterReady();
        SmartDashboard.putBoolean("Climb enabled", climbEnabled);
        SmartDashboard.putBoolean("Foward direction", getRobotFowardDirection() == RobotFowardDirection.FRONT);
        try {
            SmartDashboard.putNumber("Pressure", Pneumatics.getInstance().getPressure());
        } catch (Exception e) {
            consoleOutput(MessageType.CRITICAL, "PNEUMATICS FAULT");
        }


        Shuffleboard.update();
        SmartDashboard.updateValues();

        switch(verboseOutputChooser.getSelected()) {
            default:
                verboseOutput = false;
                debugOutput = false;
            break;
            case "DEBUG": 
                verboseOutput = false;
                debugOutput = true;
            break;
            case "VERBOSE":
                verboseOutput = true;
                debugOutput = false;
            break;
            case "ALL":
                verboseOutput = true;
                debugOutput = true;
        }
        updateClimbEnabled();

        updateRumble();
    }

    /**
     * 
     * @return true if climb enabled
     */
    public void updateClimbEnabled() {
        if(joystick1.getRawButton(7)) {
            climbEnabled = true;
        } else if(joystick1.getRawButton(8)) {
            climbEnabled = false;
        } else {
        }
    }

    public boolean getClimbUpCommand() {
        // System.out.println(xbox1.getPOV());
         return (xbox1.getPOV() == 0 && SmartDashboard.getBoolean("Climb enabled", true));
    }

    public boolean getClimbDownCommand() {
         return (xbox1.getPOV() == 180 && SmartDashboard.getBoolean("Climb enabled", true));
    }

    public boolean getClimbFinishedCommand() {
        return false;
    }


    public void setRumblePattern(RumblePattern pattern, double leftIntensity, double rightIntensity) {
        rumblePattern = pattern;
        leftRumbleIntensity = leftIntensity;
        rightRumbleIntensity = rightIntensity;
    }

    public void updateRumble() {
        switch(rumblePattern) {
            default: //catches 'NONE'
                setRumble(0, 0);
            break;
            case START: 
                setRumble(leftRumbleIntensity, rightRumbleIntensity);
            break;
            case STOP: 
                setRumble(0, 0);
            break;
            case PULSE_SHORT:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 15) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                    break;
                }
            break;
            case PULSE_MEDIUM:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 25) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                    break;
                }
            break;
            case PULSE_LONG:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 55) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                    break;
                }
            break;
            case DOUBLE_PULSE_SHORT:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 15) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        rumbleCycle ++;
                        setRumble(0, 0);
                        if(rumbleCycle > 25) {
                            rumbleStep ++;
                        }
                    break;
                    case 3:
                        rumbleCycle ++;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        if(rumbleCycle > 40) {
                            rumbleStep ++;
                        }
                    break;
                    case 4:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                }
            break;
            case DOUBLE_PULSE_MEDIUM:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 25) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        rumbleCycle ++;
                        setRumble(0, 0);
                        if(rumbleCycle > 35) {
                            rumbleStep ++;
                        }
                    break;
                    case 3:
                        rumbleCycle ++;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        if(rumbleCycle > 60) {
                            rumbleStep ++;
                        }
                    break;
                    case 4:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                }
            break;
            case DOUBLE_PULSE_LONG:
                switch(rumbleStep) {
                    case 0: 
                        rumbleCycle = 0;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        rumbleStep ++;
                    break;
                    case 1:
                        rumbleCycle ++;
                        if(rumbleCycle > 60) {
                            rumbleStep ++;
                        }
                    break;
                    case 2:
                        rumbleCycle ++;
                        setRumble(0, 0);
                        if(rumbleCycle > 80) {
                            rumbleStep ++;
                        }
                    break;
                    case 3:
                        rumbleCycle ++;
                        setRumble(leftRumbleIntensity, rightRumbleIntensity);
                        if(rumbleCycle > 140) {
                            rumbleStep ++;
                        }
                    break;
                    case 4:
                        setRumble(0, 0);
                        rumblePattern = RumblePattern.NONE;
                }
            break;
        
        } 
         
    }

    public boolean getClimberManualOverride() {
        return (!(xbox1.getLeftY() <= 0.25 && xbox1.getLeftY() >= -0.25) && SmartDashboard.getBoolean("Climb enabled", false));
    }

    public double getClimberManualOverridePower() {
        return -xbox1.getLeftY();
    }

    //SmartDashboard (shuffleboard) commands

    public void initSmartDashboard() {
        CameraServer.startAutomaticCapture(0);
        CameraServer.startAutomaticCapture(1);  
  
        Shuffleboard.update();
        verboseOutputChooser.setDefaultOption("None", "NONE");
        verboseOutputChooser.addOption("Verbose only", "VERBOSE");
        verboseOutputChooser.addOption("Debug only", "DEBUG");
        verboseOutputChooser.addOption("Verbose + Debug", "ALL");
        SmartDashboard.putData("Verbose Output", verboseOutputChooser);

        SmartDashboard.putBoolean("Climb enabled", climbEnabled);
        SmartDashboard.putNumber("Shooter Ratio Numerator", 1);
        SmartDashboard.putNumber("Shooter Ratio Denomonator", 1);
        SmartDashboard.putBoolean("Foward direction", getRobotFowardDirection() == RobotFowardDirection.FRONT);
        SmartDashboard.putNumber("Shooter target", 2000);
        SmartDashboard.putNumber("Vision TUNING", 5);
        
        SmartDashboard.putNumber("Shooter Offset Multiplier", Config.kLimelightShooterSpeedModiferPercentage);
        SmartDashboard.putNumber("Shooter C Term", Config.kLimelightShooterCTerm);

        try {
            SmartDashboard.putNumber("Pressure", Pneumatics.getInstance().getPressure());
        } catch (Exception e) {
            consoleOutput(MessageType.CRITICAL, "PNEUMATICS FAULT");
        }
    }

    public double getVisionAngleOffset() {
        return SmartDashboard.getNumber("Vision TUNING", 39.75);
    }

    public double getShooterSpeedField() {
        return SmartDashboard.getNumber("Shooter target", Shooter.getInstance().getShooterSetSpeed());
    }

    public void outputShooterRPMField(double rpm) {
        SmartDashboard.putNumber("Shooter RPM", rpm);
    }

    public double getShooterRatioNumeratorField() {
        return SmartDashboard.getNumber("Shooter Ratio Numerator", 1);
    }

    public double getShooterRatioDenomonatorField() {
        return SmartDashboard.getNumber("Shooter Ratio Denomonator", 1);
    } 

    public double getShooterOffsetMultiplier() {
        return SmartDashboard.getNumber("Shooter Offset Multiplier", Config.kLimelightShooterSpeedModiferPercentage);
    }

    public double getShooterCTerm() {
        return SmartDashboard.getNumber("Shooter C Term", Config.kLimelightShooterCTerm);
    }


    //xbox controlller y
    //               x   b
    //                 a
    public double updateLimelightSpeedOffset() {
        if(xbox1.getYButton()) {
            limelightSpeedOffset = limelightSpeedOffset + Config.kLimelightOffsetAmmmount;
        } else if(xbox1.getAButton()) {
            limelightSpeedOffset = limelightSpeedOffset - Config.kLimelightOffsetAmmmount;
        }
        return limelightSpeedOffset;
    }

    public diagnosticState getDiagnosticState() {
        // if(RobotMap.getPDH().getTotalCurrent() <= Constants.kIdleCurrent && RobotMap.getPDH().getVoltage() <= Constants.kIdleVoltageCutoff) {
        //     return diagnosticState.WARNING;
        // }
        // else {
        //     return diagnosticState.OK;
        // }
        return diagnosticState.OK;
    }

    public void displayDiagnosticState() {
        if(getDiagnosticState() == diagnosticState.WARNING) {
            consoleOutput(MessageType.WARNING, "CHANGE BATTERY NOW!!!!!");
            robotState = diagnosticState.WARNING;
        }
    }

    public void clearPDHFaults() {
        RobotMap.getPDH().clearStickyFaults();
    }
    /**
     * Checks if the shooter is locked on and within tollerence of limelight, and at or above acceptable speed. Updates smart dashboard.
     * @return if shooter is ready to fire
     */
    public boolean getShooterReady() {
        SmartDashboard.putBoolean("Shooter Ready", (VisionTrack.getInstance().getLimelightLock() && Shooter.getInstance().getShooterAtSpeed() && (VisionTrack.getInstance().getLimelightDistance() >= 27)));
        if(SmartDashboard.getBoolean("Shooter Ready", false) && Drive.getInstance().getDriveZeroInput()) {

        }
        return SmartDashboard.getBoolean("Shooter Ready", false);
    }

    public void rumble() {
        if(SmartDashboard.getBoolean("Shooter Ready", false) && Drive.getInstance().getDriveZeroInput()) {
            rumblePattern = RumblePattern.PULSE_CONSTANT_START;
        } else {
            rumblePattern = RumblePattern.STOP;
        }
    }

    public void initLogging(DataLog aLog) {

        batteryVoltage = new DoubleLogEntry(aLog, "Battery Voltage");
        totalCurrent = new DoubleLogEntry(aLog, "Total Current");
        channel0Current = new DoubleLogEntry(aLog, "Channel 0 current");
        channel1Current = new DoubleLogEntry(aLog, "Channel 1 current");
        channel2Current = new DoubleLogEntry(aLog, "Channel 2 current");
        channel3Current = new DoubleLogEntry(aLog, "Channel 3 current");
        channel4Current = new DoubleLogEntry(aLog, "Channel 4 current");
        channel5Current = new DoubleLogEntry(aLog, "Channel 5 current");
        channel6Current = new DoubleLogEntry(aLog, "Channel 6 current");
        channel7Current = new DoubleLogEntry(aLog, "Channel 7 current");
        channel8Current = new DoubleLogEntry(aLog, "Channel 8 current");
        channel9Current = new DoubleLogEntry(aLog, "Channel 9 current");
        channel10Current = new DoubleLogEntry(aLog, "Channel 10 current");
        channel11Current = new DoubleLogEntry(aLog, "Channel 11 current");
        channel12Current = new DoubleLogEntry(aLog, "Channel 12 current");
        channel13Current = new DoubleLogEntry(aLog, "Channel 13 current");
        channel14Current = new DoubleLogEntry(aLog, "Channel 14 current");
        channel15Current = new DoubleLogEntry(aLog, "Channel 15 current");
        channel16Current = new DoubleLogEntry(aLog, "Channel 16 current");
        channel17Current = new DoubleLogEntry(aLog, "Channel 17 current");
        channel18Current = new DoubleLogEntry(aLog, "Channel 18 current");
        channel19Current = new DoubleLogEntry(aLog, "Channel 19 current");
        channel20Current = new DoubleLogEntry(aLog, "Channel 20 current");
        channel21Current = new DoubleLogEntry(aLog, "Channel 21 current");
        channel22Current = new DoubleLogEntry(aLog, "Channel 22 current");
        channel23Current = new DoubleLogEntry(aLog, "Channel 23 current");

        consoleErrorOutput = new StringLogEntry(aLog, "Error log");
        consoleErrorOutput.append("Logging start", 0);

    }

    public void updateLogging(long aTime) {
        batteryVoltage.append(RobotMap.getPDH().getVoltage(), aTime);
        totalCurrent.append(RobotMap.getPDH().getTotalCurrent(), aTime);
        channel0Current.append(RobotMap.getPDH().getCurrent(0), aTime);
        channel1Current.append(RobotMap.getPDH().getCurrent(1), aTime);
        channel2Current.append(RobotMap.getPDH().getCurrent(2), aTime);
        channel3Current.append(RobotMap.getPDH().getCurrent(3), aTime);
        channel4Current.append(RobotMap.getPDH().getCurrent(4), aTime);
        channel5Current.append(RobotMap.getPDH().getCurrent(5), aTime);
        channel6Current.append(RobotMap.getPDH().getCurrent(6), aTime);
        channel7Current.append(RobotMap.getPDH().getCurrent(7), aTime);
        channel8Current.append(RobotMap.getPDH().getCurrent(8), aTime);
        channel9Current.append(RobotMap.getPDH().getCurrent(9), aTime);
        channel10Current.append(RobotMap.getPDH().getCurrent(10), aTime);
        channel11Current.append(RobotMap.getPDH().getCurrent(11), aTime);
        channel12Current.append(RobotMap.getPDH().getCurrent(12), aTime);
        channel13Current.append(RobotMap.getPDH().getCurrent(13), aTime);
        channel14Current.append(RobotMap.getPDH().getCurrent(14), aTime);
        channel15Current.append(RobotMap.getPDH().getCurrent(15), aTime);
        channel16Current.append(RobotMap.getPDH().getCurrent(16), aTime);
        channel17Current.append(RobotMap.getPDH().getCurrent(17), aTime);
        channel18Current.append(RobotMap.getPDH().getCurrent(18), aTime);
        channel19Current.append(RobotMap.getPDH().getCurrent(19), aTime);
        channel20Current.append(RobotMap.getPDH().getCurrent(20), aTime);
        channel21Current.append(RobotMap.getPDH().getCurrent(21), aTime);
        channel22Current.append(RobotMap.getPDH().getCurrent(22), aTime);
        channel23Current.append(RobotMap.getPDH().getCurrent(23), aTime);



    }


    


}
