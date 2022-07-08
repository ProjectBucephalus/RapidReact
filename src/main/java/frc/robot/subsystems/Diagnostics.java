// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.StringLogEntry;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.Config;
import frc.robot.DriverInterface;
import frc.robot.RobotMap;
import frc.robot.DriverInterface.MessageType;

/** Add your docs here. */
public class Diagnostics {

    private static Diagnostics m_instance;

    public Diagnostics getInstance() {
        if(m_instance == null) {
            m_instance = new Diagnostics();
        }
        return m_instance;
    }

    private StringLogEntry pneumaticsFault;
    private PneumaticsError pneumaticsError = PneumaticsError.NONE;

    public enum PneumaticsError {
        NONE,
        COMMS_FAULT,
        VENT_OPEN,
        LOW_PRESSURE

    }

    public enum FalconError {
        NONE,
        COMMS_FAULT,
    }

    public enum PowerDistrubutinError {
        NONE,
        CHANNEL_TRIP,
        LOW_INPUT,
        OVERCURRENT,
        OVERTEMP,

    }



    public PneumaticsError pneumaticsDiagnostics() {
        if(phCanCheck()) {
            return PneumaticsError.COMMS_FAULT;
        } else if(DriverStation.isEnabled()) {
            if(Pneumatics.getInstance().getPressure() <= Config.kLowPressureWarn && Pneumatics.getInstance().getPressure() >= 5) {
                return PneumaticsError.LOW_PRESSURE;
            } else if(Pneumatics.getInstance().getPressure() <= Config.kLowPressureWarn) {
                return PneumaticsError.VENT_OPEN;
            } else {
                return PneumaticsError.NONE;
            }
        } else {
            return PneumaticsError.NONE;
        }

    }

    private boolean phCanCheck() {
        try {
            RobotMap.getCompressor().getAnalogVoltage();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private boolean pdhCanCheck() {
        try {
            RobotMap.getPDH().getModule();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    public void updateDiagnostics() {
        pneumaticsError = pneumaticsDiagnostics();
        switch(pneumaticsError) {
            case COMMS_FAULT:
                DriverInterface.getInstance().consoleOutput(MessageType.CRITICAL, "Pneumatics COMMS fault");
                break;
            case LOW_PRESSURE:
                DriverInterface.getInstance().consoleOutput(MessageType.CAUTION, "Low Pressure");
                break;
            case VENT_OPEN:
                DriverInterface.getInstance().consoleOutput(MessageType.WARNING, "Vent Open");
                break;
            default:
                break;
            
        }
    }

    public void initLogging(DataLog aLog) {
        pneumaticsFault = new StringLogEntry(aLog, "Pneumatics Fault Code");
    }

    public void updateLogging(long aTime) {
        pneumaticsFault.append(pneumaticsError.toString(), aTime);
    }
    
    
}
