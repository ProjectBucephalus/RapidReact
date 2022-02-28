// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.cscore.VideoSource;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Subsystems.diagnosticState;

/** Add your docs here. */
public class DriverInterface {

    private static DriverInterface m_instance;

    private final SendableChooser<String> verboseOutputChooser = new SendableChooser<>();


    public DriverInterface() {
        
    }

    private double gameTime = System.currentTimeMillis() / 1000;
    private double oldTime = 0;

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

    boolean climbEnabled = true;

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
            break;
            case WARNING:
                System.out.println("WARNING " + message);
            break;
            case CRITICAL:
                System.out.println("⚠  CRITICAL " + message + " ⚠ ");
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
        if(joystick1.getRawButton(2)) {
            if(robotFowardDirection == RobotFowardDirection.FRONT) {
                if(/*oldTime + 0.1 <= gameTime*/true) {
                    robotFowardDirection = RobotFowardDirection.BACK;
                    oldTime = gameTime;
                }
            } else {
                if(/*oldTime + 0.1 <= gameTime*/true) {
                    robotFowardDirection = RobotFowardDirection.FRONT;
                    oldTime = gameTime;
                }
            }
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

    public boolean getShooterEjectCommand() {
        return xbox1.getRightBumper();
    }

    public boolean getFrontIntakeCommand() {

        if(robotFowardDirection == RobotFowardDirection.FRONT) {
            if(joystick1.getTrigger()) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getRawButton(4)) {
                return true;
            } else {
                return false;
            }
        } 
    }

    public boolean getBackIntakeCommand() {

        if(robotFowardDirection == RobotFowardDirection.BACK) {
            if(joystick1.getTrigger()) {
                return true;
            } else {
                return false;
            }
        } else {
            if(joystick1.getRawButton(4)) {
                return true;
            } else {
                return false;
            }
        } 
    }

    public boolean getClimbResetCommand() {
        return xbox1.getXButton();

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
        return xbox1.getRightY();
    }

    public boolean getLimelightCommand() {
        return joystick1.getRawButton(3);
    }

    public boolean getVisionCommand(){
        return joystick1.getRawButton(9);
    }
    public void update() {
        SmartDashboard.putBoolean("Climb enabled", climbEnabled);
        SmartDashboard.putBoolean("Foward direction", getRobotFowardDirection() == RobotFowardDirection.FRONT);

        gameTime = System.currentTimeMillis()/1000;
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
            setRumble(1, 1);
            climbEnabled = true;
        } else if(joystick1.getRawButton(8)) {
            setRumble(1, 1);
            climbEnabled = false;
        } else {
            setRumble(0, 0);
        }
    }

    public boolean getClimbUpCommand() {
        System.out.println(xbox1.getPOV());
        return (xbox1.getPOV() == 0 && SmartDashboard.getBoolean("Climb enabled", true));
    }

    public boolean getClimbDownCommand() {
        return (xbox1.getPOV() == 180 && SmartDashboard.getBoolean("Climb enabled", true));
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
        return !(xbox1.getLeftY() <= 0.25 && xbox1.getLeftY() >= -0.25);
    }

    public double getClimberManualOverridePower() {
        return -xbox1.getLeftY();
    }

    //SmartDashboard (shuffleboard) commands

    public void initSmartDashboard() {
        CameraServer.startAutomaticCapture(0);
        CameraServer.startAutomaticCapture(1);  
  
        Shuffleboard.update();
        SmartDashboard.putNumber("Shooter target", Shooter.getInstance().getShooterSetSpeed());
        verboseOutputChooser.setDefaultOption("None", "NONE");
        verboseOutputChooser.addOption("Verbose only", "VERBOSE");
        verboseOutputChooser.addOption("Debug only", "DEBUG");
        verboseOutputChooser.addOption("Verbose + Debug", "ALL");
        SmartDashboard.putData("Verbose Output", verboseOutputChooser);

        SmartDashboard.putBoolean("Climb enabled", climbEnabled);
        SmartDashboard.putNumber("Shooter Ratio Numerator", 1);
        SmartDashboard.putNumber("Shooter Ratio Denomonator", 1);
        SmartDashboard.putBoolean("Foward direction", getRobotFowardDirection() == RobotFowardDirection.FRONT);

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
        if(RobotMap.getPDH().getTotalCurrent() <= Constants.kIdleCurrent && RobotMap.getPDH().getVoltage() <= Constants.kIdleVoltageCutoff) {
            return diagnosticState.WARNING;
        }
        else {
            return diagnosticState.OK;
        }
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

    


}
