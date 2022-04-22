package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.autonomous.sequencer.SequenceTransition;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.VisionTrack;
import frc.robot.subsystems.VisionTrack.VisionState;

/**
 * Automatically returns a transcomplete when the limelight is angled correctly in relation to it's target
 */
public class autoLimelight extends SequenceTransition implements SequenceStepIf {

    int timesLooped = 0;

    @Override
    public void stepStart() {
        VisionTrack.getInstance().setDesiredState(VisionState.AUTOTURN);
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
        if (Math.abs(Limelight.getInstance().getAngleToTarget() - .15)<=1.25){
            timesLooped++;
            
            if(timesLooped >= 15){
                return true;                                   
            }
            else{
                return false;
            }
        } 
        else{
            timesLooped = 0;
            return false;
        }
    }
}
