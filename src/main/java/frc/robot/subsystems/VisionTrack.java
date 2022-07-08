package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Config;
import frc.robot.DriverInterface;
import frc.robot.subsystems.Shooter.ShooterSpeedSlot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.util.datalog.BooleanLogEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.DriverStation;
/**
 * Put docs here // TODO
 */
public class VisionTrack extends Subsystems{
    private static VisionTrack mInstance;
    private static Limelight m_lime;
    private static Shooter m_Shooter = Shooter.getInstance();
    private VisionState currentState;
    private VisionState desiredState;
    private Joystick stick = new Joystick(0);
    private static Drive m_drive;
    private int timesLooped;
    private boolean newState;
    private double speed;
    private double tx;
    private boolean turnStatus;   

    private BooleanLogEntry targetLocked;
    private DoubleLogEntry limelightPipeline;
    private DoubleLogEntry limelightErrorX;
    private DoubleLogEntry limelightErrorY;
    private DoubleLogEntry limelightDistanceToTarget;

    public static VisionTrack getInstance() {
        if (mInstance == null) {
          mInstance = new VisionTrack();
        }
        return mInstance;
      }

    private VisionTrack(){
        m_lime = new Limelight();
        m_drive = Drive.getInstance();
        currentState = VisionState.IDLE;
        desiredState = VisionState.IDLE;
    }
public void update(){
  //updateShooterSpeedLimelight();
  // System.out.println(m_lime.getDistanceToTarget());
  SmartDashboard.putString("state", stateToString());
  SmartDashboard.putNumber("Distance to target", m_lime.getDistanceToTarget());

  switch(desiredState){
      case IDLE:
      if(newState == true &&DriverStation.isTeleop() ){
        //m_Shooter.setDesiredState(ShooterState.IDLE);
      }
      if(stick.getRawButton(Constants.KVisionCommandID) == true){
        if(m_lime.getAngleToTarget() != 0.0){
          timesLooped++;
          if(timesLooped >=10){
            timesLooped = 0;
          setDesiredState(VisionState.TURNING);
          }
        }
      }

      currentState = desiredState;
      break;
      case TURNING:
        if (Math.abs(DriverInterface.getInstance().getX()) > 0.35)
        {
          setDesiredState(VisionState.IDLE);
          currentState = desiredState;
          break;
        }
        if (Math.abs(DriverInterface.getInstance().getY()) > 0.35)
        {
          setDesiredState(VisionState.IDLE);
          currentState = desiredState;
          break;
        }
      m_lime.enableVision();
      if(stick.getRawButton(Constants.KVisionCommandID) == true){
        m_lime.enableVision();

        timesLooped++;
        if(timesLooped >=50){
          timesLooped = 0;
          setDesiredState(VisionState.IDLE);
        } 
        else{
          timesLooped = 0;
        }
      }
      if(newState == true){
        newState = false;
      }
        tx = -m_lime.getAngleToTarget();
          // System.out.println("tx: " + tx);
          double visionSteering = (tx * Constants.kVisionTurnKp);
          double driverTuning = DriverInterface.getInstance().getVisionAngleOffset();
          if(tx + driverTuning - Config.kLimelightTuningOffset <.75 && tx  + driverTuning - Config.kLimelightTuningOffset> -.75){
            double isn = visionSteering * 13.3;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx + driverTuning- Config.kLimelightTuningOffset <1.5 && tx  + driverTuning- Config.kLimelightTuningOffset>-1.5){
            double isn = visionSteering * 4.6;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx + driverTuning- Config.kLimelightTuningOffset<2.5 && tx  + driverTuning- Config.kLimelightTuningOffset>-2.5){
            double isn = visionSteering * 3.5;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx + driverTuning - Config.kLimelightTuningOffset<4 && tx  + driverTuning- Config.kLimelightTuningOffset>-4){
            double isn = visionSteering * 1.5;
            m_drive.arcadeDrive(1, isn, 0.0); 
          }
          else{
            m_drive.arcadeDrive(1, visionSteering, 0.0); 
          }
          if(tx  + driverTuning - Config.kLimelightTuningOffset<.15 && tx  + driverTuning - Config.kLimelightTuningOffset>-.15){
            if(timesLooped >= 40){
            desiredState = VisionState.IDLE;//VisionState.FINDINGSPEED;
            }
            else{
              timesLooped = 0;
            }
            }
          if(tx == 0.00000){
            if(timesLooped >= 15){
              desiredState = VisionState.IDLE;
            }
            else{ 
              timesLooped++;
            }
          }
     currentState = desiredState;
      break;
      case FINDINGSPEED:
      //currentState = VisionState.IDLE;
      // m_lime.enableVision();
      // if(stick.getRawButton(Constants.KVisionCommandID) == true){
      //   timesLooped++;
      //   if(timesLooped >=50){
      //   setDesiredState(VisionState.IDLE);
      //   timesLooped = 0;
      //   }
      // }
      // if(newState == true){
      //   newState = false;
      //   speed = 0.0;
      // }
      // m_drive.arcadeDrive(0, 0, 0);


      //FIXME 
      //A calculation between yOffset,distance and motor speed needs to be implemented once we have a finalized shooter.
      // if(speed != 0.0){
      ///setDesiredState(VisionState.SHOOTING);
      // }
      // double distance = Limelight.getInstance().getDistanceToTarget();
      // speed = 19221960 + (1944.031 - 19221960)/(1 + Math.pow((distance/432.44566), 5.125014));
      // currentState = desiredState;
      break;
      case SHOOTING: 
      desiredState = VisionState.IDLE;
      currentState = desiredState;
      break;
      case AUTOTURN:
      if(newState == true){
        newState = false;
        turnStatus = false;
      }
      tx = m_lime.getAngleToTarget();
        // System.out.println("tx: " + tx);
          double AutoVisionSteering = (tx * Constants.kVisionTurnKp);
          if(tx - Config.kLimelightTuningOffset <1.3   && tx - Config.kLimelightTuningOffset >=0){
            double isn = 0 + AutoVisionSteering * 7.1;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx - Config.kLimelightTuningOffset <0   && tx - Config.kLimelightTuningOffset >-1.3){
            double isn = 0 + AutoVisionSteering * 7.1;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx- Config.kLimelightTuningOffset <2.7   && tx- Config.kLimelightTuningOffset  >-2.7){
            double isn = AutoVisionSteering * 4.2;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx- Config.kLimelightTuningOffset <4   && tx- Config.kLimelightTuningOffset  >-4){
            double isn = AutoVisionSteering * 2.375;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else{
            // m_drive.arcadeDrive(0.5, -0.9, 0.0); 
            Drive.getInstance().autoArcadeDrive(AutoVisionSteering, 0);
          }
          if(tx - Config.kLimelightTuningOffset<1&& tx- Config.kLimelightTuningOffset  >-1){
            if(timesLooped >= 10){
              timesLooped = 0;
            desiredState = VisionState.IDLE;
            }
            else{
              timesLooped++;
            }
          }
      currentState = desiredState;
      break;
      default:
      setDesiredState(VisionState.IDLE);

    
  }
}



  

    public void turnToTarget(){

    }
    public enum VisionState {
        IDLE,
        TURNING,
        FINDINGSPEED,
        SHOOTING,
        AUTOTURN,
  }

  public void setDesiredState(VisionState state) {
    newState = true;
    desiredState = state;
}

  public boolean turnComplete(){
    return turnStatus;
  }


  public VisionState getCurrentState() {
    return currentState;
  }
  public String stateToString(){
    switch(getCurrentState()){
      case IDLE:
      return "idle";
      case TURNING:
      return "turning";
      case FINDINGSPEED:
      return "finding speed";
      case SHOOTING:
      return "shooting";
      default:
      return "default";
    }
  }
  
  public void updateShooterSpeedLimelight() {
    double distance = Limelight.getInstance().getDistanceToTarget();

    //y = 3046.325 + (1852.595 - 3046.325)/(1 + (x/55.59515)^7.511479)
    //y = 3077.128 + (1786 - 3077.128)/(1 + (x/55.07565)^6.788735)
    speed = Config.kLimelightDTerm + (Config.kLimelightATerm - Config.kLimelightDTerm)/(1 + Math.pow((distance/Config.kLimelightCTerm),Config.kLimelightBTerm));
    speed = speed * Config.kLimelightShooterSpeedModiferPercentage;
    if(true) {
      m_Shooter.setShooterSpeed(ShooterSpeedSlot.SHOOTING, speed);
      if(Limelight.getInstance().getTargetAcquired()){
      m_Shooter.setShooterSpeed(ShooterSpeedSlot.IDLE, speed);
      System.out.println("Idle was impacted by limelight speed :)");
      }
    }
    
}
public double returnShooterSpeedLimelight(){
  try{      
  double distance = Limelight.getInstance().getDistanceToTarget();
  double aspeed = Config.kLimelightDTerm + (Config.kLimelightATerm - Config.kLimelightDTerm)/(1 + Math.pow((distance/Config.kLimelightCTerm),Config.kLimelightBTerm));
  aspeed = (aspeed * DriverInterface.getInstance().getShooterOffsetMultiplier()) + DriverInterface.getInstance().getShooterCTerm();
  return aspeed;
  }
  catch(Exception e){
    System.out.println("Shooter speed was impacted by a crital hecking error");
    return 2050;
  }
}

public boolean getLimelightLock() {
  return (m_lime.getTargetAcquired() && (Math.abs(m_lime.getAngleToTarget()) <= Config.kLimelightAcceptableAngle));
}

public double getLimelightDistance() {
  return m_lime.getDistanceToTarget();
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

@Override
public void clearFaults() {
  // TODO Auto-generated method stub
  
}

@Override
public void initLogging(DataLog aLog) {
  targetLocked = new BooleanLogEntry(aLog, "Limelight target lock");
  limelightPipeline = new DoubleLogEntry(aLog, "Limelight Pipeline ID");
  limelightErrorX = new DoubleLogEntry(aLog, "Limelight X Error");
  limelightErrorY = new DoubleLogEntry(aLog, "Limelight Y Error");
  limelightDistanceToTarget = new DoubleLogEntry(aLog, "Limelight distance to target");

}

@Override
public void updateLogging(long aTime) {
  targetLocked.append(m_lime.getTargetAcquired(), aTime);
  limelightPipeline.append(m_lime.getPipeline(), aTime);
  limelightErrorX.append(m_lime.getAngleToTarget(), aTime);
  limelightErrorY.append(m_lime.getErrorY(), aTime);  
  limelightDistanceToTarget.append(m_lime.getDistanceToTarget(), aTime);
}
}