// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.CANifier.GeneralPin;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Config;
import frc.robot.Constants;
import frc.robot.RobotMap;

/**
 * Put docs here // TODO
 */
public class Climber extends Subsystems {

    public enum ClimberStates {
        STOWED,
        EXTENDED,
        HOOKED,
        MANUAL,
        IDLE,
        FINISHED,
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
    private ClimberStates oldClimberState = ClimberStates.STOWED;
    
    private ClimberBarStates currentBarState = ClimberBarStates.STOWED;
    private ClimberBarStates desiredBarState = ClimberBarStates.STOWED;

    private boolean stateFinished = true;
    private byte climberStep = 0;
    private boolean climberDone = false;

    private double climberManualPower = 0;

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

        if(!RobotMap.getClimberSensors().getGeneralInput(GeneralPin.LIMR)) {
            resetSensors();
            // System.out.println("triggered");
        }


        switch(currentClimberState) {
            case STOWED: //stowed
                oldClimberState = ClimberStates.STOWED;
                stateFinished = false;
                if(RobotMap.getClimberSensors().getGeneralInput(GeneralPin.LIMR)) {
                    RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberStowedPos);
                    RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberStowedPos);    
                } else {
                    RobotMap.getLeftWinch().set(ControlMode.PercentOutput, 0);
                    RobotMap.getRightWinch().set(ControlMode.PercentOutput, 0);

                }
                
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
            break;
            case EXTENDED:
                oldClimberState = ClimberStates.EXTENDED;
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberUpPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberUpPos);
            
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
            break;
            case HOOKED:
                oldClimberState = ClimberStates.HOOKED;
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberHookedPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberHookedPos);
            
                    stateFinished = true;
                    currentClimberState = desiredClimberState;
            break;
            case FINISHED:
                oldClimberState = ClimberStates.FINISHED;
                stateFinished = false;
                RobotMap.getLeftWinch().set(ControlMode.Position, Config.kClimberFinishedPos);
                RobotMap.getRightWinch().set(ControlMode.Position, Config.kClimberFinishedPos);
                stateFinished = true;
                currentClimberState = desiredClimberState;

            break;
            case MANUAL:
                oldClimberState = ClimberStates.MANUAL;
                //if(climberManualPower > 0 && RobotMap.getClimberSensors().getGeneralInput(GeneralPin.LIMF)) {
                    RobotMap.getLeftWinch().set(ControlMode.PercentOutput, climberManualPower);
                    RobotMap.getRightWinch().set(ControlMode.PercentOutput, climberManualPower);
                //} else if(climberManualPower < 0 && RobotMap.getClimberSensors().getGeneralInput(GeneralPin.LIMR)) {
                //    RobotMap.getLeftWinch().set(ControlMode.PercentOutput, climberManualPower);
                //    RobotMap.getRightWinch().set(ControlMode.PercentOutput, climberManualPower);
                //} else {
                //    RobotMap.getLeftWinch().set(ControlMode.PercentOutput, 0);
                //    RobotMap.getRightWinch().set(ControlMode.PercentOutput, 0);
                //}
                currentClimberState = desiredClimberState;

            break;
            case IDLE:
                if(oldClimberState == ClimberStates.MANUAL) {
                    RobotMap.getLeftWinch().set(ControlMode.PercentOutput, 0);
                    RobotMap.getRightWinch().set(ControlMode.PercentOutput, 0);
                }
                oldClimberState = ClimberStates.IDLE;
                currentClimberState = desiredClimberState;
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

        RobotMap.getLeftWinch().setInverted(false);
        RobotMap.getRightWinch().setInverted(true);

        RobotMap.getLeftWinch().configClosedloopRamp(0.7);
        RobotMap.getRightWinch().configClosedloopRamp(0.7);

        RobotMap.getClimberSensors().configFactoryDefault();

        setClimberHooks(false);
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

    public void setClimberHooks(boolean state) {
        if(state) {
            RobotMap.getClimberSolenoid().set(Value.kForward);
        } else {
            RobotMap.getClimberSolenoid().set(Value.kReverse);
        }
    }

    public void setClimberManualSpeed(double manualSpeed) {
        climberManualPower = manualSpeed;
    }

    @Override
    public void clearFaults() {
        RobotMap.getLeftWinch().clearStickyFaults();  
        RobotMap.getLeftWinch().clearStickyFaults();        
      
    }

   

    

}
