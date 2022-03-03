package frc.sequencer.jarryd;

import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Limelight;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

//cannot be used with much sunlight
public class autoLimelight extends SequenceTransition implements SequenceStepIf {


    @Override
    public void stepStart() {
    }

    @Override
    public void stepEnd() {
        Drive.getInstance().autoArcadeDrive(0, 0);
    }

    @Override
    public void stepUpdate() {
        double limeErr = (Limelight.getInstance().getAngleToTarget()) % 360;
        // System.out.println("limeErr" + limeErr);
        if (limeErr > 180)
        {
            limeErr = limeErr - 360;
        }
        if (limeErr < -180)
        {
            limeErr = limeErr + 360;
        }

        double limeCmd = Math.sqrt(Math.abs(limeErr)) *0.045;
        if (limeErr < 0)
        {
            limeCmd = -limeCmd;
        }
        Drive.getInstance().autoArcadeDrive(limeCmd, 0);    
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
    if (Limelight.getInstance().getAngleToTarget()<5)
    {
        return true;
    }
        return false;
    }
}
