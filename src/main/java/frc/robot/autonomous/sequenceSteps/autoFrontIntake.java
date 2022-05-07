package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.FrontIntake.FrontIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;
/**
 * Automatically intakes using the front intake and feed
*/
public class autoFrontIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        FrontIntake.getInstance().setDesiredState(FrontIntakeStates.INTAKING);   
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
     
    }

    @Override
    public void stepEnd() {
       FrontIntake.getInstance().setDesiredState(FrontIntakeStates.STOWED);        
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
    }

    @Override
    public void stepUpdate() {        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}
