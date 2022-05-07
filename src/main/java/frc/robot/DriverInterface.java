// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Subsystems.diagnosticState;
/**
 * Put docs here // TODO
 */
public class DriverInterface {

    private static DriverInterface m_instance;

    private final SendableChooser<String> verboseOutputChooser = new SendableChooser<>();

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

    boolean debugOutput = Config.kDebugOutputDefault;
    boolean verboseOutput = Config.kVerboseOutputDefault;

    Joystick joystick1 = new Joystick(Config.kJoystick1Port);
    XboxController xbox1 = new XboxController(Config.kXbox1Port);

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
                    DataLogManager.log("DEBUG: " + message);
                } 
            break;
            case STATUS:
                if(getVerboseOutput()) {
                    DataLogManager.log("STATUS " + message);
                }
            break;
            case SYSTEM:
                DataLogManager.log("SYSTEM " + message);
            break;
            case CAUTION:
               DataLogManager.log("CAUTION " + message);
            break;
            case WARNING:
                DataLogManager.log("WARNING " + message);
            break;
            case CRITICAL:
                DataLogManager.log("⚠  CRITICAL " + message + " ⚠ ");
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

    public double getX() {
        return deadZone(getJoystickAxis(JoystickAxisType.X) * .7);
    }

    public double getY() {
        return deadZone(getJoystickAxis(JoystickAxisType.Y));
    }

    public boolean getManualShootCommand() {
        return xbox1.getRightTriggerAxis() >= 0.5;
    }

    public boolean getFrontIntakeCommand() {
        if(joystick1.getTrigger() || xbox1.getLeftTriggerAxis() >= 0.25) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getFrontIntakeReverse() {
        if(joystick1.getRawButton(12)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean getIndexerManualOverride() {
        return (xbox1.getRightY() > 0.25 || xbox1.getRightY() < -0.25);
    }

    public double getIndexerManual() {
        return -xbox1.getRightY();
    }

    public void update() {
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
    }

    public void initSmartDashboard() {
        CameraServer.startAutomaticCapture(0);
        CameraServer.startAutomaticCapture(1);  
  
        Shuffleboard.update();
        verboseOutputChooser.setDefaultOption("None", "NONE");
        verboseOutputChooser.addOption("Verbose only", "VERBOSE");
        verboseOutputChooser.addOption("Debug only", "DEBUG");
        verboseOutputChooser.addOption("Verbose + Debug", "ALL");
        SmartDashboard.putData("Verbose Output", verboseOutputChooser);

        SmartDashboard.putNumber("Shooter Ratio Numerator", 1);
        SmartDashboard.putNumber("Shooter Ratio Denomonator", 1);
        SmartDashboard.putNumber("Shooter target", 2000);
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

    public diagnosticState getDiagnosticState() {
        return diagnosticState.OK;
    }

    public void displayDiagnosticState() {
        if(getDiagnosticState() == diagnosticState.WARNING) {
            consoleOutput(MessageType.WARNING, "CHANGE BATTERY NOW!!!!!");
        }
    }
}
