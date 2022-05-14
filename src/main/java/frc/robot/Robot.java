// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Drive;
import frc.robot.autonomous.CompletedSequences;
import frc.robot.autonomous.sequencer.Sequence;
import frc.robot.autonomous.sequencer.Sequencer;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.DataLogManager;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.util.LinkedList;
import frc.robot.subsystems.*;
import frc.robot.subsystems.Shooter.ShooterState;
// TODO comment this lol
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  Drive drivetrain = Drive.getInstance();
  static DriverInterface m_driverInterface = new DriverInterface();
  static boolean runIntoTelop;
  static Pneumatics m_pneumatics;
  static Shooter m_shooter;
  static Drive m_drive;
  static FrontIntake m_frontIntake;
  static TeleopController m_teleopController;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  @Override
  public void robotInit() {
    DataLogManager.start();
    DataLog log = DataLogManager.getLog();
    DriverStation.startDataLog(log);

    Drive.getInstance().setBrakes(false);

    DriverInterface.getInstance().initSmartDashboard();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    Shooter.getInstance().initMotorControllers();

    //Sequencer
    LinkedList<Sequence> seqList = new LinkedList<Sequence>();
    seqList.addAll(CompletedSequences.getSequences());
    seqChooser = new SendableChooser<Sequence>();
    SmartDashboard.putData("Auto choices", seqChooser);
    boolean first = true;
    for (Sequence s : seqList)
    {
      if (first)
      {
        first = false;
        seqChooser.setDefaultOption(s.getName(), s);
        myDefault = s;
      }
      else
      {
        seqChooser.addOption(s.getName(), s);
      }
    }
    Drive.getInstance().initMotorControllers();

    Drive.getInstance().initLogging(log);
    FrontIntake.getInstance().initLogging(log);
    Pneumatics.getInstance().initLogging(log);
    Shooter.getInstance().initLogging(log);
  }
  
  SendableChooser<Sequence> seqChooser;
  Sequence myDefault = null;
  Sequencer mySeq;
  
  /**
   * This function is called every robot packet no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    long logTime = (long)(Timer.getFPGATimestamp() * 1000000);
    Drive.getInstance().updateLogging(logTime);
    FrontIntake.getInstance().updateLogging(logTime);
    Pneumatics.getInstance().updateLogging(logTime);
    Shooter.getInstance().updateLogging(logTime);

    Shuffleboard.update();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    Pneumatics.getInstance().setCompressorStatus(true);
    Drive.getInstance().setBrakes(true);
    Shooter.getInstance().setDesiredState(ShooterState.IDLE);

    Sequence selectedAuto = seqChooser.getSelected();
    DataLogManager.log("Running selected auto - " + selectedAuto.getName());
    mySeq = new Sequencer();
    mySeq.setInitialSteps(selectedAuto.getInitialSteps());
    mySeq.setInitialTransitions(selectedAuto.getInitialTransitions());
    mySeq.sequenceStart();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    Pneumatics.getInstance().update();

    SmartDashboard.putString("Auto Step", mySeq.getStepName());
    mySeq.update();
    Drive.getInstance().autoUpdate();
    Shooter.getInstance().update();

  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    SmartDashboard.putNumber("Shooter target", 2000);
    Pneumatics.getInstance().setCompressorStatus(true);

    DriverInterface.getInstance().printVersionNumber(Config.versionType, Config.version);
    Drive.getInstance().setBrakes(true);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    DriverInterface.getInstance().displayDiagnosticState();
    DriverInterface.getInstance().update();
    Shooter.getInstance().update();
    Pneumatics.getInstance().update();
    
    TeleopController.getInstance().callTeleopController();
    FrontIntake.getInstance().update();
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Drive.getInstance().setBrakes(false);
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {
    Drive.getInstance().setBrakes(false);

    //Limelight.getInstance().disableVision();
  }

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {
    Drive.getInstance().setBrakes(true);

    FrontIntake.getInstance().clearFaults();
    Drive.getInstance().clearFaults();
    Pneumatics.getInstance().clearFaults();
    Shooter.getInstance().clearFaults();
  }

  
  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
