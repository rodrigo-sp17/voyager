/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import com.nauta.voyager.people.PeopleView;
import com.nauta.voyager.pob.PobView;
import javax.swing.ImageIcon;

/**
 *
 * @author rodrigo
 */
public class MainView extends javax.swing.JFrame {
    
    public PobView pobPane;
    public PeopleView peoplePane;    
           
    public MainView() {
        initComponents();
        initCustomComponents();
        initViewLogic();
        
        // Sets icon
        ImageIcon icon = new ImageIcon(ClassLoader
                .getSystemResource("icon.png"));
        setIconImage(icon.getImage());
    }
    
    /* 
     * This method initializes simple view logic, related to appearance of
     * components. All events related to business/domain logic are to be passed
     * to the presenter.
     */
    private void initViewLogic() {
        
    }        
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jToolBar1 = new javax.swing.JToolBar();
        vesselButton = new javax.swing.JToggleButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 30));
        statusTab = new javax.swing.JToggleButton();
        navTab = new javax.swing.JToggleButton();
        pobTab = new javax.swing.JToggleButton();
        databaseTab = new javax.swing.JToggleButton();
        mainPane = new javax.swing.JPanel();
        statusPane = new javax.swing.JPanel();
        navPane = new javax.swing.JPanel();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Voyager");
        setBackground(new java.awt.Color(102, 102, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setName("main_frame"); // NOI18N

        jToolBar1.setBackground(new java.awt.Color(0, 51, 102));
        jToolBar1.setBorder(null);
        jToolBar1.setFloatable(false);
        jToolBar1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jToolBar1.setRollover(true);
        jToolBar1.setFocusable(false);

        vesselButton.setBackground(new java.awt.Color(255, 255, 255));
        vesselButton.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        vesselButton.setForeground(new java.awt.Color(255, 255, 255));
        vesselButton.setText("Embarcação");
        vesselButton.setActionCommand("vessel");
        vesselButton.setBorderPainted(false);
        vesselButton.setFocusable(false);
        vesselButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        vesselButton.setMaximumSize(new java.awt.Dimension(200, 35));
        vesselButton.setName("statusTab"); // NOI18N
        vesselButton.setPreferredSize(new java.awt.Dimension(100, 25));
        vesselButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(vesselButton);
        jToolBar1.add(filler1);

        statusTab.setBackground(new java.awt.Color(0, 51, 102));
        statusTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        statusTab.setForeground(new java.awt.Color(255, 255, 255));
        statusTab.setText("Estado Geral");
        statusTab.setActionCommand("status");
        statusTab.setBorderPainted(false);
        statusTab.setFocusable(false);
        statusTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        statusTab.setMaximumSize(new java.awt.Dimension(200, 35));
        statusTab.setName("statusTab"); // NOI18N
        statusTab.setPreferredSize(new java.awt.Dimension(100, 25));
        statusTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(statusTab);

        navTab.setBackground(new java.awt.Color(0, 51, 102));
        navTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        navTab.setForeground(new java.awt.Color(255, 255, 255));
        navTab.setText("Navegação");
        navTab.setActionCommand("nav");
        navTab.setBorderPainted(false);
        navTab.setFocusable(false);
        navTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navTab.setMaximumSize(new java.awt.Dimension(200, 35));
        navTab.setName("pobTab"); // NOI18N
        navTab.setPreferredSize(new java.awt.Dimension(100, 25));
        navTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(navTab);

        pobTab.setBackground(new java.awt.Color(0, 51, 102));
        pobTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        pobTab.setForeground(new java.awt.Color(255, 255, 255));
        pobTab.setSelected(true);
        pobTab.setText("POB");
        pobTab.setActionCommand("pob");
        pobTab.setBorderPainted(false);
        pobTab.setFocusable(false);
        pobTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pobTab.setMaximumSize(new java.awt.Dimension(200, 35));
        pobTab.setName("pobTab"); // NOI18N
        pobTab.setPreferredSize(new java.awt.Dimension(100, 25));
        pobTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(pobTab);

        databaseTab.setBackground(new java.awt.Color(0, 51, 102));
        databaseTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        databaseTab.setForeground(new java.awt.Color(255, 255, 255));
        databaseTab.setText("Banco de Dados");
        databaseTab.setActionCommand("database");
        databaseTab.setBorderPainted(false);
        databaseTab.setFocusable(false);
        databaseTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        databaseTab.setMaximumSize(new java.awt.Dimension(200, 35));
        databaseTab.setName("databaseTab"); // NOI18N
        databaseTab.setPreferredSize(new java.awt.Dimension(100, 25));
        databaseTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(databaseTab);

        mainPane.setName("mainPane"); // NOI18N
        mainPane.setLayout(new java.awt.CardLayout());

        statusPane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout statusPaneLayout = new javax.swing.GroupLayout(statusPane);
        statusPane.setLayout(statusPaneLayout);
        statusPaneLayout.setHorizontalGroup(
            statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1125, Short.MAX_VALUE)
        );
        statusPaneLayout.setVerticalGroup(
            statusPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );

        mainPane.add(statusPane, "statusCard");

        navPane.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout navPaneLayout = new javax.swing.GroupLayout(navPane);
        navPane.setLayout(navPaneLayout);
        navPaneLayout.setHorizontalGroup(
            navPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1125, Short.MAX_VALUE)
        );
        navPaneLayout.setVerticalGroup(
            navPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 708, Short.MAX_VALUE)
        );

        mainPane.add(navPane, "navCard");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(mainPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initCustomComponents() {
        pobPane = new PobView();        
        mainPane.add(pobPane, "pobCard");
        
        peoplePane = new PeopleView();
        mainPane.add(peoplePane, "databaseCard");        
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JToggleButton databaseTab;
    public javax.swing.Box.Filler filler1;
    public javax.swing.JMenuItem jMenuItem1;
    javax.swing.JToolBar jToolBar1;
    javax.swing.JPanel mainPane;
    javax.swing.JPanel navPane;
    javax.swing.JToggleButton navTab;
    javax.swing.JToggleButton pobTab;
    javax.swing.JPanel statusPane;
    javax.swing.JToggleButton statusTab;
    javax.swing.JToggleButton vesselButton;
    // End of variables declaration//GEN-END:variables
        
}
