/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Config;
import frc.robot.Constants;
import frc.robot.RobotMap;
/**
 * Put docs here // TODO
 */
public class Drive extends Subsystems {
    
    MotorControllerGroup mLeftDrive;
    MotorControllerGroup mRightDrive;
    RelativeEncoder mLeftEnc;
    RelativeEncoder mRightEnc;

    /**
     * Drive motor brake status. Drive brakes are disables upon powering up the robot.
     */
    private boolean myBrakes = false;

    public static Drive driveInstance;

    public static Drive getInstance() {
        if (driveInstance == null) {
            driveInstance = new Drive();
        }
        return driveInstance;
    }

    private Drive() {
    }
    //Drive control

    private double lastLeftSPeed = 0;
    private double lastRightSPeed = 0;
    private double manRampRateFwd = 0.05;
    private double manRampRateRev = 0.5;

    public void setMotors(double leftPower, double rightPower) {
        double leftDiff = leftPower - lastLeftSPeed;
        leftDiff = Math.min(leftDiff, manRampRateFwd);
        leftDiff = Math.max(leftDiff, -manRampRateRev);
        leftPower = lastLeftSPeed + leftDiff;

        double rightDiff = rightPower - lastRightSPeed;
        rightDiff = Math.min(rightDiff, manRampRateFwd);
        rightDiff = Math.max(rightDiff, -manRampRateRev);
        rightPower = lastRightSPeed + rightDiff;

        RobotMap.getLeftDriveA().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);
        RobotMap.getLeftDriveB().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);

        RobotMap.getRightDriveA().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);
        RobotMap.getRightDriveB().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);

        lastLeftSPeed = leftPower;
        lastRightSPeed = rightPower;
    }

    public void arcadeDrive(double throttle, double steering, double power, double microAdjust) {
        //Left
        double leftPower = (power + (steering)) * throttle;
        //Right
        double rightPower = (power - (steering)) * throttle;
        //Write to motors
        setMotors(-leftPower, rightPower);
    }

    public void arcadeDrive(double throttle, double steering, double power) {
        arcadeDrive(throttle, steering, power, 0);
    }

    /**
     * Sets the desired speed and steering for auto.
     * This is called by the sequence steps. This does not update the actual
     * drive motors until {@link #autoUpdate()} is called, so a sequence step
     * can set the speed to zero when exiting and the next sequence step can
     * set the speed to a different value upon starting and the last speed
     * set will be used when the update is called.
     * @param steering The left/right component between -1 and 1.
     * @param power
     */
    public void autoArcadeDrive(double steering, double power)
    {
        // Limit the power and steering commands to within +/- the maximums.
        myAutoPower = Math.max(-AUTO_POWER_LIMIT, Math.min(AUTO_POWER_LIMIT, power));
        myAutoSteer = Math.max(-AUTO_STEER_LIMIT, Math.min(AUTO_STEER_LIMIT, steering));

        // If the power + steering commands will exceed the +/- 1 range, then reduce the power
        // so that the steering command still has full effect.
        if ((myAutoPower + Math.abs(myAutoSteer)) > 1)
        {
            myAutoPower = 1 - Math.abs(myAutoSteer);
        }
        if ((myAutoPower - Math.abs(myAutoSteer)) < -1)
        {
            myAutoPower = -1 + Math.abs(myAutoSteer);
        }
    }

    /**
     * Separates the ramping into a periodically updated method. This keeps the
     * ramp rate constant even when the speed is set multiple times per scan
     * via {@link #autoArcadeDrive(double, double)} and also to allow ramping
     * to zero when the auto sequence sets the speed to zero and moves onto a
     * sequence step that doesn't update the drive speeds any more.
     * This method needs to be called every scan during update, and needs to be
     * called after the sequencer update is run.
     */
    public void autoUpdate()
    {
        // Find out how much the power and steering commands want to change, then limit this
        double steerDiff = myAutoSteer - myLastAutoSteer;
        steerDiff = Math.max(-STEER_RATE_LIMIT, Math.min(STEER_RATE_LIMIT, steerDiff));
        myLastAutoSteer = myLastAutoSteer + steerDiff;
        double powerDiff = myAutoPower - myLastAutoPower;
        powerDiff = Math.max(-ACC_REV_LIMIT, Math.min(ACC_FWD_LIMIT, powerDiff));
        myLastAutoPower = myLastAutoPower + powerDiff;

        // Calculate the individual motor outputs and send to motors.
        double leftPower = (myLastAutoPower + myLastAutoSteer);
        double rightPower = -(myLastAutoPower - myLastAutoSteer);
        RobotMap.getLeftDriveA().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);
        RobotMap.getLeftDriveB().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);
        RobotMap.getRightDriveA().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);
        RobotMap.getRightDriveB().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);

        lastLeftSPeed = leftPower;
        lastRightSPeed = rightPower;
        // SmartDashboard.putNumber("Left Auto Drive", leftPower);
        // SmartDashboard.putNumber("Right Auto Drive", rightPower);
    }


    private double myAutoPower = 0.0;
    private double myAutoSteer = 0.0;
    private double myLastAutoSteer = 0.0;
    private double myLastAutoPower = 0.0;
    private final static double AUTO_STEER_LIMIT = 0.5;
    private final static double AUTO_POWER_LIMIT = 1.0;
    /**
     * The maximum change per scan that the steering command can change.
     * At a 20ms scan rate with this set to 0.1, it will take 100ms
     * for the steering command to change from 0 to 0.5 (the limit)
     */
    private final static double STEER_RATE_LIMIT = 0.1;

    private static double ACC_FWD_LIMIT = 0.1;
    public double getAccFwdLimit()
    {
        return ACC_FWD_LIMIT;
    }
    public void setAccFwdLimit(double anAccFwdLimit)
    {
        ACC_FWD_LIMIT = anAccFwdLimit;
    }

    private static double ACC_REV_LIMIT = 0.2;
    public double getAccRevLimit()
    {
        return ACC_REV_LIMIT;
    }
    public void setAccRevLimit(double anAccRevLimit)
    {
        ACC_REV_LIMIT = anAccRevLimit;
    }

	/**
	 * Returns the average of the two drive encoders.
	 * @return average distance since reset in metres.
	 */
	public double getAvgEncoderDistance() {
		return Constants.kDriveEncoderConversionFactor * (RobotMap.getLeftDriveA().getSelectedSensorPosition() + -RobotMap.getRightDriveA().getSelectedSensorPosition()) / 2;
    }

    public void update() {

    }
    
    public void resetSensors() {
        RobotMap.getLeftDriveA().setSelectedSensorPosition(0.0);
        RobotMap.getRightDriveA().setSelectedSensorPosition(0.0);
    }

    /**
     * Set the motor controllers' brakes on or off.
     * @param brake True to enable brake mode, false to set to coast.
     */ 
	public void setBrakes(boolean brake) {
        if (brake == myBrakes)
        {
            return;
        }
        if (brake) {
            RobotMap.getLeftDriveA().setNeutralMode(NeutralMode.Brake);
            RobotMap.getLeftDriveB().setNeutralMode(NeutralMode.Brake);
            RobotMap.getRightDriveA().setNeutralMode(NeutralMode.Brake);
            RobotMap.getRightDriveB().setNeutralMode(NeutralMode.Brake);
        } else {
            RobotMap.getLeftDriveA().setNeutralMode(NeutralMode.Coast);
            RobotMap.getLeftDriveB().setNeutralMode(NeutralMode.Coast);
            RobotMap.getRightDriveA().setNeutralMode(NeutralMode.Coast);
            RobotMap.getRightDriveB().setNeutralMode(NeutralMode.Coast);
        }
    	myBrakes = brake;        
	}

    /**
     * 
     * @return True if motor controllers are both set to brake.
     */
	public boolean getBrakes() {
		return myBrakes;
	}

    @Override
    public boolean initMechanism() {

        return false;
    }
    @Override
    public diagnosticState getDiagnosticState() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public void initMotorControllers() {
        RobotMap.getLeftDriveA().configFactoryDefault();
        RobotMap.getLeftDriveB().configFactoryDefault();
        RobotMap.getRightDriveA().configFactoryDefault();
        RobotMap.getRightDriveB().configFactoryDefault();

        RobotMap.getLeftDriveA().configOpenloopRamp(0.55);
        RobotMap.getLeftDriveB().configOpenloopRamp(0.55);
        RobotMap.getRightDriveA().configOpenloopRamp(0.55);
        RobotMap.getRightDriveB().configOpenloopRamp(0.55);

    }
    @Override
    public diagnosticState test() {
        
        return null;
    }
    @Override
    public void clearFaults() {
        RobotMap.getLeftDriveA().clearStickyFaults();  
        RobotMap.getLeftDriveB().clearStickyFaults();        
      
        RobotMap.getRightDriveA().clearStickyFaults();  
        RobotMap.getRightDriveB().clearStickyFaults();        
    }

    public void initLogging(DataLog aLog)
    {
 
    }
 
    public void updateLogging(long aTime)
    {
        
    }
}