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
            theSequences.add(createPos1());
            theSequences.add(createPos2());
            theSequences.add(createPos3());
            theSequences.add(createPos4());
        }
        return Collections.unmodifiableList(theSequences);
    }


    private static Sequence createDefault()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(2);
        autoDrive s1 = new autoDrive();
        s1.setSpeed(0.5);
        s1.setDist(3);;
        timedStep t2 = new timedStep();
        t2.setDelay(2);
        autoShooter shoot1 = new autoShooter();
        autoDrive t3 = new autoDrive();
        t3.setDist(-3);

        t1.setNextTrans(t2);
        t1.setNextSteps(shoot1);
        t2.setNextTrans(t3);
        t2.setNextSteps(t3);
        
        Sequence seq = new Sequence("Basic auto", 0);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(s1);
        return seq;
    }

    static LinkedList<Sequence> theSequences = null;
}