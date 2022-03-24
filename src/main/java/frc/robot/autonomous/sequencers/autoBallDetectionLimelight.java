package frc.robot.autonomous.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.SequenceTransition;
import frc.robot.subsystems.Limelight;

/**
 * Detects balls by checking when they cover the limelight while exiting the shooter
 * 
 * <p>Note that this only work if the limelight and shooter are aligned in a way in which the ball fully covers view of the censor for at least 1/22 seconds, and if the robot is properly aligned with the top hub.\
 * @return returns a complete transition when numballs exceeds the specifed number in setNumBalls
 */
public class autoBallDetectionLimelight extends SequenceTransition{
    private boolean assumedBallStatus = false;
    private double numBalls = 0;
    private double maxNumBalls = 0;
    public void setNumBalls(double aNumBalls) {
        maxNumBalls = aNumBalls;
    }

    @Override
    public void transStart() {
        numBalls = 0;
        assumedBallStatus = false;
        SmartDashboard.putNumber("balls shot", numBalls);
        // TODO Auto-generated method stub
    }

 
    @Override
    public boolean transUpdate() {
        boolean sensorStatus = Limelight.getInstance().getTargetAcquired();
            if(sensorStatus != true){
                    assumedBallStatus = true;
            }
            else{
                if(assumedBallStatus == true){
                    ballDetected();
                    assumedBallStatus = false;
                }
            }

        return isTransComplete();
    }

    private void ballDetected()
    {
        numBalls = numBalls + 1;
        SmartDashboard.putNumber("balls shot", numBalls);
    }



    @Override
    public boolean isTransComplete() {
        if (numBalls >= maxNumBalls)
        {
            return true;        
        }
        return false;
    } 

}