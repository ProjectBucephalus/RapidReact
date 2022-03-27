// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.VisionTrack;
import frc.robot.subsystems.VisionTrack.VisionState;
import frc.robot.DriverInterface.MessageType;
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
  static Climber m_Climber;
  static VisionTrack vision;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */

  @Override
  public void robotInit() {
    DataLogManager.start();
    DataLog log = DataLogManager.getLog();
    DriverStation.startDataLog(log);
    runIntoTelop = false;
    Limelight.getInstance().disableVision();

    Drive.getInstance().setBrakes(false);

    DriverInterface.getInstance().initSmartDashboard();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    Shooter.getInstance().initMotorControllers();
    VisionTrack.getInstance().setDesiredState(VisionState.IDLE);
    Climber.getInstance().initMotorControllers();

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

    BackIntake.getInstance().initLogging(log);
    Climber.getInstance().initLogging(log);
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
    BackIntake.getInstance().updateLogging(logTime);
    Climber.getInstance().updateLogging(logTime);
    Drive.getInstance().updateLogging(logTime);
    FrontIntake.getInstance().updateLogging(logTime);
    Pneumatics.getInstance().updateLogging(logTime);
    Shooter.getInstance().updateLogging(logTime);

    Shuffleboard.update();
 
  }

  private static long currentScanTimestamp = 0;
  public static long getScanTime()
  {
    return currentScanTimestamp;
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
    runIntoTelop = false;
    Drive.getInstance().setBrakes(true);
    Limelight.getInstance().enableVision();       

    Sequence selectedAuto = seqChooser.getSelected();
    DataLogManager.log("Running selected auto - " + selectedAuto.getName());
    Drive.getInstance().setAngle(getFieldAngle(selectedAuto.getStartPos()));
    mySeq = new Sequencer();
    mySeq.setInitialSteps(selectedAuto.getInitialSteps());
    mySeq.setInitialTransitions(selectedAuto.getInitialTransitions());
    mySeq.sequenceStart();




  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    VisionTrack.getInstance().updateShooterSpeedLimelight();
    SmartDashboard.putString("Auto Step", mySeq.getStepName());
    mySeq.update();
    Drive.getInstance().autoUpdate();
    Shooter.getInstance().update();
    BackIntake.getInstance().update();    
    VisionTrack.getInstance().update();
    
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    SmartDashboard.putNumber("Shooter target", 2000);

    Pneumatics.getInstance().setCompressorStatus(true);

    Climber.getInstance().initMotorControllers();
    Limelight.getInstance().enableVision();
    VisionTrack.getInstance().setDesiredState(VisionState.IDLE);
    Climber.getInstance().resetSensors();
    DriverInterface.getInstance().printVersionNumber(Config.versionType, Config.version);
    Drive.getInstance().setBrakes(true);

  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
 

    try {
      System.out.println("Climber " + Climber.getInstance().getClimberCurrentState() + " Vision " + VisionTrack.getInstance().getCurrentState() + " Shooter " + Shooter.getInstance().getCurrentState() + " Intakes " + FrontIntake.getInstance().getCurrentState() + BackIntake.getInstance().getCurrentState());

      DriverInterface.getInstance().displayDiagnosticState();
    DriverInterface.getInstance().update();
    Shooter.getInstance().update();
    Pneumatics.getInstance().update();
    
    TeleopController.getInstance().callTeleopController();
    FrontIntake.getInstance().update();
    BackIntake.getInstance().update();
    Climber.getInstance().update();
    try {
      if(VisionTrack.getInstance().getCurrentState() == VisionState.IDLE){
        Drive.getInstance().update();
        }
      VisionTrack.getInstance().updateShooterSpeedLimelight();
      VisionTrack.getInstance().update();
    } catch(Exception e) {
      DriverInterface.getInstance().consoleOutput(MessageType.CRITICAL, "VISION CRASH " + e);
    }
    } catch (Exception e){
      DriverInterface.getInstance().consoleOutput(MessageType.CRITICAL, "Something went really really wrong " + e + " :) " + "It's not adam's fault though " + "Or Josh's " + "you can blame jarryd though");
    }
    


  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
    Limelight.getInstance().disableVision();
    Drive.getInstance().setBrakes(false);

    VisionTrack.getInstance().setDesiredState(VisionState.IDLE);
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

    BackIntake.getInstance().clearFaults();
    FrontIntake.getInstance().clearFaults();
    Climber.getInstance().clearFaults();
    Drive.getInstance().clearFaults();
    Pneumatics.getInstance().clearFaults();
    Shooter.getInstance().clearFaults();
    DriverInterface.getInstance().clearPDHFaults();

  }

  
  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
  private static double getFieldAngle(int aPosition)
  {
    if (aPosition == 1)
    {
      return -91.5;
    }
      if (aPosition == 2)
    {
      return -46.5;
    }
    if (aPosition == 3)
    {
      return -1.5;
    }
    if (aPosition == 4)
    {
      return 43.5;
    }
    return 0.0;
  }
}
