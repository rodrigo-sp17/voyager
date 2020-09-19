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
    
    private int functionId;
    private String functionPrefix;
    private String functionDescription;    
    
    public Function(int id, String prefix, String description) {
        this.functionId = id;
        this.functionPrefix = prefix;
        this.functionDescription = description;
    }
    
    public int getFunctionId() {
        return functionId;
    }

    public void setFunctionId(int functionId) {
        this.functionId = functionId;
    }

    public String getFunctionPrefix() {
        return functionPrefix;
    }

    public void setFunctionPrefix(String functionPrefix) {
        this.functionPrefix = functionPrefix;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }   
    
}
