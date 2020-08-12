/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author rodrigo
 * 
 * This class is an implementation of an observed class in an Observer Synchronization
 * application.
 * It is used by Models in an MVP/MVC architecture to notify interested observers which
 * implement the StateListener interface.
 * 
 */
class StateNotifier {
    private List<StateListener> listeners;
    
    /**
     * Adds a StateListener object to the list of listeners of this class
     * @param sl 
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
        System.out.println("State changed returned");
    }
    
    /**
     * Returns the class the fires the change of state
     * @return 
     */
    public Class<?> getSource() {
        return this.getClass();
    }
}
