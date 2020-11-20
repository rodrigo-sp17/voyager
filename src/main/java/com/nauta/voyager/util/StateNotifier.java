/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author rodrigo
 * 
 * This class is an implementation of an observed class in an Observer
 * Synchronization application.
 * It is used by Models in an MVP/MVC architecture to notify interested 
 * observers which implement the StateListener interface. 
 */
public class StateNotifier {
    private static final Logger log = LogManager.getLogger();
    
    private List<StateListener> listeners;
    
    private Set<String> screamers;
    
    /**
     * Adds a StateListener object to the list of listeners of this class
     * 
     * @param sl object that implements the StateListener interface
     */
    public void addStateListener(StateListener sl) {
        if (listeners == null) {
            listeners = new ArrayList<>();
        }
        listeners.add(sl);        
    }
    
    /**
     * Calls onListenedStateChanged() method on StateListener objects added to 
     * the listeners list.
     */
    public void fireStateChanged() {
        listeners.forEach(l -> {
            l.onListenedStateChanged();            
        });
        log.info("State changed returned");
    }
    
    public void addScreamer(String... screamers) {
        this.screamers = new HashSet<>();
        for (String s : screamers) {
            this.screamers.add(s);                    
        }
    }
    
    public boolean isScreaming(String screamer) {
        return screamers.contains(screamer);
    }
    
    public void removeScreamer(String screamer) {
        screamers.remove(screamer);
    }
        
    /**
     * Returns the class the fires the change of state
     * 
     * @return class that originated
     */
    public Class<?> getSource() {
        return this.getClass();
    }
}
