// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.DriverInterface.JoystickAxisType;
import frc.robot.subsystems.*;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.robot.subsystems.Climber.ClimberStates;
import frc.robot.subsystems.FrontIntake.FrontIntakeStates;
import frc.robot.subsystems.Shooter.ShooterSpeedSlot;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.VisionTrack.VisionState;

/** Add your docs here. */
public class TeleopController {

    private static Drive m_drive;
    private static FrontIntake m_frontIntake;
    private static BackIntake m_backIntake;
    private static Pneumatics m_pneumatics;
    private static Shooter m_shooter;
    private static DriverInterface m_driverInterface;
    private static TeleopController m_instance;
    private static Climber m_climber;
    static VisionTrack vision;

    private TeleopController() {
        m_driverInterface = new DriverInterface();
        m_drive = Drive.getInstance();
        m_pneumatics = Pneumatics.getInstance();
        m_shooter = Shooter.getInstance();
        m_frontIntake = FrontIntake.getInstance();
        m_backIntake = BackIntake.getInstance();
        m_climber = Climber.getInstance();

    }

    public static TeleopController getInstance() {
        if(m_instance == null) {
            m_instance = new TeleopController();
        }
        return m_instance;

    }

    public void callTeleopController() {

        //m_shooter.setShooterWheelRatio(m_driverInterface.getShooterRatioNumeratorField(), m_driverInterface.getShooterRatioDenomonatorField());

        if(m_driverInterface.getManualShootCommand()) {
            m_shooter.setDesiredState(ShooterState.SHOOTING); {
            };
        } else if(m_driverInterface.getShooterEjectCommand()) {
            m_shooter.setDesiredState(ShooterState.EJECT);
        } else {
            m_shooter.setDesiredState(ShooterState.IDLE);
        }

        if(m_driverInterface.getFrontIntakeCommand()) {
            m_frontIntake.setDesiredState(FrontIntakeStates.INTAKING);
        } else if(m_driverInterface.getFrontIntakeReverse()) {
            m_frontIntake.setDesiredState(FrontIntakeStates.UNINTAKING);
        } else {
            m_frontIntake.setDesiredState(FrontIntakeStates.STOWED);
        }

        if(m_driverInterface.getBackIntakeCommand()) {
            m_backIntake.setDesiredState(BackIntakeStates.INTAKING);
        } else if(m_driverInterface.getBackIntakeReverse()) {
            m_backIntake.setDesiredState(BackIntakeStates.UNINTAKING);
        } else {
            m_backIntake.setDesiredState(BackIntakeStates.IDLE);
        }

        

        if(m_driverInterface.getClimbUpCommand()) {
            m_climber.setClimberDesiredState(ClimberStates.EXTENDED);
        } else if(m_driverInterface.getClimbDownCommand()) {
            m_climber.setClimberDesiredState(ClimberStates.HOOKED);
        } else if(m_driverInterface.getClimbResetCommand()) {
            m_climber.setClimberDesiredState(ClimberStates.STOWED);
        } else if(m_driverInterface.getClimberManualOverride()) {
            m_climber.setClimberManualSpeed(m_driverInterface.getClimberManualOverridePower());
            m_climber.setClimberDesiredState(ClimberStates.MANUAL);
        } else {
            m_climber.setClimberDesiredState(ClimberStates.IDLE);
        }

        if(m_shooter.getCurrentState() == ShooterState.SHOOTING) {
            if(m_shooter.getShooterAtSpeed()) {
                m_shooter.setIndexer(1);
            } else {
                m_shooter.setIndexer(-1);
            }
            m_shooter.setFeed(1);

        } else if(m_shooter.getCurrentState() == ShooterState.EJECT || m_shooter.getCurrentState() == ShooterState.EJECT) {
            m_shooter.setIndexer(1);
            m_shooter.setFeed(1);

        } else if(m_backIntake.getCurrentState() == BackIntakeStates.INTAKING) {
            m_shooter.setFeed(1);
            m_shooter.setIndexer(-1);
        } else if(m_backIntake.getCurrentState() == BackIntakeStates.UNINTAKING) {
            m_shooter.setFeed(-1);
            m_shooter.setIndexer(-0.5);
        } else if(m_frontIntake.getCurrentState() == FrontIntakeStates.INTAKING) {
            m_shooter.setFeed(1);
            m_shooter.setIndexer(-0.5);

        } else if(m_frontIntake.getCurrentState() == FrontIntakeStates.UNINTAKING) {
            m_shooter.setFeed(-1);
            m_shooter.setIndexer(-1);
        } else if(m_driverInterface.getIndexerManualOverride()) {
            m_shooter.setIndexer(m_driverInterface.getIndexerManual());
            m_shooter.setFeed(0);
        } else if(m_frontIntake.getCurrentState() == FrontIntakeStates.UNINTAKING) {
            m_shooter.setFeed(-1, 1);
            m_shooter.setIndexer(-1);
        } else if(m_backIntake.getCurrentState() == BackIntakeStates.UNINTAKING) {
            m_shooter.setFeed(-1, 1);
            m_shooter.setIndexer(-1);
        } else {
            m_shooter.setFeed(0);
            m_shooter.setIndexer(0);
        } 

        callDrive();
        m_driverInterface.updateLimelightSpeedOffset();
        m_pneumatics.setCompressorStatus(true);

        //Update shooter values
        ///m_shooter.setShooterSpeed(ShooterSpeedSlot.SHOOTING, m_driverInterface.getShooterSpeedField());

    }

    public void callDrive() {
        m_driverInterface.getRobotFowardDirection();
        if(VisionTrack.getInstance().getCurrentState() == VisionState.IDLE){
            if(SmartDashboard.getBoolean("Foward direction", true)) {
                m_drive.arcadeDrive(m_driverInterface.getJoystickAxis(JoystickAxisType.THROTTLE), -m_driverInterface.getX(), m_driverInterface.getY());
            } else {
                m_drive.arcadeDrive(-(m_driverInterface.getJoystickAxis(JoystickAxisType.THROTTLE)), m_driverInterface.getX(), m_driverInterface.getY());
            }
        }
    }


}
