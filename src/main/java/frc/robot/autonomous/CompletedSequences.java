package frc.robot.autonomous;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import frc.robot.autonomous.sequenceSteps.*;
import frc.robot.autonomous.sequencer.Sequence;
/**
 * Implementation for all of our sequences
 */
public class CompletedSequences {
    // sequence lib
    public static synchronized List<Sequence> getSequences()
    {
        if (theSequences == null)
        {
            theSequences = new LinkedList<Sequence>();
            // READY FOR COMP TUNING
            theSequences.add(createDefault());
        }
        return Collections.unmodifiableList(theSequences);
    }


    private static Sequence createDefault()
    {
        autoDrive t1 = new autoDrive();
        t1.setAngle(4);
        autoDrive d1 = new autoDrive();
        d1.setAngle(5);
        d1.setDist(-1.5);
        d1.setSpeed(0.2);
        autoDrive d2 = new autoDrive();
        d2.setAngle(5);
        d2.setDist(0.5);
        d2.setSpeed(0.2);
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(10);
        autoShooter shoot1 = new autoShooter();

        t1.setNextTrans(d1);
        // t1.setNextSteps(d1, intake);
        d1.setNextTrans(d2);
        d1.setNextSteps(d2);
        // d2.setNextTrans(lime1);
        // d2.setNextSteps(lime1);
        // lime1.setNextTrans(ball1);
        // lime1.setNextSteps(shoot1);
        
        Sequence seq = new Sequence("Forgotten select", 0);
        seq.setInitialTransitions(t1);
        // seq.setInitialSteps(t1, intake);
        return seq;
    }

    static LinkedList<Sequence> theSequences = null;
}