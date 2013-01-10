

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.camera.AxisCamera;
import edu.wpi.first.wpilibj.image.BinaryImage;
import edu.wpi.first.wpilibj.image.CriteriaCollection;
import edu.wpi.first.wpilibj.image.LinearAverages;
import edu.wpi.first.wpilibj.image.NIVision;
import edu.wpi.first.wpilibj.image.NIVision.MeasurementType;
import edu.wpi.first.wpilibj.image.NIVisionException;
import edu.wpi.first.wpilibj.image.ParticleAnalysisReport;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Team2503RobotMain extends SimpleRobot {
    
    //CONSTANTS BEGIN HERE
    
    static final int JoystickNumber = 1; //This is the joystick # the driverstation must set the gamepad to
    
    static final int LeftJag1PWMSlot = 1;
    static final int LeftJag2PWMSlot = 2;
    static final int RightJag1PWMSlot = 4;
    static final int RightJag2PWMSlot = 4;
    static final int ForwardAxis = 1;
    static final int TurnAxis = 4;
    
    
    //CONSTANTS END HERE
    
    //ROBOT SYSTEMS AND VARIABLES START HERE
    
    Joystick gamePad;
    Jaguar leftJag1, leftJag2, rightJag1, rightJag2;
    
    
    //ROBOT SYSTEMS AND VARIABLES END HERE
    
    
    
    /**
     * This function is called once each time the robot enters autonomous mode.
     */
    
    Team2503RobotMain(){
        gamePad = new Joystick(JoystickNumber);
        
        leftJag1 = new Jaguar(LeftJag1PWMSlot);
        leftJag2 = new Jaguar(LeftJag2PWMSlot);
        rightJag1 = new Jaguar(RightJag1PWMSlot);
        rightJag2 = new Jaguar(RightJag2PWMSlot);
        
    }        

   
    
    public void autonomous() {
        
    }

    /**
     * This function is called once each time the robot enters operator control.
     */
    public void operatorControl() {
        while(isOperatorControl()){
            
            TypicalJoyPadDrive(); //Right now, just drive around using arcade drive for arbitraty sticks.
            Timer.delay(0.05);
            
        }
    }
     
     private void JoyPadDrive(double forward, double turn){
        
        leftJag1.set(forward - turn);
        leftJag2.set(forward - turn);
        rightJag1.set(forward + turn);
        rightJag2.set(forward + turn);

    }
    
    private void TypicalJoyPadDrive(){
        JoyPadDrive(gamePad.getRawAxis(ForwardAxis), gamePad.getRawAxis(TurnAxis));
    }
}


