/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.wpi.first.wpilibj.templates;

/**
 *  Enum used for selecting which interface to support on the cRio
 *  Currently, plans for only PWM and analog output are going to be supported (not going to bother with serial)
 *  
 * @author Alex
 */
public class SonarInterfaceEnum {
    
    public final static int useAnalog = 1;
    public final static int usePWM = 2;
    public final static int useSerial = 3;
    
    int value;
    
    SonarInterfaceEnum(int value){
        this.value = value;
    }
    
    public boolean useAnalog(){
        return value == useAnalog;
    }
    
    public boolean usePWM(){
        return value == usePWM;
    }
    
    public boolean useSerial(){
        return value == useSerial;
    }
    
    
    
    
}
