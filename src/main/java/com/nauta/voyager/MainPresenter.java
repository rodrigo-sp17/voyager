/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.service.VesselService;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.ButtonGroup;
import javax.swing.JToggleButton;

/**
 *
 * @author rodrigo
 */
public class MainPresenter implements WindowListener {
    
    private final MainView view;
    private VesselService vesselService;
    //private final VoyagerModel model;
    
    public MainPresenter(MainView view) {
        this.view = view;
        vesselService = ServiceFactory.getVesselService();
        initPresentationLogic();
        readGUIStateFromDomain();
    }
    
    private void initPresentationLogic() {
        view.addWindowListener(this);
                
        // Sets menu buttons for event handling
        MainMenuHandler menuHandler = new MainMenuHandler();
        //view.statusTab.addActionListener(menuHandler);        
        view.pobTab.addActionListener(menuHandler);        
        view.databaseTab.addActionListener(menuHandler);        
        //view.navTab.addActionListener(menuHandler);
        
        // Adds Main Menu buttons to a button group
        ButtonGroup group = new ButtonGroup();
        //group.add(view.statusTab);
        group.add(view.pobTab);
        group.add(view.databaseTab);
        //group.add(view.navTab);
        
        // Adds button color changer to Main Menu
        MainMenuViewHandler handler = new MainMenuViewHandler();
        //view.statusTab.addItemListener(handler);
        view.pobTab.addItemListener(handler);
        view.databaseTab.addItemListener(handler);
        //view.navTab.addItemListener(handler);
        
        // REMOVE THIS AFTER IMPLEMENTING
        view.statusTab.setVisible(false);
        view.navTab.setVisible(false);
    }
    
    private void readGUIStateFromDomain() {
        view.vesselButton.setText(vesselService.getVessel());
        //CardLayout cl = (CardLayout) view.mainPane.getLayout();
        //cl.show(view.mainPane, "pobCard");
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        VoyagerContext.getContext().endContext();
        view.dispose();
        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
    
    private class MainMenuHandler implements ActionListener {
        // Listens for main menu buttons, handles their initial GUI State
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) view.mainPane.getLayout();        
        
            switch (e.getActionCommand()) {
                case "status": 
                    cl.show(view.mainPane, "statusCard");
                    break;
                case "nav": 
                    cl.show(view.mainPane, "navPane");
                    break;
                case "pob": 
                    cl.show(view.mainPane, "pobCard");
                    break;
                case "database": 
                    cl.show(view.mainPane, "databaseCard");
                    break;
            }
        }
    }
    
    // Changes color of buttons when selected
    private class MainMenuViewHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                source.setBackground(Color.WHITE);
                source.setForeground(new Color(0, 0, 0));                
            } else {
                source.setBackground(new Color(0, 51, 102));
                source.setForeground(new Color(255, 255, 255));                
            }                
        }      
    }    
    
    
}
