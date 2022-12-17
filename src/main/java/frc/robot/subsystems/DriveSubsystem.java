package frc.robot.subsystems;


//Imports for motors and constants
import frc.robot.RobotMap;

//Imports for all the stupid stuff math and etc etc 
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;





public class DriveSubsystem extends SubsystemBase{

private final WPI_TalonFX leftMotorA = RobotMap.getLeftDriveA();
private final WPI_TalonFX leftMotorB = RobotMap.getLeftDriveB();
private final WPI_TalonFX rightMotorA = RobotMap.getRightDriveA();
private final WPI_TalonFX rightMotorB = RobotMap.getRightDriveB();

  private final MotorControllerGroup m_leftMotors = new MotorControllerGroup(leftMotorA, leftMotorB);
  private final MotorControllerGroup m_rightMotors = new MotorControllerGroup(rightMotorA, rightMotorB);
  private final DifferentialDrive m_drive = new DifferentialDrive(m_leftMotors, m_rightMotors);


  private final Gyro m_gyro = new AHRS(SPI.Port.kMXP);

  private final DifferentialDriveOdometry m_odometry;

  private double nativeUnitsToDistanceMeters(double sensorCounts){
    double motorRotations = (double)sensorCounts / 2048;
    double wheelRotations = motorRotations / ( (12/50) * (20/54) );
    double positionMeters = wheelRotations * (2 * Math.PI * .0762);
    return positionMeters;
  }
  private void zeroEncoders(){
      leftMotorA.getSensorCollection().setIntegratedSensorPosition(0, 10);
      leftMotorB.getSensorCollection().setIntegratedSensorPosition(0,10);
      rightMotorA.getSensorCollection().setIntegratedSensorPosition(0,10);
      rightMotorB.getSensorCollection().setIntegratedSensorPosition(0,10);

  }

  private double getLeftMotorDistance(){
    double leftDriveB = nativeUnitsToDistanceMeters(leftMotorA.getSelectedSensorPosition());
    double leftDriveA = nativeUnitsToDistanceMeters(leftMotorB.getSelectedSensorPosition());
    double finalVal = (leftDriveA + leftDriveB)/2;
    return finalVal;


  }

  private double getRightMotorDistance(){
    double rightDriveB = nativeUnitsToDistanceMeters(rightMotorA.getSelectedSensorPosition());
    double rightDriveA = nativeUnitsToDistanceMeters(rightMotorB.getSelectedSensorPosition());
    double finalVal = (rightDriveA + rightDriveB)/2;
    return finalVal;
}

private double getLeftMotorRate(){
    double leftDriveB = nativeUnitsToDistanceMeters(leftMotorA.getSelectedSensorVelocity());
    double leftDriveA = nativeUnitsToDistanceMeters(leftMotorB.getSelectedSensorVelocity());
    double finalVal = (leftDriveA + leftDriveB)/2;
    return finalVal;


  }

  private double getRightMotorRate(){
    double rightDriveB = nativeUnitsToDistanceMeters(rightMotorA.getSelectedSensorVelocity());
    double rightDriveA = nativeUnitsToDistanceMeters(rightMotorB.getSelectedSensorVelocity());
    double finalVal = (rightDriveA + rightDriveB)/2;
    return finalVal;
}

    public double getAverageDistance(){
        return (getRightMotorDistance() + getLeftMotorDistance())/2;
    }




  public DriveSubsystem(){
    m_rightMotors.setInverted(true);
    m_odometry = new DifferentialDriveOdometry(m_gyro.getRotation2d());
    zeroEncoders();

  }



  public void periodic() {
    // Update the odometry in the periodic block
    m_odometry.update(m_gyro.getRotation2d(),getLeftMotorDistance(),getRightMotorDistance());

  }

  public Pose2d getPose() {
    return m_odometry.getPoseMeters();
  }

  public DifferentialDriveWheelSpeeds getWheelSpeeds() {
    return new DifferentialDriveWheelSpeeds(getLeftMotorRate(), getRightMotorRate());
  }

  public void resetOdometry(Pose2d pose) {
    zeroEncoders();
    m_odometry.resetPosition(pose, m_gyro.getRotation2d());
  }


  public void arcadeDrive(double fwd, double rot) {
    m_drive.arcadeDrive(fwd, rot);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    m_leftMotors.setVoltage(leftVolts);
    m_rightMotors.setVoltage(rightVolts);
    m_drive.feed();
  }
  public void resetEncoders() {
    zeroEncoders();
  }
  public void setMaxOutput(double maxOutput) {
    m_drive.setMaxOutput(maxOutput);
  }
  public void zeroHeading() {
    m_gyro.reset();
  }
  public double getHeading() {
    return m_gyro.getRotation2d().getDegrees();
  }

  public double getTurnRate() {
    return -m_gyro.getRate();
  }

  public double getAverageEncoderDistance() {
    return getAverageDistance();
  }

}
