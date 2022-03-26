package frc.robot.autonomous.sequencers;

import frc.robot.autonomous.SequenceStepIf;
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
