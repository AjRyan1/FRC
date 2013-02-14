/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *
 * @author Alex
 * 
 * Interface for working with ultrasonic sensors. Currently in development to support the MaxBotix class of sensors -
 * others with tri-state I/O pins like the Ping and other Chinese knock-offs are not supported atm.
 */
public interface IUltrasonicSensor {
    
    //Basic functions for getting distance data from sensor
    
    double returnDistanceFeet();
    double returnDistanceInches();
    double returnDistanceCentimeters();
    double returnDistanceMillimeters();
    
    void enableSampling(); //Turns a sensor on - do not use if sensor is being automatically handled with SonarManager
    void disableSampling();  //Turns a sensor off - do not use if sensor is being automatically handled with SonarManager
    
    double millisSinceLastReading(); //Get time since last update from this sensor was taken.
    
    
    
    
}
