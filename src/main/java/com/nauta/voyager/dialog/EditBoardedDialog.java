/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.dialog;

import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.VoyagerModel;
import com.nauta.voyager.entity.BoardingData;
import com.nauta.voyager.service.FunctionService;
import com.nauta.voyager.service.PersonService;
import com.nauta.voyager.entity.Cabin;
import com.nauta.voyager.service.PobService;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.swing.InputVerifier;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author rodrigo
 */
public class EditBoardedDialog extends javax.swing.JDialog {
    
    private static final String TAG = EditBoardedDialog.class.getSimpleName();
        
    
    private Person person;
    
    private PobService pobService;
    private PersonService personService;
    private VoyagerContext context;
    
    // Format of the dates used on the dialog
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");
    
    private static final int MAX_PLACE_SIZE = 30;
    

    /**
     * Creates new form EditBoardedDialog. It is assumed there is an already
        existent CrewMember.
     * 
     * @param frame    the parent Frame view of this dialog
     * @param modal     if true, the dialog is instantiated as modal
     * @param person    the Person instance which is having its boarding
                  data edited
     */
    public EditBoardedDialog(Frame frame, boolean modal, Person person) {
        super(frame, modal);
                
        if (person == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Person is null. Exiting dialog" );        
        }       
        this.person = person;            
        this.pobService = ServiceFactory.getPobService();
        this.personService = ServiceFactory.getPersonService();
        initComponents();
        initPresentationLogic();
        readGUIState();
    }
    
    /**
     * Creates new form EditBoardedDialog. It is assumed there is an already
     * existent Person.
     * 
     * @param dialog    the parent Dialog view of this dialog
     * @param modal     if true, the dialog is instantiated as modal
     * @param model     the model used by the dialog to retrieve and persist
     *                  its data
     * @param person    the Person instance which is having its boarding
                  data edited
     */
    public EditBoardedDialog(Dialog dialog, boolean modal, Person person) {
        super(dialog, modal);
                
        if (person == null) {            
            throw new IllegalArgumentException(TAG 
                    + " - Person is null. Exiting dialog" );
        }           
        this.person = person;            
        this.pobService = ServiceFactory.getPobService();
        this.personService = ServiceFactory.getPersonService();        
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

        boardedPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        crewField = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        isBoardedField = new javax.swing.JCheckBox();
        boardingDateField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        boardingPlaceField = new javax.swing.JFormattedTextField();
        arrivalDateField = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        arrivalPlaceField = new javax.swing.JFormattedTextField();
        jLabel8 = new javax.swing.JLabel();
        cabinField = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        shiftField = new javax.swing.JComboBox<>();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        staticDataButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Dados de Embarque");
        setBackground(new java.awt.Color(255, 255, 255));

        boardedPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Nome");

        nameField.setBackground(new java.awt.Color(255, 255, 255));
        nameField.setColumns(60);
        nameField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        nameField.setForeground(new java.awt.Color(0, 0, 0));
        nameField.setText("123456789012345678901234567890123456789012345678901234567890");
        nameField.setEnabled(false);
        nameField.setPreferredSize(new java.awt.Dimension(855, 27));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Turma");

        crewField.setBackground(new java.awt.Color(255, 255, 255));
        crewField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        crewField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Está embarcado?");

        isBoardedField.setBackground(new java.awt.Color(255, 255, 255));
        isBoardedField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        isBoardedField.setForeground(new java.awt.Color(0, 0, 0));
        isBoardedField.setText("Sim");

        boardingDateField.setBackground(new java.awt.Color(255, 255, 255));
        boardingDateField.setColumns(8);
        boardingDateField.setForeground(new java.awt.Color(0, 0, 0));
        try {
            boardingDateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        boardingDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        boardingDateField.setToolTipText("Formato: dd/mm/yyyy");
        boardingDateField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Data de Embarque");

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Data de Desembarque");

        boardingPlaceField.setBackground(new java.awt.Color(255, 255, 255));
        boardingPlaceField.setColumns(30);
        boardingPlaceField.setForeground(new java.awt.Color(0, 0, 0));
        boardingPlaceField.setText("123456789012345678901234567890");
        boardingPlaceField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        boardingPlaceField.setMaximumSize(new java.awt.Dimension(318, 2147483647));
        boardingPlaceField.setPreferredSize(new java.awt.Dimension(318, 27));

        arrivalDateField.setBackground(new java.awt.Color(255, 255, 255));
        arrivalDateField.setColumns(8);
        arrivalDateField.setForeground(new java.awt.Color(0, 0, 0));
        try {
            arrivalDateField.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        arrivalDateField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        arrivalDateField.setToolTipText("Formato: dd/mm/yyyy");
        arrivalDateField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Local de Embarque");

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Local de Desembarque");

        arrivalPlaceField.setBackground(new java.awt.Color(255, 255, 255));
        arrivalPlaceField.setColumns(30);
        arrivalPlaceField.setForeground(new java.awt.Color(0, 0, 0));
        arrivalPlaceField.setText("São João da Barra - RJ");
        arrivalPlaceField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 0));
        jLabel8.setText("Camarote");

        cabinField.setBackground(new java.awt.Color(255, 255, 255));
        cabinField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        cabinField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 0));
        jLabel9.setText("Turno");

        shiftField.setBackground(new java.awt.Color(255, 255, 255));
        shiftField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        shiftField.setForeground(new java.awt.Color(0, 0, 0));

        saveButton.setBackground(new java.awt.Color(0, 51, 102));
        saveButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        saveButton.setForeground(new java.awt.Color(255, 255, 255));
        saveButton.setText("Salvar");
        saveButton.setActionCommand("save");

        cancelButton.setBackground(new java.awt.Color(153, 153, 0));
        cancelButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        cancelButton.setForeground(new java.awt.Color(0, 0, 0));
        cancelButton.setText("Cancelar");
        cancelButton.setActionCommand("cancel");

        staticDataButton.setBackground(new java.awt.Color(204, 204, 204));
        staticDataButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        staticDataButton.setForeground(new java.awt.Color(0, 0, 0));
        staticDataButton.setText("Dados Estáticos");
        staticDataButton.setActionCommand("staticData");

        javax.swing.GroupLayout boardedPanelLayout = new javax.swing.GroupLayout(boardedPanel);
        boardedPanel.setLayout(boardedPanelLayout);
        boardedPanelLayout.setHorizontalGroup(
            boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardedPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addGroup(boardedPanelLayout.createSequentialGroup()
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(boardingDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(staticDataButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(arrivalDateField, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardedPanelLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(boardedPanelLayout.createSequentialGroup()
                                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel7)
                                            .addComponent(jLabel6))
                                        .addGap(0, 174, Short.MAX_VALUE))
                                    .addComponent(arrivalPlaceField, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                                    .addComponent(boardingPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardedPanelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardedPanelLayout.createSequentialGroup()
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(isBoardedField)
                            .addComponent(jLabel3))
                        .addGap(50, 50, 50)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(cabinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(50, 50, 50)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(shiftField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 620, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        boardedPanelLayout.setVerticalGroup(
            boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(boardedPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(boardedPanelLayout.createSequentialGroup()
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardedPanelLayout.createSequentialGroup()
                                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel3))
                                .addGap(1, 1, 1)
                                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(isBoardedField)))
                            .addGroup(boardedPanelLayout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(1, 1, 1)
                                .addComponent(shiftField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(1, 1, 1)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(boardingDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(boardingPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(boardedPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(1, 1, 1)
                                .addComponent(arrivalDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(boardedPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(1, 1, 1)
                                .addComponent(arrivalPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(boardedPanelLayout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(cabinField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(50, 50, 50)
                .addGroup(boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, boardedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(staticDataButton, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boardedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(boardedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initPresentationLogic() {
        // Adds handling to buttons
        ButtonsHandler handler = new ButtonsHandler();
        saveButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
        staticDataButton.addActionListener(handler);
        
        // Initializes crewField list with fixed crew data from model
        pobService.getAllCrews().forEach(s -> {
            crewField.addItem(s);
        });
        
        // Initializes cabinField list with mutable cabin data from model
        pobService.getAllCabins().forEach(s -> {
            cabinField.addItem(s.toString());
        });
        
        // Initializes shiftField with fixed shift values from model
        pobService.getAllShifts().forEach(s -> {
            shiftField.addItem(s);
        });
        
        /* 
         * Sets place fields with input verifiers and PlainDocuments for size
         * restriction
         */
        boardingPlaceField.setDocument(
                new LengthRestrictedDocument(MAX_PLACE_SIZE));
        boardingPlaceField.setInputVerifier(new PlaceVerifier());
        
        arrivalPlaceField.setDocument(
                new LengthRestrictedDocument(MAX_PLACE_SIZE));
        arrivalPlaceField.setInputVerifier(new PlaceVerifier());
        
        DateVerifier dv = new DateVerifier();
        boardingDateField.setInputVerifier(dv);
        arrivalDateField.setInputVerifier(dv);        
    }
    
    private void readGUIState() {
        BoardingData bd = person.getBoardingData();
        nameField.setText(person.getName());
        crewField.setSelectedItem(bd.getCrew());
        isBoardedField.setSelected(bd.isBoarded());
        cabinField.setSelectedItem(bd.getCabin().toString());
        shiftField.setSelectedItem(bd.getShift());        
        boardingPlaceField.setText(bd.getBoardingPlace());
        arrivalPlaceField.setText(bd.getArrivalPlace());        
        
        if (!bd.getBoardingDate().isEqual(LocalDate.MIN)) {
            boardingDateField.setText(bd.getBoardingDate().format(FORMATTER));            
        } 
        if (!bd.getArrivalDate().isEqual(LocalDate.MIN)) {
            arrivalDateField.setText(bd.getArrivalDate().format(FORMATTER));            
        }
    }
    
    private void writeGUIState() {
        BoardingData bd = person.getBoardingData();
        bd.setCrew(crewField.getSelectedItem().toString());
        bd.setBoarded(isBoardedField.isSelected());
        bd.setCabin(new Cabin(cabinField.getSelectedItem().toString()));
        bd.setShift(shiftField.getSelectedItem().toString());
        bd.setBoardingDate(LocalDate.parse(boardingDateField.getText(),
                FORMATTER));
        bd.setBoardingPlace(boardingPlaceField.getText());
        bd.setArrivalDate(LocalDate.parse(arrivalDateField.getText(),
                FORMATTER));
        bd.setArrivalPlace(arrivalPlaceField.getText());
    }
    
    
    // Event handlers
    private class ButtonsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "save":
                    writeGUIState();
                    personService.updatePerson(person);
                    VoyagerContext.getContext().fireStateChanged();
                    dispose();
                    break;
                
                case "cancel":
                    dispose();
                    break;
                
                case "staticData":
                    EditPersonDialog d = new EditPersonDialog(
                            EditBoardedDialog.this,
                            true).withPerson(person.getPersonId());                          
                    d.getBoardingDataButton().setEnabled(false);
                    d.setVisible(true);
                    break;                
                
                default:                
            }
        }        
    }
    
    
    // Input Verifiers
    private class PlaceVerifier extends InputVerifier {
        @Override
        public boolean shouldYieldFocus(JComponent source, JComponent target) {
            boolean inputOK = verify(source);
            if (inputOK) {
                return true;
            } else {
                JOptionPane.showMessageDialog(rootPane,
                       "Local deve ser no formato Cidade - Estado!\n"
                        + "Exemplo: Rio de Janeiro - RJ");
                return false;
            }
        }
        
        @Override
        public boolean verify(JComponent input) {
            JTextField tf = (JTextField) input;
            
            boolean answer = tf.getText().chars().allMatch(c -> 
                    Character.isLetter(c) || (c == '-') || (c == ' '));           
            
            return answer;            
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
    
    /*
     * Extracted from 
     * https://stackoverflow.com/questions/13075564/limiting-length-of-input-in-jtextfield-is-not-working
     * 
     * Sets maximum limit of field
     */
    private final class LengthRestrictedDocument extends PlainDocument {

        private final int limit;

        public LengthRestrictedDocument(int limit) {
          this.limit = limit;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {
          if (str == null)
            return;
          if ((getLength() + str.length()) <= limit) {
            super.insertString(offs, str, a);
          }
        }
    }

    public JButton getStaticDataButton() {
        return staticDataButton;
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField arrivalDateField;
    private javax.swing.JFormattedTextField arrivalPlaceField;
    private javax.swing.JPanel boardedPanel;
    private javax.swing.JFormattedTextField boardingDateField;
    private javax.swing.JFormattedTextField boardingPlaceField;
    private javax.swing.JComboBox<String> cabinField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox<String> crewField;
    private javax.swing.JCheckBox isBoardedField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField nameField;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox<String> shiftField;
    private javax.swing.JButton staticDataButton;
    // End of variables declaration//GEN-END:variables
}
