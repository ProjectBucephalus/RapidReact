package frc.robot.autonomous;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import edu.wpi.first.wpilibj.TimesliceRobot;
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
        t1.setDelay(200);
        autoDrive s1 = new autoDrive();
        s1.setSpeed(0.9);
        s1.setDist(3.0);
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(2);
        timedStep t2 = new timedStep();
        t2.setDelay(5);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2065);


        s1.setNextTrans(t2,ball1);
        s1.setNextSteps(shoot1);
        ball1.setNextTrans(t2,ball1);
        ball1.setNextSteps(shoot1);
        Sequence seq = new Sequence("Basic auto", 0);
        seq.setInitialTransitions(s1);
        seq.setInitialSteps(s1);
        return seq;
    }


    private static Sequence createPos1()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(1);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(-87.5);
        d1.setDist(-1.2);
        d1.setSpeed(0.58);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(2);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2065);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(ball1);
        d1.setNextSteps(shoot1,intake);
        ball1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos1 2Ball", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }








   
    private static Sequence createPos2()
    {
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(2);
        timedStep t1 = new timedStep();
        t1.setDelay(1);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(-22.5);
        d1.setDist(-1.95);
        d1.setSpeed(0.58);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2065);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(ball1);
        d1.setNextSteps(shoot1,intake);
        ball1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos2 2Ball", 2);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }









    private static Sequence createPos3()
    {
        timedStep t1 = new timedStep();
        t1.setDelay(1);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(3);
        d1.setDist(-2);
        d1.setSpeed(0.4);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2065);

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(shoot1);
        d1.setNextSteps(shoot1,intake);
        shoot1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos3 2Ball", 3);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }

    private static Sequence createPos4(){
        timedStep subsst = new timedStep();
        subsst.setDelay(1);
        autoFrontIntake intake = new autoFrontIntake();
        autoDrive d1 = new autoDrive();
        d1.setAngle(40);
        d1.setDist(-1.95);
        d1.setSpeed(0.4);
        timedStep t2 = new timedStep();
        t2.setDelay(0.5);
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(2);
        autoShooter shoot1 = new autoShooter();
        shoot1.setRPM(2065);
        autoDrive d2 = new autoDrive();
        d2.setAngle(139.5);
        d2.setDist(-1.7);
        d2.setSpeed(0.3);
        timedStep T1 = new timedStep();
        T1.setDelay(0.3);
        autoDrive d3 = new autoDrive();
        d3.setAngle(139.5);
        d3.setDist(1.5);
        d3.setSpeed(0.3);
        autoBallDetectionMotorRPM ball2 = new autoBallDetectionMotorRPM();
        ball2.setNumBalls(5);
        autoShooter eject2 = new autoShooter();
        eject2.setRPM(800);


        subsst.setNextTrans(d1);
        subsst.setNextSteps(d1, intake);
        d1.setNextTrans(t2);
        d1.setNextSteps(t2, intake);
        t2.setNextTrans(subsst);
        t2.setNextSteps(intake);
        subsst.setNextTrans(ball1);
        subsst.setNextSteps(shoot1);
        ball1.setNextTrans(subsst);
        ball1.setNextSteps(subsst);
        subsst.setNextTrans(d2);
        subsst.setNextSteps(d2, intake);
        d2.setNextTrans(T1);
        d2.setNextSteps(intake);
        T1.setNextTrans(d3);
        T1.setNextSteps(d3, intake);
        d3.setNextTrans(subsst);
        d3.setNextSteps(subsst, intake);
        subsst.setNextTrans(ball2);
        subsst.setNextSteps(eject2);

        Sequence seq = new Sequence("Pos-4 2Ball + Steal", 4);
        seq.setInitialTransitions(subsst);
        seq.setInitialSteps(subsst, intake);
        return seq;
    }

    static LinkedList<Sequence> theSequences = null;
}