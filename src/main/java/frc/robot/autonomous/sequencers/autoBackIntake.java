package frc.robot.autonomous.sequencers;

import frc.robot.autonomous.SequenceStepIf;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;
/**
 * Enables the intake and feed for 1 step of a sequence, until the next transition is complete.
 */
public class autoBackIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.INTAKING);  
        Shooter.getInstance().setFeed(.8, .8);
        Shooter.getInstance().setIndexer(-0.55);
    }

    @Override
    public void stepEnd() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.IDLE);        
        Shooter.getInstance().setFeed(0);
        Shooter.getInstance().setIndexer(0);
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
    }

    @Override
    public void stepUpdate() {
        Shooter.getInstance().setFeed(.8, .8);
        Shooter.getInstance().setIndexer(-0.55);        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}
