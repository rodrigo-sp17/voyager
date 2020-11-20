/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.util.DatabaseUtil;
import com.nauta.voyager.people.PeoplePresenter;
import com.nauta.voyager.pob.PobPresenter;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

        
/**
 * @author rodrigo
 */
public class VoyagerRun {
    
    private static final Logger log = LogManager.getLogger();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        // Tries to get FlatLightLaf theme
        try {
            UIManager.setLookAndFeel( new FlatLightLaf() );
        } catch( UnsupportedLookAndFeelException ex ) {            
            log.warn("Failed to initialize Laf theme");
        }
        
        
        /* Create and display the app */
        java.awt.EventQueue.invokeLater(new Runnable() {            
            @Override
            public void run() {
                log.info("Starting Voyager...");
                VoyagerContext context = new VoyagerContext();
                DatabaseUtil du = DatabaseUtil.init();                
                ServiceFactory sf = new ServiceFactory(du.getSessionFactory());

                //VoyagerModel model = new VoyagerModel();
                MainView mainView = new MainView();                

                MainPresenter presenter = new MainPresenter(mainView);

                PeoplePresenter peoplePresenter = 
                        new PeoplePresenter(mainView.peoplePane, context);
                PobPresenter pobPresenter = 
                        new PobPresenter(mainView.pobPane, context);

                mainView.setVisible(true);                
                
            }
            // TODO - top level exception handling
            // TODO - use logger
        });
    }
    
}
