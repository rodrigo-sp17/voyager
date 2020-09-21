/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* 
TODO
- Colors - build enums or constants for basic app color and inverted color
- 
*/ 

/**
 *
 * @author rodrigo
 */
class MonitorFrame extends javax.swing.JFrame {
    
    public PobView pobPane;
    
    
           
    public MonitorFrame() {
        initComponents();
        initCustomComponents();
        initViewLogic();        
    }
    
    /** 
     * This method initializes simple view logic, related to appearance of
     * components. All events related to business/domain logic are to be passed
     * to the presenter.
     */
    private void initViewLogic() {
        // Adds Main Menu buttons to a button group
        ButtonGroup group = new ButtonGroup();
        group.add(statusTab);
        group.add(pobTab);
        group.add(databaseTab);
        group.add(navTab);
        
        // Adds button color changer to Main Menu
        MainMenuViewHandler handler = new MainMenuViewHandler();
        statusTab.addItemListener(handler);
        pobTab.addItemListener(handler);
        databaseTab.addItemListener(handler);
        navTab.addItemListener(handler);                            
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
        jButton2 = new javax.swing.JButton();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 30));
        statusTab = new javax.swing.JToggleButton();
        navTab = new javax.swing.JToggleButton();
        pobTab = new javax.swing.JToggleButton();
        databaseTab = new javax.swing.JToggleButton();
        mainPane = new javax.swing.JPanel();
        statusPane = new javax.swing.JPanel();
        navPane = new javax.swing.JPanel();
        databasePanel = new javax.swing.JPanel();
        searchTextField = new javax.swing.JTextField();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        crewDBTable = new javax.swing.JTable();

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

        jButton2.setBackground(new java.awt.Color(0, 51, 102));
        jButton2.setFont(new java.awt.Font("Calibri Light", 0, 18)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Ostreiro");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setMaximumSize(new java.awt.Dimension(200, 35));
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);
        jToolBar1.add(filler1);

        statusTab.setBackground(new java.awt.Color(255, 255, 255));
        statusTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        statusTab.setSelected(true);
        statusTab.setText("Estado Geral");
        statusTab.setActionCommand("status");
        statusTab.setBorderPainted(false);
        statusTab.setContentAreaFilled(false);
        statusTab.setFocusable(false);
        statusTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        statusTab.setMaximumSize(new java.awt.Dimension(200, 35));
        statusTab.setName("statusTab"); // NOI18N
        statusTab.setOpaque(true);
        statusTab.setPreferredSize(new java.awt.Dimension(100, 25));
        statusTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(statusTab);

        navTab.setBackground(new java.awt.Color(0, 51, 102));
        navTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        navTab.setForeground(new java.awt.Color(255, 255, 255));
        navTab.setText("Navegação");
        navTab.setActionCommand("nav");
        navTab.setBorderPainted(false);
        navTab.setContentAreaFilled(false);
        navTab.setFocusable(false);
        navTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        navTab.setMaximumSize(new java.awt.Dimension(200, 35));
        navTab.setName("pobTab"); // NOI18N
        navTab.setOpaque(true);
        navTab.setPreferredSize(new java.awt.Dimension(100, 25));
        navTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(navTab);

        pobTab.setBackground(new java.awt.Color(0, 51, 102));
        pobTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        pobTab.setForeground(new java.awt.Color(255, 255, 255));
        pobTab.setText("POB");
        pobTab.setActionCommand("pob");
        pobTab.setBorderPainted(false);
        pobTab.setContentAreaFilled(false);
        pobTab.setFocusable(false);
        pobTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        pobTab.setMaximumSize(new java.awt.Dimension(200, 35));
        pobTab.setName("pobTab"); // NOI18N
        pobTab.setOpaque(true);
        pobTab.setPreferredSize(new java.awt.Dimension(100, 25));
        pobTab.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(pobTab);

        databaseTab.setBackground(new java.awt.Color(0, 51, 102));
        databaseTab.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        databaseTab.setForeground(new java.awt.Color(255, 255, 255));
        databaseTab.setText("Banco de Dados");
        databaseTab.setActionCommand("database");
        databaseTab.setBorderPainted(false);
        databaseTab.setContentAreaFilled(false);
        databaseTab.setFocusable(false);
        databaseTab.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        databaseTab.setMaximumSize(new java.awt.Dimension(200, 35));
        databaseTab.setName("databaseTab"); // NOI18N
        databaseTab.setOpaque(true);
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

        databasePanel.setBackground(new java.awt.Color(255, 255, 255));
        databasePanel.setName("databasePanel"); // NOI18N

        searchTextField.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        searchTextField.setToolTipText("Digite aqui para filtrar");

        editButton.setBackground(new java.awt.Color(0, 51, 102));
        editButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        editButton.setForeground(new java.awt.Color(255, 255, 255));
        editButton.setText("Editar");
        editButton.setActionCommand("edit");

        deleteButton.setBackground(new java.awt.Color(200, 0, 0));
        deleteButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        deleteButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteButton.setText("Deletar");
        deleteButton.setActionCommand("delete");
        deleteButton.setEnabled(false);

        crewDBTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        crewDBTable.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        crewDBTable.setToolTipText("");
        crewDBTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        crewDBTable.setFillsViewportHeight(true);
        crewDBTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(crewDBTable);
        crewDBTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        javax.swing.GroupLayout databasePanelLayout = new javax.swing.GroupLayout(databasePanel);
        databasePanel.setLayout(databasePanelLayout);
        databasePanelLayout.setHorizontalGroup(
            databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(databasePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(databasePanelLayout.createSequentialGroup()
                        .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 759, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 120, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
        );
        databasePanelLayout.setVerticalGroup(
            databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(databasePanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(databasePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)
                .addGap(12, 12, 12))
        );

        mainPane.add(databasePanel, "databaseCard");

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
    
    void initCustomComponents() {
        pobPane = new PobView();        
        mainPane.add(pobPane, "pobCard");
    }
    
    /**
     * This method sets visual properties of the CrewDB JTable.
     * It is NOT dynamic - manual update when JTable changes data is required.
     */    
    void setCrewDBTableFormat() {
        // Sets Center Alighment render for columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        centerRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);

        // Sets Vertical Center alighment render for columns
        DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
        midRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);
        
        // Defines columns visual properties
        crewDBTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        crewDBTable.getColumnModel().getColumn(0).setMinWidth(4);
        crewDBTable.getColumnModel().getColumn(0).setMaxWidth(100);
        crewDBTable.getColumnModel().getColumn(1).setCellRenderer(midRenderer);
        crewDBTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(2).setPreferredWidth(150);        
        crewDBTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);        
        crewDBTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        crewDBTable.getColumnModel().getColumn(8).setCellRenderer(centerRenderer);        
    }    
    
    
    // Changes color of buttons when selected
    class MainMenuViewHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            JToggleButton source = (JToggleButton) e.getSource();
            if (e.getStateChange() == ItemEvent.SELECTED) {
                source.setBackground(new Color(255, 255, 255));
                source.setForeground(new Color(0, 0, 0));
                source.setFont(new Font("Calibri Light", 1, 18));
            } else {
                source.setBackground(new Color(0, 51, 102));
                source.setForeground(new Color(255, 255, 255));
                source.setFont(new Font("Calibri Light", 0, 18));
            }                
        }      
    }    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JTable crewDBTable;
    javax.swing.JPanel databasePanel;
    javax.swing.JToggleButton databaseTab;
    javax.swing.JButton deleteButton;
    javax.swing.JButton editButton;
    private javax.swing.Box.Filler filler1;
    javax.swing.JButton jButton2;
    private javax.swing.JMenuItem jMenuItem1;
    javax.swing.JScrollPane jScrollPane1;
    javax.swing.JToolBar jToolBar1;
    javax.swing.JPanel mainPane;
    javax.swing.JPanel navPane;
    javax.swing.JToggleButton navTab;
    javax.swing.JToggleButton pobTab;
    javax.swing.JTextField searchTextField;
    javax.swing.JPanel statusPane;
    javax.swing.JToggleButton statusTab;
    // End of variables declaration//GEN-END:variables
        
}
