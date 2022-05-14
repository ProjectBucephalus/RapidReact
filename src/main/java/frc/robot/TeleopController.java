// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;


import frc.robot.DriverInterface.JoystickAxisType;
import frc.robot.subsystems.*;
import frc.robot.subsystems.FrontIntake.FrontIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;

/**
 * Put docs here // TODO
 */
 public class TeleopController {

    private static Drive m_drive;
    private static FrontIntake m_frontIntake;
    private static Pneumatics m_pneumatics;
    private static Shooter m_shooter;
    private static DriverInterface m_driverInterface;
    private static TeleopController m_instance;

    private TeleopController() {
        m_driverInterface = new DriverInterface();
        m_drive = Drive.getInstance();
      //  m_pneumatics = Pneumatics.getInstance();
      //  m_shooter = Shooter.getInstance();
      //  m_frontIntake = FrontIntake.getInstance();
    }

    public static TeleopController getInstance() {
        if(m_instance == null) {
            m_instance = new TeleopController();
        }
        return m_instance;
    }
    public void callTeleopController() {
        callDrive();

       // if(m_driverInterface.getManualShootCommand()) {
           /// m_shooter.setDesiredState(ShooterState.SHOOTING); {
            //};
      //  } else {
           /// m_shooter.setDesiredState(ShooterState.IDLE);
       // }
/** 
        if(m_driverInterface.getFrontIntakeCommand()) {
            m_frontIntake.setDesiredState(FrontIntakeStates.INTAKING);
        } else if(m_driverInterface.getFrontIntakeReverse()) {
            m_frontIntake.setDesiredState(FrontIntakeStates.UNINTAKING);
        } else {
            m_frontIntake.setDesiredState(FrontIntakeStates.STOWED);
        }

        if(m_shooter.getCurrentState() == ShooterState.SHOOTING) {
            if(m_shooter.getShooterAtSpeed()) {
                m_shooter.setIndexer(.55);
            } else {
                m_shooter.setIndexer(-.26);
            }

        } else if(m_shooter.getCurrentState() == ShooterState.EJECT) {
            m_shooter.setIndexer(.28);
        } else if(m_frontIntake.getCurrentState() == FrontIntakeStates.INTAKING) {
            m_shooter.setIndexer(-0.075);
        } else if(m_frontIntake.getCurrentState() == FrontIntakeStates.UNINTAKING) {
            m_shooter.setIndexer(-0.075);
        } else if(m_driverInterface.getIndexerManualOverride()) {
            if(m_driverInterface.getIndexerManual() < 0) {
                m_shooter.setIndexer(-m_driverInterface.getIndexerManual());
            } else {
                m_shooter.setIndexer(-m_driverInterface.getIndexerManual());
            }
        } else {
            m_shooter.setIndexer(-.075);
        } 
*/
    }

    public void callDrive() {
        m_drive.arcadeDrive(m_driverInterface.getJoystickAxis(JoystickAxisType.THROTTLE), -m_driverInterface.getX(), m_driverInterface.getY());
    }
}
