package com.nauta.voyager.dialog;

import com.nauta.voyager.entity.Pob;
import com.nauta.voyager.service.PobService;
import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.util.VoyagerContext;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class ChangeCrewDialog extends javax.swing.JDialog {
    
    // Format of the dates used on the dialog
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");
    
    // Max size for a place
    private static final int MAX_PLACE_SIZE = 30;
    
    // Usual number of days to spend onboard
    private static final int STD_EMBARK_DAYS = 28;
    
    private Pob pob;
    
    // Services
    private PobService pobService;
    
    
    /**
     * Creates new form ChangeCrewDialog
     */
    public ChangeCrewDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        pobService = ServiceFactory.getPobService();        
        initComponents();
        initPresentationLogic();
        readGUIState();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        boardingDateField = new javax.swing.JFormattedTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        boardingPlaceField = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        arrivalDateField = new javax.swing.JFormattedTextField();
        arrivalPlaceField = new javax.swing.JFormattedTextField();
        jLabel7 = new javax.swing.JLabel();
        crewField = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        crewCBField = new javax.swing.JCheckBox();
        extrasCBField = new javax.swing.JCheckBox();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trocar Turma");

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setForeground(new java.awt.Color(255, 255, 255));

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

        jLabel6.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 0));
        jLabel6.setText("Local de Embarque");

        boardingPlaceField.setBackground(new java.awt.Color(255, 255, 255));
        boardingPlaceField.setColumns(30);
        boardingPlaceField.setForeground(new java.awt.Color(0, 0, 0));
        boardingPlaceField.setText("123456789012345678901234567890");
        boardingPlaceField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        boardingPlaceField.setMaximumSize(new java.awt.Dimension(318, 2147483647));
        boardingPlaceField.setPreferredSize(new java.awt.Dimension(318, 27));

        jLabel5.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 0));
        jLabel5.setText("Data de Desembarque");

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

        arrivalPlaceField.setBackground(new java.awt.Color(255, 255, 255));
        arrivalPlaceField.setColumns(30);
        arrivalPlaceField.setForeground(new java.awt.Color(0, 0, 0));
        arrivalPlaceField.setText("São João da Barra - RJ");
        arrivalPlaceField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 0));
        jLabel7.setText("Local de Desembarque");

        crewField.setBackground(new java.awt.Color(255, 255, 255));
        crewField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        crewField.setForeground(new java.awt.Color(0, 0, 0));

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Turma");

        jLabel3.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Afetados:");

        crewCBField.setBackground(new java.awt.Color(255, 255, 255));
        crewCBField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        crewCBField.setForeground(new java.awt.Color(0, 0, 0));
        crewCBField.setText("Tripulação");

        extrasCBField.setBackground(new java.awt.Color(255, 255, 255));
        extrasCBField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        extrasCBField.setForeground(new java.awt.Color(0, 0, 0));
        extrasCBField.setText("Extras");

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(boardingDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(arrivalDateField))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel6))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(arrivalPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(boardingPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(31, 31, 31)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(crewCBField)
                                .addGap(18, 18, 18)
                                .addComponent(extrasCBField)))))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(crewField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(crewCBField)
                    .addComponent(extrasCBField))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel6))
                .addGap(1, 1, 1)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boardingDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(boardingPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(1, 1, 1)
                        .addComponent(arrivalDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(1, 1, 1)
                        .addComponent(arrivalPlaceField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancelButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void initPresentationLogic() {
        // Adds handling to buttons
        ButtonsHandler handler = new ButtonsHandler();
        saveButton.addActionListener(handler);
        cancelButton.addActionListener(handler);
        
        // Initializes crewField list with fixed crew data from model
        pobService.getAllCrews().forEach(s -> {
            crewField.addItem(s);
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
        pob = pobService.getLastPob();
        
        boardingDateField.setText(LocalDate.now().format(FORMATTER));
        
        // Sets arrival date for 28 days later
        arrivalDateField.setText(
                ChronoUnit.DAYS.addTo(LocalDate.now(), STD_EMBARK_DAYS)
                        .format(FORMATTER));
    }
    
    private void writeGUIState() {        
        pob.setCrew(crewField.getSelectedItem().toString());
        
        pob.setBoardingDate(LocalDate.parse(boardingDateField.getText(),
                FORMATTER));
        pob.setBoardingPlace(boardingPlaceField.getText());
        pob.setArrivalDate(LocalDate.parse(arrivalDateField.getText(),
                FORMATTER));
        pob.setArrivalPlace(arrivalPlaceField.getText());
        
        if (crewCBField.isSelected()) {
            pobService.savePob(pob);
        }
    }
        
    
    // Event handlers
    private class ButtonsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()) {
                case "save":
                    writeGUIState();
                    
                    if (crewCBField.isSelected()) {
                        pobService.changeCrew(pob.getCrew(),
                                true,
                                true,
                                pob);
                    }
                    
                    if (extrasCBField.isSelected()) {
                        pobService.changeCrew(pob.getCrew(),
                                false,
                                true,
                                pob);
                    }
                    
                    // Notifies listeners
                    VoyagerContext.getContext().fireStateChanged();
                    dispose();
                    break;
                
                case "cancel":
                    dispose();
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
    
    
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFormattedTextField arrivalDateField;
    private javax.swing.JFormattedTextField arrivalPlaceField;
    private javax.swing.JFormattedTextField boardingDateField;
    private javax.swing.JFormattedTextField boardingPlaceField;
    private javax.swing.JButton cancelButton;
    private javax.swing.JCheckBox crewCBField;
    private javax.swing.JComboBox<String> crewField;
    private javax.swing.JCheckBox extrasCBField;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
