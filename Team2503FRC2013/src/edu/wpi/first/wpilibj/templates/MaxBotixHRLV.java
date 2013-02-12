/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import java.util.Date;

/**
 *Class provides an interface for working with MaxBotix HRLV sensors (10x3)
 * These are the high resolution, newer model sensors.
 * 
 * @author Alex
 */
public class MaxBotixHRLV implements IUltrasonicSensor {
    
    final double CMPerVolt = 512 / 5.0; //Scaling factor for HRLV - Assumes 5V supplied to sensor -> 500 cm max
    
    final double FeetPerCM = 0.03281;
    
    final double MMPerCM = 10.00;
    
    final double InchesPerCM = 0.3937;
    
    final int BufferSize = 10;  //Hold 10 latest readings
    
    int BufferHead = 0;
    
    boolean averaging = false;
    
    
    AnalogChannel analogFeedback;   //Port on Analog Module to handle sensing via analog voltage
    
    DigitalOutput sonarEnableDisable;   //Output port connected to pin 4, can disable or enable sensor
    
    DigitalInput PWMChannel;    //Input channel used to determine range via PWM output.
    
    double [] latestReadingBuffer;
    
    Date timeCalculator;
    
    long previousReadingTime;
    
    
    //Constructor for using sensor
    //If using analog, first argument is 
    MaxBotixHRLV(int rangeFeedbackPort, int onOffPort, SonarInterfaceEnum interfaceType){
        if(interfaceType.useAnalog()){
            //Set up to use analog
            analogFeedback = new AnalogChannel(rangeFeedbackPort);
        }
        else if(interfaceType.usePWM()){
            //Set up PWM - not sure yet how to implement
        }
        else{
            //Nothing, no serial
        }
        sonarEnableDisable = new DigitalOutput(onOffPort);
        sonarEnableDisable.set(true); //by default leave sensor on
        
        latestReadingBuffer = new double[BufferSize];
        
        timeCalculator = new Date();
        previousReadingTime = timeCalculator.getTime();
        
    }
    
    
    
    public double returnDistanceFeet(){
        double bufferedVoltage = updateBuffer(analogFeedback.getVoltage());
        previousReadingTime = timeCalculator.getTime();
        return bufferedVoltage * CMPerVolt * FeetPerCM;
    }
    public double returnDistanceInches(){
        double bufferedVoltage = updateBuffer(analogFeedback.getVoltage());
        previousReadingTime = timeCalculator.getTime();
        return bufferedVoltage * CMPerVolt * InchesPerCM;
    }
    public double returnDistanceCentimeters(){
        double bufferedVoltage = updateBuffer(analogFeedback.getVoltage());
        previousReadingTime = timeCalculator.getTime();
        return bufferedVoltage * CMPerVolt;
    }
    public double returnDistanceMillimeters(){
        double bufferedVoltage = updateBuffer(analogFeedback.getVoltage());
        previousReadingTime = timeCalculator.getTime();
        return bufferedVoltage * CMPerVolt * MMPerCM;
    }
    
    public void enableSampling(){ 
        sonarEnableDisable.set(true);
    }
    public void disableSampling(){  
        sonarEnableDisable.set(false);
    }
    public double millisSinceLastReading(){ 
        return timeCalculator.getTime() - previousReadingTime;
    }
    
    private double updateBuffer(double voltage){
        
        BufferHead++;
        latestReadingBuffer[BufferHead] = voltage;
        
        if(averaging){
           return voltage; 
        }
        else{
            return voltage;
        }
    }
}
