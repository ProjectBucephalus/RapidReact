package frc.robot.autonomous.sequencers;

import frc.robot.autonomous.SequenceStepIf;
import frc.robot.autonomous.SequenceTransition;
import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
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
        Shooter.getInstance().setFeed(0);
        
    }

    @Override
    public void stepUpdate() {
        if (Shooter.getInstance().getShooterRPM() >= (Shooter.getInstance().getShooterSetSpeed(ShooterSpeedSlot.SHOOTING) - 125))
        {
            Shooter.getInstance().setIndexer(.55);
            Shooter.getInstance().setFeed(1);
            BackIntake.getInstance().setDesiredState(BackIntakeStates.INTAKING);
        }
        else {
            Shooter.getInstance().setIndexer(0);
            Shooter.getInstance().setFeed(0.4);
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
