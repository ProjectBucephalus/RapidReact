package frc.sequencer.jarryd;

import frc.robot.subsystems.BackIntake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.BackIntake.BackIntakeStates;
import frc.robot.subsystems.Shooter.ShooterState;
import frc.sequencer.SequenceStepIf;
import frc.sequencer.SequenceTransition;

public class autoShooter extends SequenceTransition implements SequenceStepIf {

    @Override
    public void stepStart() {
        Shooter.getInstance().setDesiredState(ShooterState.SHOOTING);
    }

    @Override
    public void stepEnd() {
        Shooter.getInstance().setDesiredState(ShooterState.SPINUP);
        // TODO Auto-generated method stub
        
    }

    @Override
    public void stepUpdate() {
        if (Shooter.getInstance().getShooterRPM() >= (shootSpeed - 200))
        {
            Shooter.getInstance().setIndexer(1);
            Shooter.getInstance().setFeed(1);
            BackIntake.getInstance().setDesiredState(BackIntakeStates.INTAKING);
        }
        else {
            Shooter.getInstance().setIndexer(-0.2);
            Shooter.getInstance().setFeed(0.1);
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

    private double shootSpeed = 0;
    public void setShootSpeed(double aShootSpeed)
    {
        shootSpeed = aShootSpeed;
    }
}
