package frc.robot.autonomous.sequenceSteps;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.autonomous.sequencer.SequenceTransition;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.VisionTrack;
import frc.robot.subsystems.VisionTrack.VisionState;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * Automatically returns a transcomplete when the limelight is angled correctly in relation to it's target
 */
public class autoLimelight extends SequenceTransition implements SequenceStepIf {

    int timesLooped = 0;

    @Override
    public void stepStart() {
        VisionTrack.getInstance().setDesiredState(VisionState.AUTOTURN);
        timesLooped = 0;
    }

    @Override
    public void stepEnd() {
    }

    @Override
    public void stepUpdate() {
        VisionTrack.getInstance().setDesiredState(VisionState.AUTOTURN);

    }

    @Override
    public String stepName() {


         
        return "Limelight";
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
        double tx = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0.0);
        if (Math.abs(tx - .15)<=1.5){
        timesLooped++;
        if(timesLooped >= 14){
            return true;
        }
        else{
            
            return false;
        }
        } 
        else{
            SmartDashboard.putNumber("god help us",Math.abs(tx - .15) );
            return false;
        }
    }
}
