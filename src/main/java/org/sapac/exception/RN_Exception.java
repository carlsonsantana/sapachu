/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.sapac.exception;

/**
 *
 * @author edson
 */
public class RN_Exception extends Exception{
    String customMessage = null;
    
    public RN_Exception(String message) {
        super();
        customMessage = message;
    }
    
    public boolean hasCustomMessage(){
        return customMessage != null;
    }

    public String getCustomMessage() {
        return customMessage;
    }
    
    
}
