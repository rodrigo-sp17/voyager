/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.dialog;

import com.nauta.voyager.people.Person;
import com.nauta.voyager.people.Function;
import com.nauta.voyager.VoyagerModel;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.*;
import java.util.*;
import java.time.LocalDate;
import java.time.format.*;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;


/**
 *
 * @author rodrigo
 */
public class EditPersonDialog extends javax.swing.JDialog {
    
    private static final String TAG = EditPersonDialog.class.getSimpleName();
    
    private static final int MAX_NAME_SIZE = 60;
    private static final int MAX_COMPANY_SIZE = 40;
    private static final int MAX_SISPAT_SIZE = 8;
    private static final int MAX_NATIONALITY_SIZE = 20;
    private static final int MAX_CIR_SIZE = 14;       
    
    private Person person;
    private final VoyagerModel model;     
    private boolean editMode = false;

    private List<Function> functions;
    
    // Format of the dates used on the dialog
    private final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");

    /**
     * Use this constructor to <b>add</b> a previously inexistent CrewMember to
     * the model.
     * <p>
 WARNING: providing an empty Person with the intention to insert a new
 one into the model will trigger model UPDATE function instead of INSERT,
 causing unexpected behavior.
     * 
     * @param frame the owner frame, which the dialog will attempt to return
     *              to on completion
     * @param modal if true, creates JDialog as a modal form 
     * @param model the VoyagerModel instance the dialog uses to persist
              its addition/changes  
     * 
     */
    public EditPersonDialog(Frame frame, boolean modal, VoyagerModel model){
        super(frame, modal);
        
        this.person = new Person();
        this.editMode = false;
        
        // If model is null, exits application - saving would be impossible
        if (model != null) {
            this.model = model;
        } else {                        
            throw new IllegalArgumentException(TAG 
                    + " - Model is null. Exiting dialog");
        }        
        
        initComponents();
        initPresentationLogic();
        readGUIState();        
    }
    
    /**
     * Use this constructor to <b>edit</b> an existent CrewMember in the model.
     * <p>
 WARNING: providing a non-existent Person will trigger model UPDATE
 instead of INSERT function. The modifications will NOT be persisted.
     * 
     * @param frame     the owner frame, which the dialog will attempt to return
     *                  to on completion
     * @param modal     if true, creates JDialog as a modal form     *   
     * @param model     the VoyagerModel instance the dialog uses to persist
                  its changes
     * @param person    the Person instance to be edited. Must be a 
                  previously existent one  
     */
    public EditPersonDialog(
            Frame frame,
            boolean modal,
            VoyagerModel model,
            Person person) {
        super(frame, modal);
        
        /*
         * person should not be assumed to be null. Having a null person means
         * probable wrong usage or bug, so the dialog should return.
         */
        if (person == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Person is null. Exiting dialog" );
        } else {
            this.person = person;
            editMode = true;
        }
        
        // If model is null, exits application - saving would be impossible
        if (model == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Model is null. Exiting dialog");
        } else {
            this.model = model;
        }       
        
        initComponents();
        initPresentationLogic();
        readGUIState();        
    }
    
    /**
     * Use this constructor to <b>add</b> a previously inexistent CrewMember to
     * the model.
     * <p>
 WARNING: providing an empty Person with the intention to insert a new
 one into the model will trigger model UPDATE function instead of INSERT,
 causing unexpected behavior.
     * 
     * @param dialog the owner Dialog, which the dialog will attempt to return
     *              to on completion
     * @param modal if true, creates JDialog as a modal form 
     * @param model the VoyagerModel instance the dialog uses to persist
              its addition/changes  
     * 
     */
    public EditPersonDialog(Dialog dialog, boolean modal, VoyagerModel model){
        super(dialog, modal);
        
        this.person = new Person();
        this.editMode = false;
        
        // If model is null, exits application - saving would be impossible
        if (model != null) {
            this.model = model;
        } else {                        
            throw new IllegalArgumentException(TAG 
                    + " - Model is null. Exiting dialog");
        }        
        
        initComponents();
        initPresentationLogic();
        readGUIState();        
    }
    
    /**
     * Use this constructor to <b>edit</b> an existent CrewMember in the model.
     * <p>
 WARNING: providing a non-existent Person will trigger model UPDATE
 instead of INSERT function. The modifications will NOT be persisted.
     * 
     * @param dialog     the owner Dialog, which the dialog will attempt to return
     *                  to on completion
     * @param modal     if true, creates JDialog as a modal form     *   
     * @param model     the VoyagerModel instance the dialog uses to persist
                  its changes
     * @param person    the Person instance to be edited. Must be a 
                  previously existent one  
     */
    public EditPersonDialog(
            Dialog dialog,
            boolean modal,
            VoyagerModel model,
            Person person) {
        super(dialog, modal);
        
        /*
         * person should not be assumed to be null. Having a null person means
         * probable wrong usage or bug, so the dialog should return.
         */
        if (person == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Person is null. Exiting dialog" );
        } else {
            this.person = person;
            editMode = true;
        }
        
        // If model is null, exits application - saving would be impossible
        if (model == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Model is null. Exiting dialog");
        } else {
            this.model = model;
        }       
        
        initComponents();
        initPresentationLogic();
        readGUIState();        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        dialogPanel = new javax.swing.JPanel();
        nameField = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        companyField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        sispatField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        nationalityField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cirField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        functionField = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        birthDateField = new javax.swing.JFormattedTextField();
        cirExpDateField = new javax.swing.JFormattedTextField();
        boardingDataButton = new javax.swing.JButton();

        jButton1.setBackground(new java.awt.Color(0, 0, 102));
        jButton1.setFont(new java.awt.Font("Calibri", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Cancelar");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Pessoal");
        setBackground(new java.awt.Color(255, 255, 255));
        setResizable(false);

        dialogPanel.setBackground(new java.awt.Color(255, 255, 255));
        dialogPanel.setPreferredSize(new java.awt.Dimension(36, 16));

        nameField.setBackground(new java.awt.Color(255, 255, 255));
        nameField.setColumns(60);
        nameField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        nameField.setForeground(new java.awt.Color(0, 0, 0));
        nameField.setName(""); // NOI18N

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setLabelFor(nameField);
        jLabel1.setText("Nome");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setFocusable(false);
        jLabel1.setInheritsPopupMenu(false);
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerifyInputWhenFocusTarget(false);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        companyField.setBackground(new java.awt.Color(255, 255, 255));
        companyField.setColumns(40);
        companyField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        companyField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setLabelFor(nameField);
        jLabel2.setText("Empresa");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setFocusable(false);
        jLabel2.setInheritsPopupMenu(false);
        jLabel2.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel2.setRequestFocusEnabled(false);
        jLabel2.setVerifyInputWhenFocusTarget(false);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setLabelFor(nameField);
        jLabel3.setText("Função");
        jLabel3.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel3.setFocusable(false);
        jLabel3.setInheritsPopupMenu(false);
        jLabel3.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel3.setRequestFocusEnabled(false);
        jLabel3.setVerifyInputWhenFocusTarget(false);
        jLabel3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        sispatField.setBackground(new java.awt.Color(255, 255, 255));
        sispatField.setColumns(8);
        sispatField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        sispatField.setForeground(new java.awt.Color(0, 0, 0));
        sispatField.setMaximumSize(new java.awt.Dimension(176, 176));

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setLabelFor(nameField);
        jLabel4.setText("SISPAT");
        jLabel4.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel4.setFocusable(false);
        jLabel4.setInheritsPopupMenu(false);
        jLabel4.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel4.setRequestFocusEnabled(false);
        jLabel4.setVerifyInputWhenFocusTarget(false);
        jLabel4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setLabelFor(nameField);
        jLabel5.setText("Data de Nascimento");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel5.setFocusable(false);
        jLabel5.setInheritsPopupMenu(false);
        jLabel5.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel5.setRequestFocusEnabled(false);
        jLabel5.setVerifyInputWhenFocusTarget(false);
        jLabel5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        nationalityField.setBackground(new java.awt.Color(255, 255, 255));
        nationalityField.setColumns(20);
        nationalityField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        nationalityField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setLabelFor(nameField);
        jLabel6.setText("Nacionalidade");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel6.setFocusable(false);
        jLabel6.setInheritsPopupMenu(false);
        jLabel6.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel6.setRequestFocusEnabled(false);
        jLabel6.setVerifyInputWhenFocusTarget(false);
        jLabel6.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        cirField.setBackground(new java.awt.Color(255, 255, 255));
        cirField.setColumns(14);
        cirField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        cirField.setForeground(new java.awt.Color(0, 0, 0));
        cirField.setToolTipText("");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setLabelFor(nameField);
        jLabel7.setText("Nº CIR ou ID");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel7.setFocusable(false);
        jLabel7.setInheritsPopupMenu(false);
        jLabel7.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel7.setRequestFocusEnabled(false);
        jLabel7.setVerifyInputWhenFocusTarget(false);
        jLabel7.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setLabelFor(nameField);
        jLabel8.setText("Validade CIR/ID");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel8.setFocusable(false);
        jLabel8.setInheritsPopupMenu(false);
        jLabel8.setPreferredSize(new java.awt.Dimension(36, 16));
        jLabel8.setRequestFocusEnabled(false);
        jLabel8.setVerifyInputWhenFocusTarget(false);
        jLabel8.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        functionField.setBackground(new java.awt.Color(255, 255, 255));
        functionField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        functionField.setForeground(new java.awt.Color(0, 0, 0));
        functionField.setToolTipText("Escolha uma função pré-cadastrada");
        functionField.setActionCommand("functionChanged");
        functionField.setPreferredSize(new java.awt.Dimension(114, 29));

        cancelButton.setBackground(new java.awt.Color(153, 153, 0));
        cancelButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 0, 0));
        cancelButton.setText("Cancelar");
        cancelButton.setActionCommand("cancel");

        saveButton.setBackground(new java.awt.Color(0, 51, 102));
        saveButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Salvar");
        saveButton.setActionCommand("save");

        birthDateField.setBackground(new java.awt.Color(255, 255, 255));
        birthDateField.setColumns(8);
        birthDateField.setForeground(new java.awt.Color(0, 0, 0));
        try {
            birthDateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        birthDateField.setToolTipText("Formato: dd/mm/yyyy");
        birthDateField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        cirExpDateField.setBackground(new java.awt.Color(255, 255, 255));
        cirExpDateField.setColumns(8);
        cirExpDateField.setForeground(new java.awt.Color(0, 0, 0));
        try {
            cirExpDateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        cirExpDateField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        boardingDataButton.setBackground(new java.awt.Color(204, 204, 204));
        boardingDataButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        boardingDataButton.setForeground(new java.awt.Color(0, 0, 0));
        boardingDataButton.setText("Dados de Embarque");
        boardingDataButton.setActionCommand("boardingData");

        javax.swing.GroupLayout dialogPanelLayout = new javax.swing.GroupLayout(dialogPanel);
        dialogPanel.setLayout(dialogPanelLayout);
        dialogPanelLayout.setHorizontalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(functionField, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dialogPanelLayout.createSequentialGroup()
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(nationalityField, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(birthDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(dialogPanelLayout.createSequentialGroup()
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sispatField, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cirField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cirExpDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, dialogPanelLayout.createSequentialGroup()
                            .addComponent(boardingDataButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(nameField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        dialogPanelLayout.setVerticalGroup(
            dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(dialogPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(functionField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(dialogPanelLayout.createSequentialGroup()
                        .addComponent(companyField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(sispatField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(dialogPanelLayout.createSequentialGroup()
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(1, 1, 1)
                        .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cirField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cirExpDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(1, 1, 1)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nationalityField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(birthDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(50, 50, 50)
                .addGroup(dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, dialogPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(boardingDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        ComboBoxRenderer renderer = new ComboBoxRenderer();
        functionField.setRenderer(renderer);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dialogPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dialogPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initPresentationLogic() {
        // Adds listener to buttons
        ButtonsHandler handler = new ButtonsHandler();
        saveButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
        boardingDataButton.addActionListener(handler);
        
        // Loads functionField item list with model data
        functions = model.getAllFunctions();        
        functions.forEach(f -> {
            functionField.addItem(f);
        });
        
        nameField.setInputVerifier(new NameVerifier());
        companyField.setInputVerifier(new CompanyVerifier());
        sispatField.setInputVerifier(new SispatVerifier());
        nationalityField.setInputVerifier(new NationalityVerifier());
        cirField.setInputVerifier(new CirVerifier());
        
        DateVerifier dv = new DateVerifier();
        birthDateField.setInputVerifier(dv);
        cirExpDateField.setInputVerifier(dv);
    }       
    
    private void readGUIState() {        
        // Loads GUI State if in Edit Mode
        if (editMode) {
            // Fills fields with received value
            nameField.setText(person.getName());
            
            Function function = functions.stream()
                    .filter(f -> f.getFunctionId() == person
                            .getFunction().getFunctionId())
                    .findFirst()
                    .orElse(null);
            functionField.setSelectedItem(function);
            companyField.setText(person.getCompany());
            sispatField.setText(person.getSispat());
            nationalityField.setText(person.getNationality().toUpperCase());        
            cirField.setText(person.getCir());

            // Date Fields
            birthDateField.setText(person.getBirthDate().format(FORMATTER));
            cirExpDateField.setText(person.getCirExpDate().format(FORMATTER));
        }        
    }
    
    private void writeGUIState() {
        // Writes fields values to person variable
        person.setName(nameField.getText());        
        person.setFunction((Function) functionField.getSelectedItem());
        person.setCompany(companyField.getText());        
        person.setSispat(sispatField.getText());
        person.setNationality(nationalityField.getText());
        person.setCir(cirField.getText());
        
        // Parses date fields from string to LocalDates        
        try {    
            person.setBirthDate(LocalDate.parse(birthDateField.getText(),
                    FORMATTER));
            person.setCirExpDate(LocalDate.parse(cirExpDateField.getText(),
                    FORMATTER));
        } catch (DateTimeParseException e) {
            System.err.println(TAG + "Could not save date to Person" + e);
        }
    }    
    
    // Handlers
    class ButtonsHandler implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "save":
                    writeGUIState();
                    if (editMode) {
                        model.updatePerson(person);                        
                    } else {
                        model.insertPerson(person);
                    }                    
                    dispose();
                    break;
                
                case "cancel":
                    dispose();
                    break;
                
                case "boardingData":          
                    EditBoardedDialog d = new EditBoardedDialog(
                            EditPersonDialog.this,
                            true,
                            model,
                            person);
                    d.getStaticDataButton().setEnabled(false);
                    d.setVisible(true);
                    break;
                                
                default:
                    EditPersonDialog.this.getParent().requestFocus();                
            }  
        }       
    }   
    
    // Input verifiers    
    class NameVerifier extends InputVerifier {        
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Campo deve ter no máximo " 
                                + MAX_NAME_SIZE + " letras!%n" 
                                + "Campo deve ter apenas letras.");
                return false;
            }
        }
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            return isInputAlphabetic(tf.getText()) 
                    && (tf.getText().length() <= MAX_NAME_SIZE);
        }
        
    }        
    
    class CompanyVerifier extends InputVerifier {        
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Campo deve ter no máximo " + 
                                MAX_COMPANY_SIZE + " letras!%n" 
                                + "Campo deve ter apenas letras!");
                return false;
            }
        }        
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;           
            return isInputAlphabetic(tf.getText()) 
                    && tf.getText().length() <= MAX_COMPANY_SIZE;
        }       
    }
    
    class SispatVerifier extends InputVerifier {        
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Campo deve ter apenas " 
                                + MAX_SISPAT_SIZE + " números!%n");
                return false;
            }
        }        
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;           
            return isInputNumeric(tf.getText()) 
                    && tf.getText().length() <= MAX_SISPAT_SIZE;
        }       
    }
    
    class NationalityVerifier extends InputVerifier {        
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Campo deve ter no máximo " 
                                + MAX_NATIONALITY_SIZE + " letras!%n");
                return false;
            }
        }        
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;           
            return isInputAlphabetic(tf.getText()) 
                    && tf.getText().length() <= MAX_NATIONALITY_SIZE;
        }       
    }
    
    class CirVerifier extends InputVerifier {        
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                        "Campo deve ter no máximo " 
                                + MAX_CIR_SIZE + " letras!%n");
                return false;
            }
        }        
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;           
            return isInputAlphanumeric(tf.getText()) 
                    && tf.getText().length() <= MAX_CIR_SIZE;
        }       
    }
    
    private final class DateVerifier extends InputVerifier {
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                source.setToolTipText("Data deve estar no formato dd/MM/yyyy");
                return false;
            }
        }        
        
        @Override
        public boolean verify(JComponent input) {
            JFormattedTextField tf = (JFormattedTextField) input;
            try {
                LocalDate.parse(tf.getText(), FORMATTER);
                return true;
            } catch (DateTimeParseException d) {
                return false;
            }            
        }       
    }
    
    private boolean isInputAlphabetic(String text) {
        char[] word = text.toCharArray();
        for (char c : word) {
            if (!Character.isLetter(c) && c != ' ') {
                return false;
            }
        }
        return true;
    }
    
    private boolean isInputAlphanumeric(String text) {
        char[] word = text.toCharArray();
        for (char c : word) {
            if (!Character.isLetter(c) && !Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }   
    
    private boolean isInputNumeric(String text) {
        char [] word = text.toCharArray();
        for (char c : word) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }  
    
    
    // Implementes custom renderer for functionField combobox
    class ComboBoxRenderer extends JLabel implements ListCellRenderer {
    
        public ComboBoxRenderer() {
            setOpaque(true);
            setHorizontalAlignment(CENTER);
            setVerticalAlignment(CENTER);
        }

        @Override
        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            Function function = (Function) value;
            
            setText(function.getFormalDescription());
            
            return this;            
        }   
    }

    public JButton getBoardingDataButton() {
        return boardingDataButton;
    }    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField birthDateField;
    private javax.swing.JButton boardingDataButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JFormattedTextField cirExpDateField;
    private javax.swing.JTextField cirField;
    private javax.swing.JTextField companyField;
    private javax.swing.JPanel dialogPanel;
    private javax.swing.JComboBox<Function> functionField;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField nameField;
    private javax.swing.JTextField nationalityField;
    private javax.swing.JButton saveButton;
    private javax.swing.JTextField sispatField;
    // End of variables declaration//GEN-END:variables
}
