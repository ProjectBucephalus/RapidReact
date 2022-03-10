// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import java.util.HashMap;
import java.util.Map;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.DriverInterface;
import frc.robot.RobotMap;

/** Add your docs here. */
public class Shooter extends Subsystems{

    private static Shooter m_instance;
    public boolean shooterAtSpeed = false;
    Map<Double, Double> speedTable = new HashMap<>();

    public enum ShooterSpeedSlot {
        IDLE, //Shooter in idle state
        SHOOTING, //shooter shooting ball
        EJECT, //shooter ejecting wrong ball colour
        VISION,
        SPINUP, //to be constantly run during auto
    }

    public enum ShooterState {
        IDLE, //Shooter in idle state
        SHOOTING, //shooter shooting ball
        EJECT, //shooter ejecting wrong ball colour
        VISION,
        SPINUP, //to be constantly run during auto
    }


    private static ShooterState currentState = ShooterState.IDLE;
    private static ShooterState desiredState = ShooterState.IDLE;

    private static ShooterSpeedSlot speedSlot = ShooterSpeedSlot.IDLE;

    private double shooterIdleSpeed = 1500;
    private double shooterShootSpeed = 2450;
    private double shooterEjectSpeed = 500;
    private double shooterSpinUpSpeed = shooterShootSpeed;

   

    private double ratio = 1;
    private double wheelRatio = 2;


    private void ballDetected()
    {
        numBalls = numBalls + 1;
        SmartDashboard.putNumber("balls shot", numBalls);
    }

    private double numBalls = 0;
    private final static int waitTime = 10;
    private int waitCounts = waitTime;

    private final static int pastLength = 5;
    private double[] pastRPM = new double[pastLength];

    @Override
    public void update() {
        if (waitCounts > 0)
        { 
            waitCounts --;
        }
        double currRPM = Shooter.getInstance().getShooterRPM();    
        // compare currRPM with pastRPM[pastLength]
        if ( (currRPM / pastRPM[0]) < 0.95)
        {
            //ball detected
            if (waitCounts == 0)
            {
                waitCounts = waitTime;
                ballDetected();
            }
        }
        for ( int ii = 0 ; ii < pastLength - 1 ; ii++)
        {
            pastRPM[ii] = pastRPM[ii++];
        }
        pastRPM[pastLength-1] = currRPM;



        if(DriverInterface.getInstance().getManualShootCommand()) {
            shooterAtSpeed = false;
        }
        if((RobotMap.getShooterBottom().getSelectedSensorVelocity() / 2048 * 1200) >= getShooterSetSpeed() - getShooterSetSpeed()*0.05985) {
            shooterAtSpeed = true;
        }

        // VisionTrack.getInstance().updateShooterSpeedLimelight();

        DriverInterface.getInstance().outputShooterRPMField(RobotMap.getShooterBottom().getSelectedSensorVelocity() / 2048 * 1200);

        switch(currentState) {
            default: //catches 'IDLE'
                stopShooter();
                currentState = desiredState;
            break;
            case SHOOTING:
                setShooterSpeedSlot(ShooterSpeedSlot.SHOOTING);
                shooterPID();
                currentState = desiredState;
            break;
            case EJECT:
                setShooterSpeedSlot(ShooterSpeedSlot.EJECT);
                shooterPID();
                currentState = desiredState;
            break;
            case SPINUP:
                setShooterSpeedSlot(ShooterSpeedSlot.SHOOTING);
                shooterPID();
                currentState = desiredState;
        }

    }

    @Override
    public void resetSensors() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean initMechanism() {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public diagnosticState getDiagnosticState() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void initMotorControllers() {
        

        RobotMap.getShooterBottom().configFactoryDefault();
        RobotMap.getShooterTop ().configFactoryDefault();

        RobotMap.getIndexerA().configFactoryDefault();
        RobotMap.getFeedA().configFactoryDefault();
        RobotMap.getFeedB().configFactoryDefault();

        RobotMap.getIndexerA().setInverted(true);;
        RobotMap.getFeedA().setInverted(true);
        RobotMap.getFeedB().setInverted(true);

        RobotMap.getShooterBottom().config_kP(0, Constants.kShooterP);       
        RobotMap.getShooterTop().config_kP(0, Constants.kShooterP);       

        RobotMap.getShooterBottom().config_kI(0, Constants.kShooterI);       
        RobotMap.getShooterTop().config_kI(0, Constants.kShooterI);     

        RobotMap.getShooterBottom().setInverted(true);
        RobotMap.getShooterTop().setInverted(true);

        RobotMap.getShooterBottom().configPeakOutputReverse(0);
        RobotMap.getShooterTop().configPeakOutputReverse(0);




    }

    public void setShooterSpeed(ShooterSpeedSlot speedSlot, double rpm) {
        switch(speedSlot) {
            case IDLE:
                shooterIdleSpeed = rpm;
            break;
            case SHOOTING:
                shooterShootSpeed = rpm;
            break;
            case EJECT:
                shooterEjectSpeed = rpm;
            break;
            case VISION:
            break;
            case SPINUP:
                shooterSpinUpSpeed = rpm;
            break;
        }
    }

    /**
     * Set the speed ratio between the top and bottom wheels
     * @param topRatio numerator
     * @param bottomRatio denomonator
     * @param topWheel numerator
     * @param bottomWheel denomonator
     */
    public void setShooterWheelRatio(double topRatio, double bottomRatio, double topWheel, double bottomWheel) {
        ratio = topRatio / bottomRatio;
        if(!(topWheel == -1 || bottomWheel == -1)) {
            wheelRatio = topWheel/bottomWheel;
        }
    }

    /**
     * 
     * @param topRatio numerator
     * @param bottomRatio denomonator
     */
    public void setShooterWheelRatio(double topRatio, double bottomRatio) {
        setShooterWheelRatio(topRatio, bottomRatio, -1, -1);
    }

    /**
     * Returns the set speed of the shooter at the selected speed slot. If not specified, defaults to shooting speed.
     * @param speedSlot which speed value to return
     * @return shooter set speed in RPM
     */
    public double getShooterSetSpeed(ShooterSpeedSlot speedSlot) {
        switch(speedSlot) {
            case IDLE:
                return shooterIdleSpeed;
            default:
                return shooterShootSpeed;
            case EJECT:
                return shooterEjectSpeed;
            case SPINUP:
                return shooterSpinUpSpeed;
        }
    }

    /**
     * Returns the set speed of the shooter at the selected speed slot. If not specified, defaults to shooting speed.
     * @return shooter set speed in RPM
     */
    public double getShooterSetSpeed() {
        return getShooterSetSpeed(ShooterSpeedSlot.SHOOTING);
    }

    private void setShooterSpeedSlot(ShooterSpeedSlot slot) {
        switch(slot) {
            case SHOOTING: 
                speedSlot = ShooterSpeedSlot.SHOOTING;
            break;
            case IDLE: 
                speedSlot = ShooterSpeedSlot.IDLE;
            break;
            case EJECT:
                speedSlot = ShooterSpeedSlot.EJECT;
            break;
            case VISION:
            
            break;
            case SPINUP:
                speedSlot = ShooterSpeedSlot.SPINUP;
            break;
        }
    }

    private void shooterPID() {
        
            RobotMap.getShooterBottom().set(ControlMode.Velocity, (getShooterSetSpeed(speedSlot) / 1200 * 2048));
            RobotMap.getShooterTop().set(ControlMode.Velocity, (getShooterSetSpeed(speedSlot) * ratio * wheelRatio / 1200 * 2048));
        

        
    }

    public double getShooterRPM() {
        return RobotMap.getShooterTop().getSelectedSensorVelocity() * 600 / 2048;
    }

    public double getShooterTargetSpeed() {
        return getShooterSetSpeed();
    }

    @Override
    public diagnosticState test() {
        // TODO Auto-generated method stub
        return null;
    }

    public static Shooter getInstance() {
        if(m_instance == null) {
            m_instance = new Shooter();
        }
        return m_instance;
    }

    public void setDesiredState(ShooterState state) {
        desiredState = state;
    }

    public ShooterState getCurrentState() {
        return currentState;
    }

    public void stopShooter() {
        shooterPID();
    }

    public void setIndexer(double speed) {
        RobotMap.getIndexerA().set(ControlMode.PercentOutput, speed);
        
    }

    public void setFeed(double aSpeed, double bSpeed) {
        RobotMap.getFeedA().set(ControlMode.PercentOutput, aSpeed);
        RobotMap.getFeedB().set(ControlMode.PercentOutput, bSpeed);

    }

    @Override
    public void clearFaults() {
        RobotMap.getShooterBottom().clearStickyFaults();
        RobotMap.getShooterTop().clearStickyFaults();

        RobotMap.getFeedA().clearStickyFaults();
        RobotMap.getFeedB().clearStickyFaults();
        RobotMap.getIndexerA().clearStickyFaults();

    }

    public boolean getShooterAtSpeed() {
        System.out.println(RobotMap.getShooterBottom().getSelectedSensorVelocity()/2048*1200);
        if((RobotMap.getShooterBottom().getSelectedSensorVelocity() / 2048 * 1200) >= getShooterSetSpeed() - getShooterSetSpeed()*0.1) {
            if(currentState == ShooterState.SPINUP) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
}

    public void setFeed(double speed) {
        setFeed(speed, speed);
    }
   
}
