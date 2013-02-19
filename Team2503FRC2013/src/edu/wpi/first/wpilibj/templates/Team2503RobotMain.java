

package edu.wpi.first.wpilibj.templates;


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
    static final int LoaderJag1PWMSlot = 6;
    static final int ForwardAxisleft = 1;
    static final int ForwardAxisright = 4;
   // static final int JoystickButton1 = 5;
    static final int rel1kforward = 1;
    static final int Button4 = 2;
    static final int Button1 = 4;
    static final int Button3 = 3;
    static final int button6 = 6;
   
    static final int analogSensorPort = 7; //For now, use port 1 on the analog module to receive analog data. goes to pin 3 on sensor
    static final int digitalOutputOnOffPort = 1; //use port 1 on the I/O section of the breakout to control on/off - goes to pin 4 on sensor
    
    
    //CONSTANTS END HERE
    
    
    //ROBOT SYSTEMS AND VARIABLES START HERE
    
    Joystick gamePad;
    Jaguar leftJag1, leftJag2, rightJag1, ShooterJag2, LoaderJag1;
    Relay rel1; 
    //JoystickButton Button1;

    MaxBotixHRLV mySonar;
    
    //ROBOT SYSTEMS AND VARIABLES END HERE
    
    
    
    
    
    Team2503RobotMain(){
        gamePad = new Joystick(JoystickNumber);
        
        leftJag1 = new Jaguar(LeftJag1PWMSlot);
        
        rightJag1 = new Jaguar(RightJag1PWMSlot);
        ShooterJag2 = new Jaguar(ShooterJag2PWMSlot);
        LoaderJag1 = new Jaguar(LoaderJag1PWMSlot);
       
        mySonar = new MaxBotixHRLV(1, 1, //Create a sonar with analog voltage pin connected to channel 1, module 1 
                                   2, 1, //Create a sonar with digital enable/disable connected to breakout channel GPIO 1
                                   new SonarInterfaceEnum(SonarInterfaceEnum.useAnalog));
        
        
        
    }        

   
    
    public void autonomous() {
        
        
        
        
        double distanceInFeet = mySonar.returnDistanceFeet(); ;
        //Do something here based on the distance in feet
      
        
            
           
               ShooterJag2.set(0.6);
               Timer.delay(2.0);
               LoaderJag1.set(0.35);
         
             
           }
        

    
    
        
        
   



    

    
    public void operatorControl() {
        while(isOperatorControl()){
            
            TypicalJoyPadDrive(); //Right now, just drive around using arcade drive for arbitraty sticks.
            Timer.delay(0.05);
            
        }
    }
     
         
         
     
     private void JoyPadDrive( double turn, double rawAxis, boolean rawButton, boolean rawButton2, boolean rawButton3, boolean rawButton6){
        
       leftJag1.set(turn);
        rightJag1.set(rawAxis);
       
        if(rawButton2){
           ShooterJag2.set(0.75);
        }
        else {
            ShooterJag2.set(0.0);
                    }    

        if(rawButton){  
            LoaderJag1.set(-0.35);
          
        }
        
        else {
            LoaderJag1.set(0.0);
        }
        if(rawButton3){
            rightJag1.set(-0.8);
            leftJag1.set(0.8);
            
        }
        
        else{
         leftJag1.set(0);
            rightJag1.set(0);
     }
    if(rawButton6){
        leftJag1.set(-0.8);
        rightJag1.set(0.8);
    }
    else{
        leftJag1.set(0.0);
        rightJag1.set(0.0);
    }
     
     }
     
    
    private void TypicalJoyPadDrive(){
        JoyPadDrive(gamePad.getRawAxis(ForwardAxisleft), gamePad.getRawAxis(ForwardAxisright), gamePad.getRawButton(Button1), gamePad.getRawButton(button6), gamePad.getRawButton(Button3), gamePad.getRawButton(Button4));
    }
    
  

    
}
    



