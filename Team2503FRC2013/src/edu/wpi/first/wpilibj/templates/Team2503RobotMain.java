

package edu.wpi.first.wpilibj.templates;


import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Ultrasonic;
        
public class Team2503RobotMain extends SimpleRobot {
    
    //CONSTANTS BEGIN HERE
    
    static final int JoystickNumber = 1; //This is the joystick # the driverstation must set the gamepad to
    
    static final int LeftJag1PWMSlot = 1;
    static final int LeftJag2PWMSlot = 2;
    static final int RightJag1PWMSlot = 3;
    static final int RightJag2PWMSlot = 4;
    static final int ForwardAxis = 1;
    static final int TurnAxis = 4;
    static final int rel1kforward = 1;
    static final int robotDrive = 1;
    
    //CONSTANTS END HERE
    
    
    //ROBOT SYSTEMS AND VARIABLES START HERE
    
    Joystick gamePad;
    Jaguar leftJag1, leftJag2, rightJag1, rightJag2;
    Relay rel1;
    RobotDrive fish;
    //ROBOT SYSTEMS AND VARIABLES END HERE
    
    
    
    
    
    Team2503RobotMain(){
        gamePad = new Joystick(JoystickNumber);
        
        leftJag1 = new Jaguar(LeftJag1PWMSlot);
        leftJag2 = new Jaguar(LeftJag2PWMSlot);
        rightJag1 = new Jaguar(RightJag1PWMSlot);
        rightJag2 = new Jaguar(RightJag2PWMSlot);
       
    }        

   
    
    public void autonomous() {

    fish.drive(0.5, 0.0);
    Timer.delay(2.0);
    fish.drive(0.0, 0.0);
    Timer.delay(2.0);
    fish.drive(0.0, 0.75);  
      
    }

        
        
        
        //for (int i = 0; i < 4; i++)

//Timer.delay(2.0);
 

//leftJag1.(0.75);
//rightJag1.(0.5);


    

    
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


