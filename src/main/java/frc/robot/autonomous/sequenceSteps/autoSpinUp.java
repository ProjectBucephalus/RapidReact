package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.autonomous.sequencer.SequenceTransition;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
/**
 * Spins up motor before shooting, as to lower cycle times between robots. No longer necessary due to our robot's framework
 */
@Deprecated
public class autoSpinUp extends SequenceTransition implements SequenceStepIf{

    @Override
    public void stepStart() {
        Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
    }

    @Override
    public void stepEnd() {
        
    }

    @Override
    public void stepUpdate() {
        Shooter.getInstance().update();
    }

    @Override
    public String stepName() {
        return null;
    }
    
    @Override
    public void transStart() {
    }

    @Override
    public boolean transUpdate() {
        return false;
    }

    @Override
    public boolean isTransComplete() {
        return false;
    }
    
}