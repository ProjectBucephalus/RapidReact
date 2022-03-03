package frc.sequencer.jarryd;

import frc.robot.subsystems.FrontIntake;
import frc.robot.subsystems.FrontIntake.FrontIntakeStates;
import frc.sequencer.SequenceStepIf;

public class autoFrontIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        FrontIntake.getInstance().setDesiredState(FrontIntakeStates.INTAKING);        
    }

    @Override
    public void stepEnd() {
        FrontIntake.getInstance().setDesiredState(FrontIntakeStates.STOWED);        
    }

    @Override
    public void stepUpdate() {        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}