package frc.robot.autonomous.sequenceSteps;

import frc.robot.autonomous.sequencer.SequenceStepIf;
import frc.robot.autonomous.sequencer.SequenceTransition;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Shooter.ShooterSpeedSlot;
import frc.robot.subsystems.Shooter.ShooterState;

/**
 * Turns on shooter when the shooter is at it's correct speed
 */
public class autoShooter extends SequenceTransition implements SequenceStepIf {

    @Override
    public void stepStart() {
        Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
    }

    @Override
    public void stepEnd() {
        Shooter.getInstance().setDesiredState(ShooterState.IDLE);
        Shooter.getInstance().setIndexer(0);
      
        
    }

    @Override
    public void stepUpdate() {
        if (Shooter.getInstance().getShooterRPM() >= (Shooter.getInstance().getShooterSetSpeed(ShooterSpeedSlot.SHOOTING) - 110))
        {
            Shooter.getInstance().setIndexer(.55);
        }
        else {
            Shooter.getInstance().setIndexer(0);
        }
        Shooter.getInstance().update();
    }

    @Override
    public String stepName() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void transStart() {
        Shooter.getInstance().getShooterRPM();
        Shooter.getInstance().getShooterTargetSpeed(); 
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean transUpdate() {
        return isTransComplete();
    }
    
    @Override
    public boolean isTransComplete() {
        return Shooter.getInstance().shooterAtSpeed;
    } 


}
