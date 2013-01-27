/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalOutput;

/**
 *Class provides an interface for working with MaxBotix HRLV sensors (10x3)
 * These are the high resolution, newer model sensors.
 * 
 * @author Alex
 */
public class MaxBotixHRLV implements IUltrasonicSensor {
    
    AnalogChannel analogFeedback;
    DigitalOutput sonarEnableDisable;
    
    
    MaxBotixHRLV(int channelPort1, int channelPort2, SonarInterfaceEnum interfaceType){
        
    }
    
    
    
    public double returnDistanceFeet(){
        return 0.0;
    }
    public double returnDistanceInches(){
        return 0.0;
    }
    public double returnDistanceCentimeters(){
        return 0.0;
    }
    public double returnDistanceMillimeters(){
        return 0.0;
    }
    
    public void enableSampling(){ 
        return;
    }
    public void disableSampling(){  
        return;
    }
    public double millisSinceLastReading(){ 
        return 0.0;
    }
}
