package frc.sequencer.jarryd;

import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

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