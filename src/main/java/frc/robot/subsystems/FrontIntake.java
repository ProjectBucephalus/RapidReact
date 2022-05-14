// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import frc.robot.Config;
import frc.robot.RobotMap;
/**
 * Put docs here // TODO
 */
public class FrontIntake extends Subsystems{

    public static enum FrontIntakeStates {
        STOWED,
        INTAKING,
        UNINTAKING,
    }

    private FrontIntakeStates currentState = FrontIntakeStates.STOWED;
    private FrontIntakeStates desiredState = FrontIntakeStates.STOWED;
    
    private DoubleLogEntry logFrontSpeed;
    private BooleanLogEntry logFrontExtend;

    private static FrontIntake m_instance;

    public static FrontIntake getInstance() {
        if(m_instance == null) {
            m_instance = new FrontIntake();
        }

        return m_instance;
    }

    @Override
    public void update() {

        switch(currentState) {
            default:

                RobotMap.getFrontIntakeESC().set(ControlMode.PercentOutput, 0);
                RobotMap.getFrontIntakeSolenoid().set(Value.kForward);
                currentState = desiredState;

            break;
            case INTAKING: 
                RobotMap.getFrontIntakeESC().set(ControlMode.PercentOutput, Config.kIntakeSpeed);
                RobotMap.getFrontIntakeSolenoid().set(Value.kReverse);
                currentState = desiredState;

            break;
            case UNINTAKING: 

                RobotMap.getFrontIntakeESC().set(ControlMode.PercentOutput, Config.kIntakeSpeed * -1);
                RobotMap.getFrontIntakeSolenoid().set(Value.kReverse);
                currentState = desiredState;

            break;
            case STOWED:
                RobotMap.getFrontIntakeESC().set(ControlMode.PercentOutput, 0);
                RobotMap.getFrontIntakeSolenoid().set(Value.kForward);
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
    public FrontIntakeStates getCurrentState() {
        return currentState;
    }

    /**
     * @param State to become the intake desired state.
     */
    public void setDesiredState(FrontIntakeStates state) {
        desiredState = state;
    }

    @Override
    public void clearFaults() {
   ///// RobotMap.getFrontIntakeESC().clearStickyFaults();
    }

    public void initLogging(DataLog aLog)
    {
        logFrontSpeed = new DoubleLogEntry(aLog, "Front Intake Speed");
        logFrontExtend = new BooleanLogEntry(aLog, "Front Intake Extend");
    }
 
    public void updateLogging(long aTime)
    {
      //////////  logFrontSpeed.append(RobotMap.getFrontIntakeESC().getSelectedSensorVelocity(), aTime);
        logFrontExtend.append(RobotMap.getFrontIntakeSolenoid().get() == Value.kForward , aTime);
        }
 
}
