package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.subsystems.Drive;
/**
 * Just a stop
 */
public class autoStop implements SequenceStepIf{

    @Override
    public void stepStart() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepEnd() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepUpdate() {
        Drive.getInstance().arcadeDrive(0, 0, 0);
        // TODO Auto-generated method stub
        
    }

    @Override
    public String stepName() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
