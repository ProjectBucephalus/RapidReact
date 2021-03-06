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
            
            theSequences.add(createPos1Complex());
            theSequences.add(createPos1Basic());
            theSequences.add(createPos2Basic());
            theSequences.add(createPos3Basic());
            theSequences.add(createPos4Normal());


    // not used
            // theSequences.add(create4bCut());
            // theSequences.add(createshoot());
            // theSequences.add(createSquare());
            // theSequences.add(createCurveShoot());
            // theSequences.add(createFullShooter());
            // theSequences.add(create1b());
            // theSequences.add(create2b());
            // theSequences.add(create3b());
            // theSequences.add(create4b());
            //theSequences.add(createAccTest());
            // theSequences.add(createRevAccTest());

        }
        return Collections.unmodifiableList(theSequences);

    }


    private static Sequence createDefault()
    {
        autoBackIntake intake = new autoBackIntake();
        autoTurn t1 = new autoTurn();
        t1.setAngle(4);
        autoDrive d1 = new autoDrive();
        d1.setAngle(5);
        d1.setDist(-1.5);
        d1.setSpeed(0.2);
        autoDrive d2 = new autoDrive();
        d2.setAngle(5);
        d2.setDist(0.5);
        d2.setSpeed(0.2);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(10);
        autoShooter shoot1 = new autoShooter();

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(d2);
        d1.setNextSteps(d2);
        d2.setNextTrans(lime1);
        d2.setNextSteps(lime1);
        lime1.setNextTrans(ball1);
        lime1.setNextSteps(shoot1);
        
        Sequence seq = new Sequence("Forgotten select", 0);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }









    private static Sequence createPos1Complex()
    {
        autoShooter tick = new autoShooter();
        timedStep tick2 = new timedStep();
        tick2.setDelay(.01);
        autoBackIntake intake = new autoBackIntake();
        // autoBackUnIntake unIntake = new autoBackUnIntake();
        // autoSpinUp spin1 = new autoSpinUp();
        // autoSpinUp spin2 = new autoSpinUp();
        // autoSpinUp spin3 = new autoSpinUp();
        autoTurn t1 = new autoTurn();
        t1.setAngle(-84.75);
        autoDrive d1 = new autoDrive();
        d1.setAngle(-84.75);
        d1.setDist(-.82);
        d1.setSpeed(0.85);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionLimelight ball = new autoBallDetectionLimelight();
        ball.setNumBalls(2);
        timedStep T01 = new timedStep();
        T01.setDelay(1.3);
        autoShooter shoot = new autoShooter();
        autoDrive d2 = new autoDrive();
        d2.setAngle(-75);
        d2.setDist(0.16);
        d2.setSpeed(0.87);
        autoTurn t2 = new autoTurn();
        t2.setAngle(17.3);
        autoDrive d3 = new autoDrive();
        d3.setAngle(17.3);
        d3.setDist(-2.496);
        d3.setSpeed(0.892);
        d3.setAccFwdLimit(0.18);
        d3.setAccRevLimit(0.25);
        autoTurn t02 = new autoTurn();
        t02.setAngle(-30);
        autoLimelight lime2 = new autoLimelight();
        timedStep T1 = new timedStep();
        T1.setDelay(.01);
        autoBallDetectionLimelight ball2 = new autoBallDetectionLimelight();
        ball2.setNumBalls(1);
        timedStep T02 = new timedStep();
        T02.setDelay(1.0);
        autoShooter shoot2 = new autoShooter();
        autoTurn t3 = new autoTurn();
        t3.setAngle(0);
        autoDrive d4 = new autoDrive();
        d4.setAngle(0);
        d4.setDist(-1.8875);//NICE
        d4.setSpeed(0.9); 
        d4.setAccFwdLimit(0.15);
        d4.setAccRevLimit(0.22);
        d4.setDistGain(1.9);
        autoTurn t4 = new autoTurn();
        t4.setAngle(-43);
        autoDrive d5 = new autoDrive();
        d5.setAngle(-45);
        d5.setDist(-1.375125);
        d5.setSpeed(0.76);
        d5.setAccFwdLimit(0.15);
        d5.setAccRevLimit(0.12);
        d5.setDistGain(1);
        timedStep T2 = new timedStep();
        T2.setDelay(.01);
        autoDrive d6 = new autoDrive();
        d6.setAngle(-46.4);
        d6.setDist(0.5);
        d6.setSpeed(1);
        d6.setAccFwdLimit(0.20);
        d6.setAccRevLimit(0.22);
        d6.setDistGain(2);
        autoTurn t5 = new autoTurn();
        t5.setAngle(-19);
        autoLimelight lime3 = new autoLimelight();
        autoDrive d7 = new autoDrive();
        d7.setAngle(-19);
        d7.setDist(2);
        d7.setSpeed(.71);
        d7.setAccFwdLimit(0.02);
        d7.setAccRevLimit(0.045);
        d7.setDistGain(.53);
        timedStep T3 = new timedStep();
        T3.setDelay(1.0);
        autoBallDetectionLimelight ball3 = new autoBallDetectionLimelight();
        ball3.setNumBalls(31);
        timedStep T4 = new timedStep();
        T4.setDelay(.01);
        autoShooter shoot3 = new autoShooter();
        timedStep ELimit = new timedStep();
        ELimit.setDelay(1.35);
        //Please note, the desync between the prespin and the actual shooting of the last shot cycle is an intentional descision to the ball isn't affected by any drop off acceleration and nicely lands in the circle.
        tick2.setNextTrans(t1);
        tick2.setNextSteps(t1, shoot, intake);
        t1.setNextTrans(t1);
        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(lime1);
        d1.setNextSteps(lime1, intake);
        lime1.setNextTrans(shoot);
        lime1.setNextSteps(shoot,intake);
        shoot.setNextTrans(ball, T01);
        shoot.setNextSteps(shoot);
        ball.setNextTrans(d2);
        ball.setNextSteps(d2, intake);
        T01.setNextTrans(d2);
        T01.setNextSteps(d2, intake);
        d2.setNextTrans(t2);
        d2.setNextSteps(t2, intake);
        t2.setNextTrans(d3);
        t2.setNextSteps(d3, intake);
        d3.setNextTrans(t02);
        d3.setNextSteps(t02, intake);
        t02.setNextTrans(lime2);
        t02.setNextSteps(lime2, intake);
        lime2.setNextTrans(T1);
        lime2.setNextSteps(intake,shoot2);
        T1.setNextTrans(ball2, T02);
        T1.setNextSteps(shoot2);
        ball2.setNextTrans(t3);
        ball2.setNextSteps(t3);
        T02.setNextTrans(t3);
        T02.setNextSteps(t3);
        t3.setNextTrans(d4);
        t3.setNextSteps(d4);
        d4.setNextTrans(t4);
        d4.setNextSteps(t4);
        t4.setNextTrans(d5);
        t4.setNextSteps(d5,intake);
        d5.setNextTrans(T3);
        d5.setNextSteps(intake);
        T3.setNextTrans(d6);
        T3.setNextSteps(d6);
        d6.setNextTrans(t5);
        d6.setNextSteps(t5);
        t5.setNextTrans(d7);
        t5.setNextSteps(d7,intake);
        d7.setNextTrans(lime3);
        d7.setNextSteps(lime3);
        lime3.setNextTrans(T4); 
        T4.setNextTrans(ball3);
        T4.setNextSteps(shoot3);
        Sequence seq = new Sequence("Pos-1 5Ball", 1);
        seq.setInitialTransitions(tick2);

        seq.setInitialSteps(tick);
        return seq;
    }










    private static Sequence createPos4Normal()
    {
        autoBackIntake intake = new autoBackIntake();
        autoTurn t1 = new autoTurn();
        t1.setAngle(40);
        autoDrive d1 = new autoDrive();
        d1.setAngle(40);
        d1.setDist(-1.5);
        d1.setSpeed(0.4);
        timedStep t2 = new timedStep();
        t2.setDelay(0.5);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionLimelight ball1 = new autoBallDetectionLimelight();
        ball1.setNumBalls(2);
        autoShooter shoot1 = new autoShooter();
        autoTurn t3 = new autoTurn();
        t3.setAngle(139.5);
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
        autoTurn t4 = new autoTurn();
        t4.setAngle(210);
        autoBallDetectionLimelight ball2 = new autoBallDetectionLimelight();
        ball2.setNumBalls(5);
        autoEjector eject2 = new autoEjector();


        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(t2);
        d1.setNextSteps(t2, intake);
        t2.setNextTrans(lime1);
        t2.setNextSteps(lime1, intake);
        lime1.setNextTrans(ball1);
        lime1.setNextSteps(shoot1);
        ball1.setNextTrans(t3);
        ball1.setNextSteps(t3);
        t3.setNextTrans(d2);
        t3.setNextSteps(d2, intake);
        d2.setNextTrans(T1);
        d2.setNextSteps(intake);
        T1.setNextTrans(d3);
        T1.setNextSteps(d3, intake);
        d3.setNextTrans(t4);
        d3.setNextSteps(t4, intake);
        t4.setNextTrans(ball2);
        t4.setNextSteps(eject2);

        Sequence seq = new Sequence("Pos-4 2Ball + Steal", 4);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }









    private static Sequence createPos1Basic()
    {

        autoBackIntake intake = new autoBackIntake();
        autoTurn t1 = new autoTurn();
        t1.setAngle(-87.5);
        autoDrive d1 = new autoDrive();
        d1.setAngle(-87.5);
        d1.setDist(-1.2);
        d1.setSpeed(0.58);
        d1.setAccFwdLimit(0.15);
        d1.setAccRevLimit(0.17);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionLimelight ball = new autoBallDetectionLimelight();
        ball.setNumBalls(2);
        autoShooter shoot1 = new autoShooter();

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(lime1);
        d1.setNextSteps(lime1);
        lime1.setNextTrans(ball);
        lime1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos1 2Ball", 1);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }








   
    private static Sequence createPos2Basic()
    {
        autoBackIntake intake = new autoBackIntake();
        autoTurn t1 = new autoTurn();
        t1.setAngle(-21.5);
        autoDrive d1 = new autoDrive();
        d1.setAngle(-21.5);
        d1.setDist(-1.5);
        d1.setSpeed(0.4);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(3);
        autoShooter shoot1 = new autoShooter();

        t1.setNextTrans(d1);
        t1.setNextSteps(d1, intake);
        d1.setNextTrans(lime1);
        d1.setNextSteps(lime1, intake);
        lime1.setNextTrans(ball1);
        lime1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos 2 2Ball", 2);
        seq.setInitialTransitions(t1);
        seq.setInitialSteps(t1, intake);
        return seq;
    }









    private static Sequence createPos3Basic()
    {
        autoDrive d1 = new autoDrive();
        d1.setAngle(3);
        d1.setDist(-2);
        d1.setSpeed(0.3);
        autoTurn a1 = new autoTurn();
        a1.setAngle(3);
        timedStep T1 = new timedStep();
        T1.setDelay(3);
        autoLimelight lime1 = new autoLimelight();
        autoBallDetectionMotorRPM ball1 = new autoBallDetectionMotorRPM();
        ball1.setNumBalls(5);
        autoShooter shoot1 = new autoShooter(); 
        a1.setNextTrans(d1);
        a1.setNextSteps(d1);
        d1.setNextTrans(lime1, T1);
        d1.setNextSteps(lime1);
        lime1.setNextTrans(ball1);
        lime1.setNextSteps(shoot1);

        Sequence seq = new Sequence("Pos 3 1ball", 3);
        seq.setInitialTransitions(a1);
        seq.setInitialSteps(a1);
        return seq;
    }

    // private static Sequence createAccTest()
    // {
    //     autoDrive drive1 = new autoDrive();
    //     drive1.setDist(1.5);
    //     drive1.setAngle(0);
    //     drive1.setSpeed(0.2);

    //     Sequence seq = new Sequence("Acceleration Test", 0);
    //     seq.setInitialTransitions(drive1);
    //     seq.setInitialSteps(drive1);
    //     return seq;
    // }    

    // private static Sequence createRevAccTest()
    // {
    //     autoDrive drive1 = new autoDrive();
    //     drive1.setDist(-2);
    //     drive1.setAngle(0);
    //     drive1.setSpeed(0.5);
    //     drive1.setAccFwdLimit(0.1);
    //     drive1.setAccRevLimit(0.05);
  
    //     Sequence seq = new Sequence ("Reverse Acc Test", 0);
    //     seq.setInitialTransitions(drive1);
    //     seq.setInitialSteps(drive1);
    //     return seq;
    // }





    // //auto start pos code
    // //test/improve
    // private static Sequence create1b()
    // {
    //     autoShooter step1C1 = new autoShooter();
    //     autoBackUnIntake step1C2 = new autoBackUnIntake();

    //     autoTurn step1C4 = new autoTurn();
    //     step1C4.setAngle(-86.5);

    //     autoDrive step1C5 = new autoDrive();
    //     step1C5.setAngle(-86.5);
    //     step1C5.setDist(-1.25);
    //     step1C5.setSpeed(0.3);

    //     autoTurn step1C6 = new autoTurn();
    //     step1C6.setAngle(-79.5);

    //     timedStep timer1C1 = new timedStep();
    //     timer1C1.setDelay(10);
        
    //     step1C4.setNextTrans(step1C5);
    //     step1C4.setNextSteps(step1C5, step1C1);//, step1C2
    //     step1C5.setNextTrans(step1C6);
    //     step1C5.setNextSteps(step1C6, step1C1, step1C2);
    //     step1C6.setNextTrans(timer1C1);
    //     step1C6.setNextSteps(step1C1, step1C2);

    //     Sequence seq = new Sequence("1B start pos", 1);
    //     seq.setInitialTransitions(step1C4);
    //     seq.setInitialSteps(step1C4, step1C1);
    //     return seq;
    // }

    // private static Sequence create2b()
    // {
    //     timedStep timer2B1 =new timedStep();
    //     timer2B1.setDelay(2);

    //     timedStep timer2B2 = new timedStep();
    //     timer2B2.setDelay(0.5);

    //     timedStep timer2B3 = new timedStep();
    //     timer2B3.setDelay(5);

    //     autoCurve step2B1 = new autoCurve();
    //     step2B1.setRadius(1.58);
    //     step2B1.setAngle(-50);
    //     step2B1.setSpeed(-0.3);

    //     autoDrive step2B2 = new autoDrive();
    //     step2B2.setDist(-0.2);
    //     step2B2.setAngle(-11.5);
    //     step2B2.setSpeed(0.3);

    //     autoTurn step2B3 = new autoTurn();
    //     step2B3.setAngle(-38.5);

    //     autoTurn step2B8 = new autoTurn();
    //     step2B8.setAngle(-6.5);

    //     autoDrive step2B9 = new autoDrive();
    //     step2B9.setAngle(-6.5);
    //     step2B9.setSpeed(0.3);
    //     step2B9.setDist(-3.5);

    //     autoTurn step2B10 = new autoTurn();
    //     step2B10.setAngle(-45);

    //     autoDrive step2B11 = new autoDrive();
    //     step2B11.setAngle(-45);
    //     step2B11.setDist(-0.8);
    //     step2B11.setSpeed(0.3);

    //     autoCurve step2B12 = new autoCurve();
    //     step2B12.setRadius(0.5);
    //     step2B12.setAngle(10);

    //     autoTurn step2B13 = new autoTurn();
    //     step2B13.setAngle(-21.5);

    //     autoDrive step2B14 = new autoDrive();
    //     step2B14.setAngle(-21.5);
    //     step2B14.setDist(-1.5);
    //     step2B14.setSpeed(0.3);

    //     autoShooter step2B4 = new autoShooter();
    //     autoBackUnIntake step2B6 = new autoBackUnIntake();

    //     step2B13.setNextTrans(step2B14);
    //     step2B13.setNextSteps(step2B14);//, step2B6
       
    //     step2B14.setNextTrans(step2B3);
    //     step2B14.setNextSteps(step2B3, step2B4);
    //     step2B3.setNextTrans(timer2B1);
    //     step2B3.setNextSteps(step2B4);
    //     timer2B1.setNextTrans(step2B8);
    //     timer2B1.setNextSteps(step2B8);
    //     step2B8.setNextTrans(step2B9);
    //     step2B8.setNextSteps(step2B9);
    //     step2B9.setNextTrans(step2B10);
    //     step2B9.setNextSteps(step2B10);
    //     step2B10.setNextTrans(step2B11);
    //     step2B10.setNextSteps(step2B11);
    //     step2B11.setNextTrans(timer2B2);
    //     step2B11.setNextSteps(step2B6);
    //     timer2B2.setNextTrans(step2B12);
    //     timer2B2.setNextSteps(step2B12, step2B4);//, step2B6
    //     step2B12.setNextTrans(timer2B3);
    //     step2B12.setNextSteps(step2B4);//, step2B6

    //     Sequence seq = new Sequence("2B start pos", 2);
    //     seq.setInitialTransitions(step2B13);
    //     seq.setInitialSteps(step2B13);//, step2B6
    //     return seq;
    // }

    

    // private static Sequence create3b()
    // {
    //     autoShooter shoot = new autoShooter();
    //     autoBackUnIntake intake = new autoBackUnIntake();

    //     autoTurn step3B1 = new autoTurn();
    //     step3B1.setAngle(64);

    //     autoDrive step3B2 = new autoDrive();
    //     step3B2.setAngle(64);
    //     step3B2.setDist(-2.2);
    //     step3B2.setSpeed(0.3);

    //     autoTurn step3B3 = new autoTurn();
    //     step3B3.setAngle(29);

    //     timedStep timer3B1 = new timedStep();
    //     timer3B1.setDelay(7.5);


    //     step3B1.setNextTrans(step3B2);
    //     step3B1.setNextSteps(step3B2, shoot,  intake);
    //     step3B2.setNextTrans(step3B3);
    //     step3B2.setNextSteps(step3B3, shoot, intake);
    //     step3B3.setNextTrans(timer3B1);
    //     step3B3.setNextSteps(shoot, intake);

    //     Sequence seq = new Sequence("3B start pos", 3);
    //     seq.setInitialTransitions(step3B1);
    //     seq.setInitialSteps(step3B1);
    //     return seq;
    // }  

    // private static Sequence create4b()
    // {
    //     autoShooter shoot = new autoShooter();
    //     autoBackUnIntake intake = new autoBackUnIntake();

    //     autoTurn step4B1 = new autoTurn();
    //     step4B1.setAngle(36);

    //     autoDrive step4B2 = new autoDrive();
    //     step4B2.setAngle(36);
    //     step4B2.setDist(-1.5);
    //     step4B2.setSpeed(0.3);

    //     timedStep timer4B1 = new timedStep();
    //     timer4B1.setDelay(5);

    //     step4B1.setNextTrans(step4B2);
    //     step4B1.setNextSteps(step4B2, shoot, intake);
    //     step4B2.setNextTrans(timer4B1);
    //     step4B2.setNextSteps(shoot);
    //     Sequence seq = new Sequence("4B start pos", 4);
    //     seq.setInitialTransitions(step4B1);
    //     seq.setInitialSteps(step4B1, shoot);
    //     return seq;
    // }

    // private static Sequence create4bCut()
    // {
        // autoShooter shoot = new autoShooter();
        // autoBackUnIntake intake = new autoBackUnIntake();

        // autoTurn step4B1 = new autoTurn();
        // step4B1.setAngle(36);

        // autoDrive step4B2 = new autoDrive();
        // step4B2.setAngle(36);
        // step4B2.setDist(-1.5);
        // step4B2.setSpeed(0.3);

        // timedStep timer4B1 = new timedStep();
        // timer4B1.setDelay(5);

        // step4B1.setNextTrans(step4B2);
        // step4B1.setNextSteps(step4B2, shoot, intake);
        // step4B2.setNextTrans(timer4B1);
        // step4B2.setNextSteps(shoot);

    //     Sequence seq = new Sequence("4B start pos", 4);
    //     seq.setInitialTransitions();
    //     seq.setInitialSteps();
    //     return seq;
    // }





















//     private static Sequence createSquare()
//     {
//         // jtimer timer1 =new jtimer();
//         // timer1.setDelay(3);
//         autoDrive step1=new autoDrive();
//         step1.setDist(3);
//         autoDrive timer1 = step1;

//         // jtimer timer2 =new jtimer();
//         // timer2.setDelay(3);
//         autoTurn step2=new autoTurn();
//         step2.setAngle(90);
//         autoTurn timer2 = step2;

//         // jtimer timer3 =new jtimer();
//         // timer3.setDelay(3);
//         autoDrive step3=new autoDrive();
//         step3.setDist(3);
//         step3.setAngle(90);
//         autoDrive timer3 = step3;

//         // jtimer timer4 =new jtimer();
//         // timer4.setDelay(3);
//         autoTurn step4=new autoTurn();
//         step4.setAngle(180);
//         autoTurn timer4 = step4;

//         // jtimer timer5 =new jtimer();
//         // timer5.setDelay(3);
//         autoDrive step5=new autoDrive();
//         step5.setDist(3);
//         step5.setAngle(180);
//         autoDrive timer5 = step5;

//         // jtimer timer6 =new jtimer();
//         // timer6.setDelay(3);
//         autoTurn step6=new autoTurn();
//         step6.setAngle(270);
//         autoTurn timer6 = step6;

//         // jtimer timer7 =new jtimer();
//         // timer7.setDelay(3);
//         autoDrive step7=new autoDrive();
//         step7.setDist(3);
//         step7.setAngle(270);
//         autoDrive timer7 = step7;

//         // jtimer timer8 =new jtimer();
//         // timer8.setDelay(3);
//         autoTurn step8=new autoTurn();
//         step8.setAngle(0);
//         autoTurn timer8 = step8;

//         timer1.setNextTrans(timer2);
//         timer1.setNextSteps(step2);
//         timer2.setNextTrans(timer3);
//         timer2.setNextSteps(step3);
//         timer3.setNextTrans(timer4);
//         timer3.setNextSteps(step4);
//         timer4.setNextTrans(timer5);
//         timer4.setNextSteps(step5);
//         timer5.setNextTrans(timer6);
//         timer5.setNextSteps(step6);
//         timer6.setNextTrans(timer7);
//         timer6.setNextSteps(step7);
//         timer7.setNextTrans(timer8);
//         timer7.setNextSteps(step8);
//         timer8.setNextTrans(timer1);
//         timer8.setNextSteps(step1);

//         Sequence seq = new Sequence("Jarryd Square", 0);
//         seq.setInitialSteps(step1);
//         seq.setInitialTransitions(timer1);
//         return seq;

//     }

//     private static Sequence createCurveShoot()
//     {
//         autoCurve stepc1 = new autoCurve();
//         stepc1.setRadius(1);
//         stepc1.setAngle(-90);
      
//         autoCurve stepc2 = new autoCurve();
//         stepc2.setRadius(1);
//         stepc2.setAngle(-90);
//         stepc2.setSpeed(-0.3);
//         autoShooter stepc2a = new autoShooter();
    
//         autoDrive stepc3 = new autoDrive();
//         stepc3.setAngle(0);
//         stepc3.setDist(-0.50);
//         stepc3.setSpeed(0.3);
        
//         stepc1.setNextTrans(stepc2);
//         stepc1.setNextSteps(stepc2, stepc2a);
//         stepc2.setNextTrans(stepc3);
//         stepc2.setNextSteps(stepc3, stepc2a);

//         Sequence seq = new Sequence("Jarryd Curve Shoot", 0);
//         seq.setInitialSteps(stepc1);
//         seq.setInitialTransitions(stepc1);
//         return seq;
//     }

// private static Sequence createFullShooter()
// {
//     autoBackUnIntake stepf1 = new autoBackUnIntake();
//     autoShooter stepf2 = new autoShooter();

//     timedStep timerf1 = new timedStep();
//     timerf1.setDelay(5);
//     timedStep timerf2 = new timedStep();
//     timerf2.setDelay(5);
//     timedStep timerf3 = new timedStep();
//     timerf3.setDelay(5);
//     timedStep timerf4 = new timedStep();
//     timerf4.setDelay(5);

//     timerf1.setNextTrans(timerf2);
//     timerf1.setNextSteps(stepf2);
//     timerf2.setNextTrans(timerf3);
//     timerf2.setNextSteps(stepf2);


//         Sequence seq = new Sequence("Jarryd Full Shooter", 0);
//         seq.setInitialSteps(stepf1);
//         seq.setInitialTransitions(timerf1);
//         return seq;
// }
// private static Sequence createshoot()
// {
//     autoShooter shoot = new autoShooter();
//     autoBallShooter ball = new autoBallShooter();
//     ball.setNumBalls(3);
//     autoBallShooter ball2 = new autoBallShooter();
//     ball2.setNumBalls(2);
//     timedStep t1 = new timedStep();
//     t1.setDelay(2);
//     timedStep t2 = new timedStep();
//     t2.setDelay(5);

//     timedStep t3 = new timedStep();
//     t3.setDelay(3);
    
//     autoTurn d2 = new autoTurn();
//     d2.setAngle(90);
   
//     autoDrive d1 = new autoDrive();
//     d1.setAngle(0);
//     d1.setDist(1);
//     d1.setSpeed(0.3);

//     shoot.setNextTrans(ball);
//     shoot.setNextSteps(shoot);
//     ball.setNextTrans(t1);
//     ball.setNextSteps();

//     Sequence seq = new Sequence("shoot", 0);
//     seq.setInitialTransitions(shoot);
//     seq.setInitialSteps(shoot);
//     return seq;
// }



    static LinkedList<Sequence> theSequences = null;
}