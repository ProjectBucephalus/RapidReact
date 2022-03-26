package frc.robot.autonomous.sequencers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.SequenceTransition;
import frc.robot.subsystems.Shooter;
/**
 * Detects balls by checking when they cover the limelight while exiting the shooter
 * 
 * <p>Note that this needs tuning on a robot to robot basis
 *  @return returns a complete transition when numballs exceeds the specifed number in setNumBalls
 */
public class autoBallDetectionMotorRPM extends SequenceTransition{

    @Override
    public void transStart() {
        numBalls = 0;
        SmartDashboard.putNumber("balls shot", numBalls);
        Shooter.getInstance().getShooterRPM();
        Shooter.getInstance().getShooterTargetSpeed(); 
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        if (waitCounts > 0)
        { 
            waitCounts --;
        }
        double currRPM = Shooter.getInstance().getShooterRPM();    
        // compare currRPM with pastRPM[pastLength]
        if ( (currRPM / pastRPM[0]) < 0.87)
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
            pastRPM[ii] = pastRPM[ii+1];
        }
        pastRPM[pastLength-1] = currRPM;
        return isTransComplete();
    }

    private void ballDetected()
    {
        numBalls = numBalls + 1;
        SmartDashboard.putNumber("balls shot", numBalls);
    }

    private double numBalls = 0;
    private double maxNumBalls = 0;
    public void setNumBalls(double aNumBalls) {
        maxNumBalls = aNumBalls;
    }
    private final static int waitTime = 13;
    private int waitCounts = waitTime;


    @Override
    public boolean isTransComplete() {
        if (numBalls >= maxNumBalls)
        {
            return true;        
        }
        return false;
    } 
    

    private final static int pastLength = 5;
    private double[] pastRPM = new double[pastLength];//do this for acceleration
}

