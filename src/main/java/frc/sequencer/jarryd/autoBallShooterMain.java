package frc.sequencer.jarryd;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.sequencer.SequenceTransition;

public class autoBallShooterMain extends SequenceTransition{
    private boolean assumedBallStatus = false;
    private double numBalls = 0;
    private double maxNumBalls = 0;
    private double ticks = 0;
    private double ballsgonefor = 0;
    public void setNumBalls(double aNumBalls) {
        maxNumBalls = aNumBalls;
    }
    private final static int waitTime = 15;
    private int waitCounts = waitTime;

    @Override
    public void transStart() {
        numBalls = 0;
        assumedBallStatus = false;
        ticks = 0;
        ballsgonefor = 0;
        SmartDashboard.putNumber("balls shot", numBalls);
        // TODO Auto-generated method stub
    }

 
    @Override
    public boolean transUpdate() {
        boolean sensorStatus = RobotMap.ballSense.get();
        // if (sensorStatus == true)
        // {
        // //ball detected
        //     if (waitCounts <= 0 && assumedBallStatus == false && waitCounts >-15){
        //         waitCounts = waitTime;
        //         assumedBallStatus = true;
        //         ballDetected();
        //     }
        //     else if(waitCounts <= 0 && assumedBallStatus == true && waitCounts >-15){
        //         waitCounts--;
        //     }
        //     else if(assumedBallStatus == true && waitCounts <= -15){
        //         System.out.println("A ball has been detected in the shooter for too long!!! Assume stuck or code error!");
        //     }
        //     else{
        //         waitCounts --;
        //     }
        // }
        // else if(sensorStatus == false){
        //     waitCounts = waitTime;
        //     assumedBallStatus = false;
        // } 
            if(sensorStatus == true){
                ticks++;
                ballsgonefor = 0;
                if(ticks >= 8){
                    assumedBallStatus = true;
                }
            }
            else if(sensorStatus == false){
                ballsgonefor++;
                ticks = 0;
                if(assumedBallStatus = true && ballsgonefor > 5){
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