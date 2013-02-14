/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalOutput;



/**
 *
 * @author Alex
 */
public class MaxBotixHRLV implements IUltrasonicSensor {
    
    AnalogChannel myChannel;
    
    DigitalOutput sonarEnableDisable;
    
    Counter PWMCounter;
    
    final double InchesPerVolt = (102.4 / 30.48) * 12;       //Assume 5 volts
    final double FeetPerVolt = 102.4 / 30.48;
    final double CentimetersPerVolt = 102.4;
    final double MillimetersPerVolt = 1024.0;
    
    
    MaxBotixHRLV(int senseModule, int senseChannel,
                 int sonarEnableDisableModule, int sonarEnableDisablePin,
                 SonarInterfaceEnum interfaceType){
        
        if(interfaceType.useAnalog()){
            myChannel = new AnalogChannel(senseModule, senseChannel);
        }
        else if(interfaceType.usePWM()){
            //Not implemented, use counter in future
            //PWMCounter = new PWMCounter();
        }
        else if(interfaceType.useSerial()){
            //not implemented
        }
        
        sonarEnableDisable = new DigitalOutput(sonarEnableDisableModule, sonarEnableDisablePin);
        
    }
    
    public double returnDistanceFeet(){
        return myChannel.getVoltage() * FeetPerVolt;
    }
    public double returnDistanceInches(){
        return myChannel.getVoltage() * InchesPerVolt;
    }
    public double returnDistanceCentimeters(){
        return myChannel.getVoltage() * CentimetersPerVolt;
    }
    public double returnDistanceMillimeters(){
        return myChannel.getVoltage() * MillimetersPerVolt;
    }
    
    public void enableSampling(){ 
        sonarEnableDisable.set(true);
    }
    public void disableSampling(){  
        sonarEnableDisable.set(false);
    }
    public double millisSinceLastReading(){ 
        return 0.0; //Not implemented
    }
}
