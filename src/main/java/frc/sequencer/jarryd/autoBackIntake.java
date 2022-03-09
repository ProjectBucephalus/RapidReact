package frc.sequencer.jarryd;

import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.sequencer.SequenceStepIf;

public class autoBackIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.INTAKING);  
        Shooter.getInstance().setFeed(1);
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
