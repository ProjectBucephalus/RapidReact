package frc.robot.autonomous.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.autonomous.SequenceTransition;


/**
 * This code was found to be dodgy, and was depreceated in lieu of using the limelight as a sensor. It uses the proximity sensor to detect balls running through the shooter
 * 
 * @return returns a complete transition when numballs exceeds the specifed number in setNumBalls
 */
@Deprecated
public class autoBallDetectionProximitySensor extends SequenceTransition{
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
        boolean sensorStatus = RobotMap.ballSense.get();
            if(sensorStatus == true){
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