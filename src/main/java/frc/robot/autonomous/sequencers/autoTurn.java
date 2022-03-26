package frc.robot.autonomous.sequencers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.SequenceStepIf;
import frc.robot.autonomous.SequenceTransition;
import frc.robot.subsystems.Drive;
/**
 * I have no idea what this acomplishes and why it isn't intergrated into autodrive, but this needs to happen before autodrive as it keeps it's angle more accurate and 'clean'
 */
public class autoTurn extends SequenceTransition implements SequenceStepIf{

    @Override
    public void stepStart() {
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);        
    }

    @Override
    public void stepUpdate() {
        double angleErr = (myAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        if (angleErr < -180)
        {
            angleErr = angleErr + 360;
        }
        SmartDashboard.putNumber("GT error", angleErr);
        double steerCmd = Math.sqrt(Math.abs(angleErr)) *0.045;
        if (angleErr < 0)
        {
            steerCmd = -steerCmd;
        }
        Drive.getInstance().autoArcadeDrive(steerCmd, 0);    
    }

    @Override
    public String stepName() {
        return "turn - " + myAngle;
    }
    private double myAngle = 0;
    public void setAngle(double anAngle)
    {
        myAngle = anAngle;
    }

    @Override
    public void transStart() {
        
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
        double angleErr = (myAngle - Drive.getInstance().getYaw()) % 360;
        if (angleErr > 180)
        {
            angleErr = angleErr - 360;
        }
        if (Math.abs(angleErr) <5)
        {
            return true;
        }    
        return false;
    }

}
