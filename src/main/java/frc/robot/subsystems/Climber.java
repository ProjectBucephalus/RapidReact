// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motorcontrol.ControlMode;

import frc.robot.Config;
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
    
    private ClimberBarStates currentBarState = ClimberBarStates.LOW;
    private ClimberBarStates desiredBarState = ClimberBarStates.LOW;
    @Override
    public void update() {

        switch(currentClimberState) {
            default: //stowed
                RobotMap.getLeftWinch().set(ControlMode.MotionMagic, Config.kClimberStowedPos);
                RobotMap.getRightWinch().set(ControlMode.MotionMagic, Config.kClimberStowedPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() <= Config.kClimberStowedPos + Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() <= Config.kClimberStowedPos + Config.kClimberHysteresis) {
                    currentClimberState = desiredClimberState;
                }
            break;
            case EXTENDED:
                RobotMap.getLeftWinch().set(ControlMode.MotionMagic, Config.kClimberUpPos);
                RobotMap.getRightWinch().set(ControlMode.MotionMagic, Config.kClimberUpPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() >= Config.kClimberUpPos - Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() >= Config.kClimberUpPos - Config.kClimberHysteresis) {
                    currentClimberState = desiredClimberState;
                }
            break;
            case HOOKED: 
                RobotMap.getLeftWinch().set(ControlMode.MotionMagic, Config.kClimberHookedPos);
                RobotMap.getRightWinch().set(ControlMode.MotionMagic, Config.kClimberHookedPos);
            
                if(RobotMap.getLeftWinch().getSelectedSensorPosition() <= Config.kClimberHookedPos + Config.kClimberHysteresis && RobotMap.getRightWinch().getSelectedSensorPosition() <= Config.kClimberHookedPos + Config.kClimberHysteresis) {
                    currentClimberState = desiredClimberState;
                }
            break;
        } 
        
    

    }
    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
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
        // TODO Auto-generated method stub
        
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



    

}
