/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.entity;

/**
 *
 * @author rodrigo
 */
public enum Raft {
    
    PORT("P" , "BOMBORDO", "PORT"),
    STBD("S" ,"BORESTE", "STARBOARD");
    
    private final String textId;
    private final String textPT;
    private final String textEN;
    
        
    Raft(String textId, String port, String stbd) {
        this.textId = textId;
        this.textPT = port;
        this.textEN = stbd;        
    }    
    
    public String textPT() { return textPT; }
    
    public String textEN() { return textEN; }
    
    public String id() { return textId; }
    
    public static Raft parse(String value) {
        if (value.equals("P") || value.equals("p")) {
            return PORT;
        } else if (value.equals("S") || value.equals("s")) {
            return STBD;
        } else {
            throw new IllegalArgumentException("Value is not a valid Raft");
        }
    }    
}
 