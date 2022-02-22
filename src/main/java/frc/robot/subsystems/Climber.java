// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import frc.robot.Config;
import frc.robot.Constants;

import frc.robot.RobotMap;

/** Add your docs here. */
public class Climber extends Subsystems {

    public enum ClimberStates {
        STOWED,
        EXTENDED,
        HOOKED,
    }

    public enum ClimberBarStates {
        LOW,
        MEDIUM,
        HIGH,
        TRAVERSAL,
        STOWED,
    }

    private ClimberStates currentClimberState = ClimberStates.STOWED;
    private ClimberStates desiredClimberState = ClimberStates.STOWED;
    
    private ClimberBarStates currentBarState = ClimberBarStates.STOWED;
    private ClimberBarStates desiredBarState = ClimberBarStates.STOWED;

    private boolean stateFinished = true;
    private byte climberStep = 0;
    private boolean climberDone = false;

    private static Climber m_instance;

    public static Climber getInstance() {
        if(m_instance == null) {
            m_instance = new Climber();
        }
        return m_instance;
    }

    private Climber() {

    }

    @Override
    public void update() {


        switch(currentClimberState) {
            default: //stowed
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberStowedPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberStowedPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() <= Config.kClimberStowedPos + Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() <= Config.kClimberStowedPos + Config.kClimberHysteresis) {
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
                }
            break;
            case EXTENDED:
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberUpPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberUpPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() >= Config.kClimberUpPos - Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() >= Config.kClimberUpPos - Config.kClimberHysteresis) {
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
                }
            break;
            case HOOKED:
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberHookedPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberHookedPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() <= Config.kClimberHookedPos + Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() <= Config.kClimberHookedPos + Config.kClimberHysteresis) {
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
                }
            break;
        } 

        switch(currentBarState) {
            default: //stowed
                currentBarState = desiredBarState;
            break;
            case LOW: 
                climberDone = false;
                switch(climberStep) {
                    case 0:
                        setClimberDesiredState(ClimberStates.EXTENDED);
                        if(stateFinished) {
                            climberStep ++;
                        }
                    break;
                    case 1:
                        setClimberDesiredState(ClimberStates.HOOKED);
                        if(stateFinished) {
                            climberStep ++;
                        }
                    break;
                    case 2:
                        climberDone = true;
                        currentBarState = desiredBarState;
                    break;
                }
            break;
            case MEDIUM:
                climberDone = false;
                switch(climberStep) {
                    case 0:
                        setClimberDesiredState(ClimberStates.EXTENDED);
                        if(stateFinished) {
                            climberStep ++;
                        }
                    break;
                    case 1:
                        setClimberDesiredState(ClimberStates.HOOKED);
                        if(stateFinished) {
                            climberStep ++;
                        }
                    break;
                    case 2:
                        climberDone = true;
                        currentBarState = desiredBarState;
                    break;
                }
            break;
        }
        
    

    }
    @Override
    public void resetSensors() {
        climberStep = 0;

        RobotMap.getLeftWinch().setSelectedSensorPosition(0);
        RobotMap.getRightWinch().setSelectedSensorPosition(0);

    }
    @Override
    public boolean initMechanism() {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public diagnosticState getDiagnosticState() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void initMotorControllers() {

        RobotMap.getLeftWinch().configFactoryDefault();
        RobotMap.getRightWinch().configFactoryDefault();

        RobotMap.getLeftWinch().config_kP(0, Constants.kClimberWinchP);
        RobotMap.getRightWinch().config_kP(0, Constants.kClimberWinchP);

        RobotMap.getLeftWinch().setNeutralMode(NeutralMode.Brake);
        RobotMap.getRightWinch().setNeutralMode(NeutralMode.Brake);



    }
    @Override
    public diagnosticState test() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @return the current state of the climber
     */
    public ClimberStates getClimberCurrentState() {
        return currentClimberState;
    }

    /**
     * 
     * @param desiredState the desired positon of the climber
     */
    public void  setClimberDesiredState(ClimberStates desiredState) {
        desiredClimberState = desiredState;
    }

    /**
     * @return the current state of the climber
     */
    public ClimberBarStates getClimberBarCurrentState() {
        return currentBarState;
    }

    /**
     * 
     * @param desiredState the desired positon of the climber
     */
    public void  setClimberBarDesiredState(ClimberBarStates desiredState) {
        desiredBarState = desiredState;
    }

    /**
     * @return if climber is done
     */
    public boolean getClimberDone() {
        return climberDone;
    }

   

    

}
