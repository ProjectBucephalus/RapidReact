package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.DriverInterface;
import frc.robot.DriverInterface.JoystickAxisType;
import frc.robot.subsystems.Shooter.ShooterSpeedSlot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.DriverStation;
public class VisionTrack {
    private static VisionTrack mInstance;
    private static Limelight m_lime;
    private static Shooter m_Shooter = Shooter.getInstance();
    private VisionState currentState;
    private VisionState desiredState;
    private Joystick stick = new Joystick(0);
    private static Drive m_drive;
    private int timesLooped;
    private double yOffset;
    private boolean newState;
    private double speed;
    private double tx;
    private boolean turnStatus;
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
  // updateShooterSpeedLimelight();
  System.out.println(m_lime.getDistanceToTarget());
  SmartDashboard.putString("state", stateToString());
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
        if (Math.abs(DriverInterface.getInstance().getX()) > 0.5)
        {
          setDesiredState(VisionState.IDLE);
          currentState = desiredState;
          break;
        }
        if (Math.abs(DriverInterface.getInstance().getY()) > 0.5)
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
      }
      if(newState == true){
        newState = false;
        timesLooped = 0;
      }
        tx = -m_lime.getAngleToTarget();
          // System.out.println("tx: " + tx);
          double visionSteering = (tx * Constants.kVisionTurnKp);
          if(tx <4 && tx >-4){
            double isn = visionSteering * 2;
            m_drive.arcadeDrive(1, isn, 0.0); 
          }
          else{
            m_drive.arcadeDrive(1, visionSteering, 0.0); 
          }
          if(tx <4 && tx >-4){
            if(timesLooped >= 15){
            desiredState = VisionState.IDLE;//VisionState.FINDINGSPEED;
            }
            else{
              timesLooped++;
            }
            }
          if(tx == 0.00000){
            if(timesLooped >= 10){
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
      m_lime.enableVision();
      if(stick.getRawButton(Constants.KVisionCommandID) == true){
        timesLooped++;
        if(timesLooped >=50){
        setDesiredState(VisionState.IDLE);
        timesLooped = 0;
        }
      }
      if(newState == true){
        newState = false;
        speed = 0.0;
      }
      m_drive.arcadeDrive(0, 0, 0);
      yOffset = m_lime.getDistanceToTarget();


      //FIXME 
      //A calculation between yOffset,distance and motor speed needs to be implemented once we have a finalized shooter.
      if(speed != 0.0){
      ///setDesiredState(VisionState.SHOOTING);
      }
      double distance = Limelight.getInstance().getDistanceToTarget();
      speed = 19221960 + (1944.031 - 19221960)/(1 + Math.pow((distance/432.44566), 5.125014));
      currentState = desiredState;
      break;
      case SHOOTING: 
      desiredState = VisionState.IDLE;
      currentState = desiredState;
      break;
      case AUTOTURN:
      if(newState == true){
        newState = false;
        turnStatus = false;
        timesLooped = 0;
      }
      tx = m_lime.getAngleToTarget();
        // System.out.println("tx: " + tx);
          double AutoVisionSteering = (tx * Constants.kVisionTurnKp);
          if(tx <4 && tx >-4){
            double isn = AutoVisionSteering * 2;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else{
            // m_drive.arcadeDrive(0.5, -0.9, 0.0); 
            Drive.getInstance().autoArcadeDrive(AutoVisionSteering, 0);
          }
          if(tx <1 && tx >-1){
            if(timesLooped >= 15){
            desiredState = VisionState.IDLE;
            turnStatus = true;
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
      speed = 19221960 + (1944.031 - 19221960)/(1 + Math.pow((distance/432.44566), 5.125014));
    if(true) {
      m_Shooter.setShooterSpeed(ShooterSpeedSlot.SHOOTING, speed);
    }
}
}
