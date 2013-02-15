

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel; 
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;

//import edu.wpi.first.wpilibj.buttons.JoystickButton;

        
public class Team2503RobotMain extends SimpleRobot {
    
    //CONSTANTS BEGIN HERE
    
    static final int JoystickNumber = 1; //This is the joystick # the driverstation must set the gamepad to
    
    static final int LeftJag1PWMSlot = 1;
    
    static final int RightJag1PWMSlot = 4;
    static final int ShooterJag2PWMSlot = 3;
    static final int CenterJag1PWMSlot = 6;
    static final int ForwardAxisleft = 1;
    static final int ForwardAxisright = 4;
   // static final int JoystickButton1 = 5;
    static final int rel1kforward = 1;
    
    static final int Button1 = 2;
    
    static final int button6 = 6;
    
    static final int analogSensorPort = 7; //For now, use port 1 on the analog module to receive analog data. goes to pin 3 on sensor
    static final int digitalOutputOnOffPort = 1; //use port 1 on the I/O section of the breakout to control on/off - goes to pin 4 on sensor
    
    
    //CONSTANTS END HERE
    
    
    //ROBOT SYSTEMS AND VARIABLES START HERE
    
    Joystick gamePad;
    Jaguar leftJag1, leftJag2, rightJag1, ShooterJag2, centerJag1;
    Relay rel1; 
    //JoystickButton Button1;

    MaxBotixHRLV mySonar;
    
    //ROBOT SYSTEMS AND VARIABLES END HERE
    
    
    
    
    
    Team2503RobotMain(){
        gamePad = new Joystick(JoystickNumber);
        
        leftJag1 = new Jaguar(LeftJag1PWMSlot);
        
        rightJag1 = new Jaguar(RightJag1PWMSlot);
        ShooterJag2 = new Jaguar(ShooterJag2PWMSlot);
        centerJag1 = new Jaguar(CenterJag1PWMSlot);
       
        mySonar = new MaxBotixHRLV(1, 1, 
                                   2, 1,
                                   new SonarInterfaceEnum(SonarInterfaceEnum.useAnalog));

    }        

   
    
    public void autonomous() {
        

    }
    
        
        
   



    

    
    public void operatorControl() {
        while(isOperatorControl()){
            
            TypicalJoyPadDrive(); //Right now, just drive around using arcade drive for arbitraty sticks.
            Timer.delay(0.05);
            
        }
    }
     
         
         
     
     private void JoyPadDrive(double forward, double turn, boolean rawButton, boolean rawButton2){
        
        leftJag1.set(forward - turn);
        
        rightJag1.set(forward + turn);
       
        if(rawButton2){
            ShooterJag2.set(0.75);
        }
        else {
            ShooterJag2.set(0.0);
                    }    

        if(rawButton){
            centerJag1.set(-0.35);
    
        }
        
        else {
            centerJag1.set(0.0);
        }
        
    }
    
    private void TypicalJoyPadDrive(){
        JoyPadDrive(gamePad.getRawAxis(ForwardAxisleft), gamePad.getRawAxis(ForwardAxisright), gamePad.getRawButton(Button1), gamePad.getRawButton(button6));
    }
    
  

    
}
    



