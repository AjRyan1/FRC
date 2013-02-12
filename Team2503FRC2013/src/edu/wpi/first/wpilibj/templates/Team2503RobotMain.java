

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel; 
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Jaguar;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

        
public class Team2503RobotMain extends SimpleRobot {
    
    //CONSTANTS BEGIN HERE
    
    static final int JoystickNumber = 1; //This is the joystick # the driverstation must set the gamepad to
    
    static final int LeftJag1PWMSlot = 1;
    static final int LeftJag2PWMSlot = 2;
    static final int RightJag1PWMSlot = 3;
    static final int RightJag2PWMSlot = 4;
    static final int CenterJag1PWMSlot = 5;
    static final int ForwardAxis = 1;
    static final int TurnAxis = 4;
    static final int JoystickButton1 = 5;
    static final int rel1kforward = 1;
    
    
    static final int analogSensorPort = 1; //For now, use port 1 on the analog module to receive analog data. goes to pin 3 on sensor
    static final int digitalOutputOnOffPort = 1; //use port 1 on the I/O section of the breakout to control on/off - goes to pin 4 on sensor
    
    
    //CONSTANTS END HERE
    
    
    //ROBOT SYSTEMS AND VARIABLES START HERE
    
    Joystick gamePad;
    Jaguar leftJag1, leftJag2, rightJag1, rightJag2, centerJag1;
    Relay rel1; // ignore this one
    JoystickButton Button1;
    DigitalOutput sonarOnOff;   //This is the object for controlling the output from the sidecar - set high to sample, low to turn off
    AnalogChannel sonarDistance; //This is the object for reading the sensor from the analog breakout on the cRio.
    
    //ROBOT SYSTEMS AND VARIABLES END HERE
    
    
    
    
    
    Team2503RobotMain(){
        gamePad = new Joystick(JoystickNumber);
        
        leftJag1 = new Jaguar(LeftJag1PWMSlot);
        leftJag2 = new Jaguar(LeftJag2PWMSlot);
        rightJag1 = new Jaguar(RightJag1PWMSlot);
        rightJag2 = new Jaguar(RightJag2PWMSlot);
        centerJag1 = new Jaguar(CenterJag1PWMSlot);
        rel1 = new Relay(rel1kforward);
        JoystickButton1 = new JoystickButton(CenterJag1PWMSlot);
        sonarOnOff = new DigitalOutput(digitalOutputOnOffPort);
        sonarDistance = new AnalogChannel(analogSensorPort);
    }        

   
    
    public void autonomous() {

      //Executes JoyPadDrive function, a substitute for the RobotDrive function provided in WPILib
        
        //Execute a slow, 1-second ramp to full speed. Hopefully, the robot mass is low enough that the motor power roughly matches the motor speed
        //i.e. motors are close to free-run level
        final double ImotorPower = 0.0; //Initial motor power
        final double FmotorPower = 0.5; //Final motor power
        final double updateTime = 0.005; //update motor power every 5 mS
        final double totalRampTime = 1.0; //time to ramp up, 1 sec
        
        for(double motorPower = ImotorPower; motorPower < FmotorPower; motorPower += (FmotorPower - ImotorPower) * (updateTime / totalRampTime)){
            
            JoyPadDrive(motorPower, 0.0, gamePad.getRawButton(Button1));
            
            Timer.delay(updateTime);
        }
        
        
        //Set wheel power to half forward, for 5 seconds.
        JoyPadDrive(0.5, 0.0, gamePad.getRawButton(Button1));
        Timer.delay(5.0); 
        
        //USING SONAR SENSOR
        
        //Lets pretend we have to stop 2 feet from a while we're driving at
        boolean isBeyond2Feet = true; //lets suppose we're not there yet
        while(isBeyond2Feet){
            
            //Get data from analog output.
            double analogVolts = sonarDistance.getVoltage();
            double feetDistance = 1.0; //use dummy value since conversion from volts to distance hasn't happened in code
            //convert voltage into distance
            //You should read the datasheet, I don't have the time to do this right now
            /*
             * Conversion code here
             */
            
            if(feetDistance < 2.0){ isBeyond2Feet = false; } //if closer than 2 feet, stop. If not, keep going
            else{ JoyPadDrive(0.3, 0.0, gamePad.getRawButton(Button1));
            }
          Timer.delay(0.005);
            
        }
            //Stop now
            JoyPadDrive(0.0, 0.0, gamePad.getRawButton(Button1));
        
        
        
      
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
     
     private void JoyPadDrive(double forward, double turn, boolean rawButton){
        
        leftJag1.set(forward - turn);
        leftJag2.set(forward - turn);
        rightJag1.set(forward + turn);
        rightJag2.set(forward + turn);
        centerJag1.set(forward + turn);
        
    }
    
    private void TypicalJoyPadDrive(){
        JoyPadDrive(gamePad.getRawAxis(ForwardAxis), gamePad.getRawAxis(TurnAxis), gamePad.getRawButton(Button1));
    }
    
    private void TurnSonarOn(){
        
        sonarOnOff.set(true);
    }
    
    private void TurnSonarOff(){
        sonarOnOff.set(false);
        
    }
}


