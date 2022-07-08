// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.*;
import frc.robot.Config;
import frc.robot.RobotMap;

/** 
 * The class responsible for running and managing the back intake.
 */
public class BackIntake extends Subsystems{

    public static enum BackIntakeStates {
        IDLE,
        INTAKING,
        UNINTAKING,
    }

    private BackIntakeStates currentState = BackIntakeStates.IDLE;
    private BackIntakeStates desiredState = BackIntakeStates.IDLE;
    
    // DataLog variables
    private DoubleLogEntry logRearInSpeed;
    private DoubleLogEntry logRearInStatorCurrent;
    private StringLogEntry logRearInCurrentState;
    private StringLogEntry logRearInDesiredState;

    
    public BackIntake() {

    }

    private static BackIntake m_instance;

    public static BackIntake getInstance() {
        if(m_instance == null) {
            m_instance = new BackIntake();
        }

        return m_instance;
    }
    /**
     * Calling this allows the intake to update every tick
     */
    @Override
    public void update() {
        switch(currentState) {
            default:
                RobotMap.getBackIntakeESC().set(ControlMode.PercentOutput, 0);


                currentState = desiredState;
            break;
            case INTAKING: 
                RobotMap.getBackIntakeESC().set(ControlMode.PercentOutput, Config.kIntakeSpeed);


                currentState = desiredState;

            break;
            case UNINTAKING: 

                RobotMap.getBackIntakeESC().set(ControlMode.PercentOutput, Config.kIntakeSpeed * -1);
                currentState = desiredState;

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
     * @return The current state of the intakes
     */
    public BackIntakeStates getCurrentState() {
        return currentState;
    }

    /**
     * @return The desired state of the intakes
     */
    public BackIntakeStates getDesiredState() {
        return desiredState;
    }

    /**
     * @param State to become the intake desired state.
     */
    public void setDesiredState(BackIntakeStates state) {
        desiredState = state;
    }

    @Override
    public void clearFaults() {
        RobotMap.getBackIntakeESC().clearStickyFaults();        
    }

    public double getSpeed()
    {
        return RobotMap.getBackIntakeESC().getSelectedSensorVelocity();
    }

    public double getSupplyCurrent()
    {
        return RobotMap.getBackIntakeESC().getSupplyCurrent();
    }

    public double getStatorCurrent()
    {
        return RobotMap.getBackIntakeESC().getStatorCurrent();
    }

    public void initLogging(DataLog aLog)
    {
        logRearInSpeed = new DoubleLogEntry(aLog, "Rear Intake Speed");
        logRearInStatorCurrent = new DoubleLogEntry(aLog, "Rear Intake Stator Current");
        logRearInCurrentState = new StringLogEntry(aLog, "Rear Intake Current State");
        logRearInDesiredState = new StringLogEntry(aLog, "Rear Intake Desired State");

    }
 
    public void updateLogging(long aTime)
    {
        logRearInSpeed.append(BackIntake.getInstance().getSpeed(), aTime);
        logRearInStatorCurrent.append(BackIntake.getInstance().getStatorCurrent(), aTime);
        logRearInCurrentState.append(getCurrentState().toString(), aTime);
        logRearInDesiredState.append(getDesiredState().toString(), aTime);

    }
 
}
