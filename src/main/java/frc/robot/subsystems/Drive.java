/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import frc.robot.Config;
import frc.robot.Constants;
import frc.robot.RobotMap;

public class Drive extends Subsystems {
    
    MotorControllerGroup mLeftDrive;
    MotorControllerGroup mRightDrive;
    RelativeEncoder mLeftEnc;
    RelativeEncoder mRightEnc;
    public static AHRS _imu;

    
    ProfiledPIDController profiledDriveController = new ProfiledPIDController(
        Constants.kEncoderDriveKp, 0.0, 0.0,
        new TrapezoidProfile.Constraints(Constants.kDriveMaxSpeed, Constants.kDriveMaxAccel));
    
    ProfiledPIDController profiledTurnController = new ProfiledPIDController(
        Constants.kGyroTurnKp, 0.0, 0.0, 
        new TrapezoidProfile.Constraints(Constants.kDriveMaxTurnSpeed, Constants.kDriveMaxTurnAccel));


    public static Drive driveInstance;
    public static Drive getInstance() {
        if (driveInstance == null) {
            driveInstance = new Drive();
        }
        return driveInstance;
    }
    private Drive() {
        _imu = getImuInstance();

    }
    
    public void setSystem(MotorControllerGroup leftDrive, MotorControllerGroup rightDrive, RelativeEncoder leftEncoder, RelativeEncoder rightEncoder) {
        mLeftDrive = leftDrive;
        mRightDrive = rightDrive;
        mLeftEnc = leftEncoder;
        mRightEnc = rightEncoder;
    }
  

    //Drive control

    private double lastLeftSPeed = 0;
    private double lastRightSPeed = 0;
    private double autoRampRateFwd = 0.02;
    private double autoRampRateRev = 0.02;
    private double manRampRateFwd = 0.05;
    private double manRampRateRev = 0.5;
    static public enum gameMode {
        DISABLED,
        TELEOP,
        AUTO,
        TEST
    };
    private gameMode currentMode = gameMode.DISABLED;
    public void setGameMode(gameMode aMode)
    {
        currentMode = aMode;
    }

    public void setMotors(double leftPower, double rightPower) {
        if (currentMode == gameMode.AUTO)
        {
            double leftDiff = leftPower - lastLeftSPeed;
            leftDiff = Math.min(leftDiff, autoRampRateRev);
            leftDiff = Math.max(leftDiff, -autoRampRateFwd);
            leftPower = lastLeftSPeed + leftDiff;

            double rightDiff = rightPower - lastRightSPeed;
            rightDiff = Math.min(rightDiff, autoRampRateRev);
            rightDiff = Math.max(rightDiff, -autoRampRateFwd);
            rightPower = lastRightSPeed + rightDiff;
        }
        else
        {
            double leftDiff = leftPower - lastLeftSPeed;
            leftDiff = Math.min(leftDiff, manRampRateFwd);
            leftDiff = Math.max(leftDiff, -manRampRateRev);
            leftPower = lastLeftSPeed + leftDiff;

            double rightDiff = rightPower - lastRightSPeed;
            rightDiff = Math.min(rightDiff, manRampRateFwd);
            rightDiff = Math.max(rightDiff, -manRampRateRev);
            rightPower = lastRightSPeed + rightDiff;
        }

       //System.out.println("Mode - " + currentMode);
        //set motors
       // System.out.println("leftpower " + leftPower);
        RobotMap.getLeftDriveA().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);
        RobotMap.getLeftDriveB().set(ControlMode.PercentOutput, leftPower * Config.kInvertDir);


      // System.out.println("rightpower " + rightPower);

        RobotMap.getRightDriveA().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);
        RobotMap.getRightDriveB().set(ControlMode.PercentOutput, rightPower * Config.kInvertDir);


        lastLeftSPeed = leftPower;
        lastRightSPeed = rightPower;
    }

    public void arcadeDrive(double throttle, double steering, double power, double microAdjust) {
       /* double steerDiff = steering - lastSteer;
        steerDiff = Math.min(steerDiff,0.1);
        steerDiff = Math.max(steerDiff,-0.1);
        lastSteer = lastSteer + steerDiff;
        double powderDiff = power - lastPower;
        powderDiff -
        */
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
    private double myAutoPower = 0.0;
    private double myAutoSteer = 0.0;
    private final static double AUTO_STEER_LIMIT = 0.5;
    private final static double AUTO_POWER_LIMIT = 1.0;


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
    * Drives the robot, calculating other settings
    *Stall detection disabled currently
    * @param throttle
    * @param steering
    * @param power
    * @param stallSense
    */
    public void smartDrive(double throttle, double steering, double power, boolean stallSense) {
        double pitch = _imu.getPitch();
        //double stallThreshold = (Config.kStallThreshold);
        double tipThreshold = (Config.kTipThreshold);
        if(pitch > tipThreshold && stallSense == true) {
            setMotors(-Config.kTipCorrectionPower, -Config.kTipCorrectionPower);
            System.out.println("PITCH > THRESHOLD");
        } else if(pitch < -tipThreshold && stallSense == true) {
            setMotors(Config.kTipCorrectionPower, Config.kTipCorrectionPower);
            System.out.println("PITCH < THRESHOLD");
        } else {
            //Normal
              arcadeDrive(throttle, steering, power);
        }
    }
    /*
    private void steerPriority(double left, double right)
    {
        if (left - right > 2)
        {
            left = 1;
            right = -1;
        }
        else if (right - left > 2)
        {
            left = -1;
            right = 1;
        }
        else if (Math.max(right, left) > 1)
        {
            left = left - (Math.max(right,left) - 1);
            right = right - (Math.max(right,left) - 1);
        }
        else if (Math.min(right, left) < -1)
        {
            left = left - (Math.min(right,left) + 1);
            right = right - (Math.min(right,left) + 1);
        }
        SmartDashboard.putNumber("leftPower", left);
        SmartDashboard.putNumber("rightPower", left);
        SmartDashboard.putNumber("SteerLeft", left-right);
        // leftDrive.set(-left);
        // rightDrive.set(right);
        setMotors(left, -right);
    }


*/

   /**
    * @param targetHeading
    * @param maxRate not used
    * @return true when done
    */
    public boolean actionGyroTurn(double targetHeading, int maxRate) {
		double currentRate = _imu.getRate();
		double currentHeading = _imu.getYaw();
        double steering = (targetHeading - currentHeading) * Constants.kGyroTurnKp;
        // profiledTurnController.setConstraints(new TrapezoidProfile.Constraints(maxRate, Constants.kDriveMaxTurnAccel));
        // double steering = profiledTurnController.calculate(currentHeading, targetHeading);
		arcadeDrive(1.0, steering, 0.0);

		return (Math.abs(targetHeading - currentHeading) <= Config.kDriveGyroTurnThresh) && (Math.abs(currentRate) <= Config.kDriveGyroRateThresh);

    }

    /**
	 * Drive at a given distance and gyro heading.
	 * @param speed Maximum speed in m/s
	 * @param targetHeading in degrees.
	 * @param distance in metres.
	 * @return True when driven to given distance, within a threshold. @see getEncoderWithinDistance()
	 */
	public boolean actionSensorDrive(double maxPower, double targetHeading, double distance) {
		double steering = (targetHeading - _imu.getYaw()) * Constants.kGyroDriveTurnKp;
        double power = profiledDriveController.calculate(getAvgEncoderDistance(), distance);
		if (power >= 0 && power > maxPower) {
			power = maxPower;
		} else if (power < 0 && power < -maxPower) {
			power = -maxPower;
		}
        
		arcadeDrive(1.0, steering, power);
		return encoderIsWithinDistance(distance, 0.05);
    }



    public AHRS getImuInstance() {
		if (_imu == null) {
			_imu = new AHRS(SPI.Port.kMXP); // Must be over SPI so the JeVois can communicate through UART Serial.
		}
		return _imu;
    }
    /**
	 * Returns true if the average of the two encoders are within a certain range.
	 * @param distance is metres
	 * @param threshRange +/- in metres
	 * @return Boolean true when within range.
	 */
	public boolean encoderIsWithinDistance(double distance, double threshRange) {
		return (distance - getAvgEncoderDistance()) < threshRange;
	}

	/**
	 * Returns the average of the two drive encoders.
	 * @return average distance since reset in metres.
	 */
	public double getAvgEncoderDistance() {
		return Constants.kDriveEncoderConversionFactor * (RobotMap.getLeftDriveA().getSelectedSensorPosition() + -RobotMap.getRightDriveA().getSelectedSensorPosition()) / 2;
    }

    /**
     * Returns gyro in degrees
     * @return gyro angle in degrees
     */
    public double getYaw() {
        return _imu.getYaw();
    }

    public void update() {

    }
    
    public void resetSensors() {
        RobotMap.getLeftDriveA().setSelectedSensorPosition(0.0);
        RobotMap.getRightDriveA().setSelectedSensorPosition(0.0);
        _imu.reset();
    }

    /**
     * Set the motor controllers' brakes on or off.
     * @param brake True to enable brake mode, false to set to coast.
     */ 
	public void setBrakes(boolean brake) {
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
        
	}

    /**
     * 
     * @return True if motor controllers are both set to brake.
     */
	public boolean getBrakes() {
		return true;
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
        RobotMap.getLeftDriveC().clearStickyFaults();        
      
        RobotMap.getRightDriveA().clearStickyFaults();  
        RobotMap.getRightDriveB().clearStickyFaults();        
        RobotMap.getRightDriveC().clearStickyFaults();
    }
}