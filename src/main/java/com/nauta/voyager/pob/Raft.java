/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.pob;

/**
 *
 * @author rodrigo
 */
public enum Raft {
    
    PORT("BOMBORDO", "PORT"),
    STBD("BORESTE", "STARBOARD");
    
    private final String textPT;
    private final String textEN;
    
        
    Raft(String port, String stbd) {
        this.textPT = port;
        this.textEN = stbd;        
    }    
    
    public String textPT() { return textPT; }
    
    public String textEN() { return textEN; }
}
 