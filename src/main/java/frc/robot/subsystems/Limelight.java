package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.Constants;
/**
 * Put docs here // TODO
 */
public class Limelight {
    private static Limelight mInstance;

    public static Limelight getInstance() {
      if (mInstance == null) {
        mInstance = new Limelight();
      }
      return mInstance;
    }

    Limelight() {
      //setPipeline(0);
    }

    double targetOffsetVertical = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
    double angleToGoalDegrees = Constants.kA1 + targetOffsetVertical;
    double angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);
    double distanceFromLimelightToGoalInches = (Constants.kGoalHeightInches - Constants.kLimelightHeightInches) / Math.tan((angleToGoalRadians));


    public boolean getTargetAcquired() {
      //setPipeline(0);
      double targetAcquired = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tv").getDouble(0.0);
      boolean yesorno;
      if(targetAcquired == 1.0){
        yesorno = true;
      }
      else{
        yesorno = false;
      }
      return yesorno; 
    }

    public double getAngleToTarget() {
      //setPipeline(0);
      double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
      return tx;
    }

    public double getErrorY() {
      //setPipeline(0);
      double ty = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
      return ty;
    }

    public void disableVision() {
      setPipeline(0);
    }

    private void setPipeline(int pipelineId) {
      NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").setNumber(pipelineId);
    }

    public double getPipeline() {
      return NetworkTableInstance.getDefault().getTable("limelight").getEntry("pipeline").getDouble(0);
    }

    /**
     * 
     * @return distance in metres to target
     */
    public double getDistanceToTarget() {
      targetOffsetVertical = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ty").getDouble(0.0);
      double angleToGoalDegrees = Constants.kA1 + targetOffsetVertical;
      angleToGoalDegrees = Constants.kA1 + targetOffsetVertical;
      angleToGoalRadians = angleToGoalDegrees * (Math.PI / 180.0);
      //setPipeline(0);
      return (Constants.kGoalHeightInches - Constants.kLimelightHeightInches)/Math.tan(angleToGoalRadians);
    }

    public void enableVision() {
      setPipeline(1);
    }
}

