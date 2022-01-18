// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

/** Add your docs here. */
public abstract class Subsystems {

    public static enum diagnosticState{
        OK, //All systems are good
        CAUTION, //Non-critical issue to be noted.
        WARNING, //Higher impact error. Notes somewhat failure in a mechanism
        CRITICAL, //Notes a critical failure, crippling the mechanism
        FAILURE, //Mechanism is unusable/completly failed.
    }

    /**
     * Method run every cycle
     */
    public abstract void update();

    /**
     * Zero all sensors
     */
    public abstract void resetSensors();

    /**
     * Method to do any initilization of mechanisms.
     * @return if completed
     */
    public abstract boolean initMechanism();

    /**
     * Method to return the current diagnostic state of the mechanism, to report errors.
     * @return the state of the mechanism
     */
    public abstract diagnosticState getDiagnosticState(); 

    public abstract void initMotorControllers();

}
