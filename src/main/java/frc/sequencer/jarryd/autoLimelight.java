package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.VisionTrack;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.robot.subsystems.VisionTrack.VisionState;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

//cannot be used with much sunlight
public class autoLimelight extends SequenceTransition implements SequenceStepIf {


    @Override
    public void stepStart() {
        VisionTrack.getInstance().setDesiredState(VisionState.AUTOTURN);
    
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);
    }

    @Override
    public void stepUpdate() {
        VisionTrack.getInstance().setDesiredState(VisionState.AUTOTURN);
        if (Limelight.getInstance().getAngleToTarget()<2)
        {
                Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
                Shooter.getInstance().setFeed(1);
        }
    }

    @Override
    public String stepName() {
        return "Limelight";
    }


    @Override
    public void transStart() {
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }

    @Override
    public boolean isTransComplete() {
    if (Math.abs(Limelight.getInstance().getAngleToTarget())<2)
    {
        return true;
    }
        return false;
    }
}
