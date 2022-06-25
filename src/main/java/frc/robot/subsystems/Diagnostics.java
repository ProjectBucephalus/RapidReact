// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;

/** Add your docs here. */
public class Diagnostics {

    private static Diagnostics m_instance;

    public Diagnostics getInstance() {
        if(m_instance == null) {
            m_instance = new Diagnostics();
        }
        return m_instance;
    }

    public enum PneumaticsError {
        NONE,
        COMMS_FAULT,
        VENT_OPEN,
        LOW_PRESSURE_STARTUP

    }

    // public PneumaticsError pneumaticsDiagnostics() {
    //     if(DriverStation.) {
            
    //     } else {
    //         return PneumaticsError.NONE;
    //     }

    // }

    
}
