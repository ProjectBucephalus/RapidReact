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


    private static Sequence createPos1()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(0.2);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(-87.5);
        d1.setDist(-1.2);
        d1.setSpeed(0.58);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2400);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(shoot1);
        d1.setNextSteps(shoot1);
        shoot1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos1 2Ball", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }








   
    private static Sequence createPos2()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(0.2);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(-22.5);
        d1.setDist(-1.5);
        d1.setSpeed(0.58);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2400);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(shoot1);
        d1.setNextSteps(shoot1);
        shoot1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos2 2Ball", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }









    private static Sequence createPos3()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(0.2);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(3);
        d1.setDist(-2);
        d1.setSpeed(0.4);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2400);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(shoot1);
        d1.setNextSteps(shoot1);
        shoot1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos3 2Ball", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }
    static LinkedList<Sequence> theSequences = null;
}