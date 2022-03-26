package frc.robot.autonomous.sequencers;

import frc.robot.autonomous.SequenceStepIf;
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
        Shooter.getInstance().setFeed(1);
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
     
    }

    @Override
    public void stepEnd() {
        FrontIntake.getInstance().setDesiredState(FrontIntakeStates.STOWED);        
        Shooter.getInstance().setFeed(0);
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
