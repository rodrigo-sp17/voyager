/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;
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
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {            
            public void run() {
                MonitorFrame view = new MonitorFrame();
                view.setVisible(true);
                
                CrewMemberModel model = new CrewMemberModel();
                
                CrewPresenter crewPresenter = new CrewPresenter(view, model);
                PobPresenter pobPresenter = new PobPresenter(view.pobPane, model);
            }
        });
    }
    
}
