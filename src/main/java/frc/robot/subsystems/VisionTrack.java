package frc.robot.subsystems;

import frc.robot.Constants;
import frc.robot.Config;
import frc.robot.DriverInterface;
import frc.robot.subsystems.Shooter.ShooterSpeedSlot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj.DriverStation;
/**
 * Put docs here // TODO
 */
public class VisionTrack {
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
      }
      if(newState == true){
        newState = false;
        timesLooped = 0;
      }
        tx = -m_lime.getAngleToTarget();
          // System.out.println("tx: " + tx);
          double visionSteering = (tx * Constants.kVisionTurnKp);
          double driverTuning = DriverInterface.getInstance().getVisionAngleOffset();
          if(tx + driverTuning <2.5 && tx  + driverTuning>-2.5){
            double isn = visionSteering * 2.7;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx + driverTuning <4 && tx  + driverTuning>-4){
            double isn = visionSteering * 1.4;
            m_drive.arcadeDrive(1, isn, 0.0); 
          }
          else{
            m_drive.arcadeDrive(1, visionSteering, 0.0); 
          }
          if(tx  + driverTuning <1.35 && tx  + driverTuning >-1.35){
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
          if(tx <2.5   && tx  >-2.5){
            double isn = AutoVisionSteering * 3.25;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else if(tx <4   && tx  >-4){
            double isn = AutoVisionSteering * 2.175;
            // m_drive.arcadeDrive(0.5, -1, 0.0);
            Drive.getInstance().autoArcadeDrive(isn, 0); 
          }
          else{
            // m_drive.arcadeDrive(0.5, -0.9, 0.0); 
            Drive.getInstance().autoArcadeDrive(AutoVisionSteering, 0);
          }
          if(tx <1.25&& tx  >-1.25){
            if(timesLooped >= 10){
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

    //NEW
    //y = 2759.394 + (1874.137 - 2759.394)/(1 + (x/52.33355)^6.507768)
    //speed = 2759.394 + (1874.137 - 2759.394)/(1 + Math.pow((distance/52.33355), 6.507768));
    // NEW y = 2650.932 + (1874.07 - 2650.932)/(1 + (x/51.17063)^6.958591)
    // y = 2770.681 + (1884.225 - 2770.681)/(1 + (x/53.64364)^6.918515).
    //y = 2808.138 + (1917.8 - 2808.138)/(1 + (x/53.34703)^9.267454)
    //y = 2979.306 + (1888.511 - 2979.306)/(1 + (x/54.77293)^6.861607)
    //y = 3018.697 + (1869.989 - 3018.697)/(1 + (x/54.96491)^5.909357)
    //y = 2864.219 + (1912.245 - 2864.219)/(1 + (x/52.9066)^7.685986)
    //y = 2955.321 + (1897.034 - 2955.321)/(1 + (x/53.46672)^7.484881)
    //y = 2962.245 + (1895.911 - 2962.245)/(1 + (x/53.51383)^7.309899)4
    //y = 2984.966 + (1893.633 - 2984.966)/(1 + (x/54.00045)^6.883124)
    //y = 2958.363 + (1871.747 - 2958.363)/(1 + (x/53.81824)^6.75047)
    
    speed = 2958.363 + (1871.747 - 2958.363)/(1 + Math.pow((distance/53.81824), 6.75047));
    
    speed = speed * Config.kLimelightShooterSpeedModiferPercentage;
    //OLD !!!!
    //y = 2744.632 + (1878.076 - 2744.632)/(1 + (x/52.17749)^6.838287)
    //speed = 2744.632 + (1878.076 - 2744.632)/(1 + Math.pow((distance/52.17749), 6.838287));
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
  double aspeed = 2759.394 + (1874.137 - 2759.394)/(1 + Math.pow((distance/52.33355), 6.507768));
  aspeed = aspeed * Config.kLimelightShooterSpeedModiferPercentage;
  return aspeed;
  }
  catch(Exception e){
    System.out.println("Shooter speed was impacted by a crital hecking error");
    return 2050;
  }
}


}
