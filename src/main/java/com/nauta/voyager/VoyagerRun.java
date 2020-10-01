/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import com.nauta.voyager.people.PeoplePresenter;
import com.nauta.voyager.pob.PobPresenter;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
        
/*
TODO
- Config loading
- Error log and exception handling
- 
 *//**
 *
 * @author rodrigo
 */
public class VoyagerRun {
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Tries to get FlatLightLaf theme
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }
        
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {            
            public void run() {
                MainView mainView = new MainView();                
                mainView.setVisible(true);
                
                VoyagerModel model = new VoyagerModel();
                
                MainPresenter presenter = new MainPresenter(mainView);
                
                PeoplePresenter peoplePresenter = 
                        new PeoplePresenter(mainView.peoplePane, model);
                PobPresenter pobPresenter = 
                        new PobPresenter(mainView.pobPane, model);
            }
        });
    }
    
}
