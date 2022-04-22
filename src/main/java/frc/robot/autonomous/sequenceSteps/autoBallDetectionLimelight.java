package frc.robot.autonomous.sequenceSteps;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.sequencer.SequenceTransition;
import frc.robot.subsystems.Limelight;

/**
 * Detects balls by checking when they cover the limelight while exiting the shooter
 * 
 * <p>Note that this only work if the limelight and shooter 
 * are aligned in a way in which the ball fully covers
 * view of the censor for at least 1/22 seconds, and if the 
 * robot is properly aligned with the top hub.
 * @return returns a complete transition when numballs exceeds the specifed number in setNumBalls
 */
public class autoBallDetectionLimelight extends SequenceTransition{
    private boolean assumedBallStatus = false;
    private double numBalls = 0;
    private double maxNumBalls = 0;
    private int numLoops = 0;
    public void setNumBalls(double aNumBalls) {
        maxNumBalls = aNumBalls;
    }

    @Override
    public void transStart() {
        numBalls = 0;
        numLoops = 0;
        assumedBallStatus = false;
        SmartDashboard.putNumber("balls shot", numBalls);
        // TODO Auto-generated method stub
    }

 
    @Override
    public boolean transUpdate() {
        boolean sensorStatus = Limelight.getInstance().getTargetAcquired();
            if(sensorStatus != true){
                    assumedBallStatus = true;
                    numLoops++;
            }
            else{
                if(assumedBallStatus == true && numLoops >= 3){
                    ballDetected();
                    assumedBallStatus = false;
                    numLoops = 0;
                }
                else if(assumedBallStatus == true || sensorStatus != true){
                    numLoops++;
                }
                else{
                    numLoops++;
                    System.out.println("Something just went wronng . if you are seeing this just throw your joystick or controller away god has left, science is no more and computers are dead.a");
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