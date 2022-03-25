package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;
/**
 *  Unintakes for 1 step of a sequence, until the next transition is complete.
 */
public class autoBackUnIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.UNINTAKING);  
        Shooter.getInstance().setFeed(0.5);
        Shooter.getInstance().setIndexer(0);
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
    }

    @Override
    public void stepEnd() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.IDLE);        
        Shooter.getInstance().setFeed(0);
        Shooter.getInstance().setIndexer(0);
        Shooter.getInstance().setDesiredState(ShooterState.SPINUP);
    }

    @Override
    public void stepUpdate() {        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}
