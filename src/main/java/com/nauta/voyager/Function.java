/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

/**
 *
 * @author rodrigo
 */
public class Function {
        
    public static final String TAG = Function.class.getSimpleName();
    
    private final int functionId;
    private final String functionPrefix;
    private final String functionDescription;
    private final int functionVariation;
    
    
    public Function(int id, String prefix, String description, int variation) {
        this.functionId = id;
        this.functionPrefix = prefix;
        this.functionDescription = description;
        this.functionVariation = variation;
    }
    
    public Function(int id, String prefix, String description) {
        this.functionId = id;
        this.functionPrefix = prefix;
        this.functionDescription = description;
        this.functionVariation = 0;
    }
    
    public int getFunctionId() {
        return functionId;
    }   

    public String getFunctionPrefix(boolean shouldShowVariation) {
        if (shouldShowVariation) {
            return functionPrefix + "-" + Integer.toString(functionVariation);
        } else {
            return functionPrefix;            
        }        
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public String getFormalDescription() {
        if (functionVariation == 0) {
            return String.format("%s - %s",
                    functionPrefix,
                    functionDescription);
        } else {
            return String.format("%s - %l - %s",
                    functionPrefix,
                    functionVariation,
                    functionDescription);
        }
    }
    
    public String getIdentifier() {
        return TAG + functionId;
    }
}
