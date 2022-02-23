package frc.sequencer.jarryd;

import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.sequencer.SequenceStepIf;

public class autoBackIntake implements SequenceStepIf{

    @Override
    public void stepStart() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.INTAKING);        
    }

    @Override
    public void stepEnd() {
        BackIntake.getInstance().setDesiredState(BackIntakeStates.IDLE);        
    }

    @Override
    public void stepUpdate() {        
    }

    @Override
    public String stepName() {
        return "Intake";
    }
    
}
