/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.time.format.DateTimeFormatter;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;

/**
 *
 * @author rodrigo
 */
public class PobView extends javax.swing.JPanel {
    
    // Defines the date format that is used on view components
    public final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    /**
     * Creates new form PobView
     */
    public PobView() {
        initComponents();       
        
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        pobTable = new javax.swing.JTable();
        pobDateField = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pobSizeField = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        crewField = new javax.swing.JComboBox<>();
        deleteMemberButton = new javax.swing.JButton();
        addMemberButton = new javax.swing.JButton();
        printPobButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1120, 700));

        pobTable.setBackground(new java.awt.Color(255, 255, 255));
        pobTable.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        pobTable.setFont(new java.awt.Font("Calibri", 0, 12)); // NOI18N
        pobTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        pobTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pobTable.setFillsViewportHeight(true);
        pobTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(pobTable);

        pobDateField.setBackground(new java.awt.Color(255, 255, 255));
        pobDateField.setColumns(8);
        pobDateField.setForeground(new java.awt.Color(0, 0, 0));
        try {
            pobDateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        pobDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pobDateField.setToolTipText("Formato: dd/mm/yyyy");
        pobDateField.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Data:");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("POB:");

        pobSizeField.setEditable(false);
        pobSizeField.setBackground(new java.awt.Color(255, 255, 255));
        pobSizeField.setColumns(3);
        pobSizeField.setForeground(new java.awt.Color(0, 0, 0));
        pobSizeField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#0"))));
        pobSizeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pobSizeField.setText("25");
        pobSizeField.setToolTipText("Formato: dd/mm/yyyy");
        pobSizeField.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Verdana", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Turma:");

        crewField.setBackground(new java.awt.Color(255, 255, 255));
        crewField.setFont(new java.awt.Font("Verdana", 0, 18)); // NOI18N
        crewField.setForeground(new java.awt.Color(0, 0, 0));
        crewField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "A", "B", "?" }));

        deleteMemberButton.setBackground(new java.awt.Color(255, 51, 51));
        deleteMemberButton.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        deleteMemberButton.setForeground(new java.awt.Color(255, 255, 255));
        deleteMemberButton.setText("-");
        deleteMemberButton.setActionCommand("delete");

        addMemberButton.setBackground(new java.awt.Color(51, 204, 0));
        addMemberButton.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        addMemberButton.setForeground(new java.awt.Color(255, 255, 255));
        addMemberButton.setText("+");
        addMemberButton.setActionCommand("add");

        printPobButton.setBackground(new java.awt.Color(255, 255, 255));
        printPobButton.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        printPobButton.setForeground(new java.awt.Color(0, 0, 0));
        printPobButton.setText("Imprimir");
        printPobButton.setActionCommand("print");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pobDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pobSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(25, 25, 25)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 324, Short.MAX_VALUE)
                        .addComponent(printPobButton)
                        .addGap(100, 100, 100)
                        .addComponent(addMemberButton)
                        .addGap(18, 18, 18)
                        .addComponent(deleteMemberButton))
                    .addComponent(jScrollPane2))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(161, 161, 161)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteMemberButton)
                    .addComponent(addMemberButton)
                    .addComponent(printPobButton)
                    .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pobSizeField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(pobDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 483, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12))
        );
    }// </editor-fold>//GEN-END:initComponents

    public JTable getPobTable() {
        return pobTable;
    }

    public JButton getAddMemberButton() {
        return addMemberButton;
    }

    public JButton getDeleteMemberButton() {
        return deleteMemberButton;
    }

    public JFormattedTextField getPobDateField() {
        return pobDateField;
    }

    public JFormattedTextField getPobSizeField() {
        return pobSizeField;
    }

    public JButton getPrintPobButton() {
        return printPobButton;
    }
    
    public JComboBox<String> getCrewField() {
        return crewField;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMemberButton;
    private javax.swing.JComboBox<String> crewField;
    private javax.swing.JButton deleteMemberButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFormattedTextField pobDateField;
    private javax.swing.JFormattedTextField pobSizeField;
    private javax.swing.JTable pobTable;
    private javax.swing.JButton printPobButton;
    // End of variables declaration//GEN-END:variables
}
