/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.dialog;

import com.nauta.voyager.people.Person;
import com.nauta.voyager.dialog.EditBoardedDialog;
import com.nauta.voyager.dialog.EditPersonDialog;
import com.nauta.voyager.VoyagerModel;
import com.nauta.voyager.util.StateListener;
import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListCellRenderer;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author rodrigo
 */
public class BoardingDialog extends javax.swing.JDialog implements
        StateListener {
    
    private static final String TAG = BoardingDialog.class.getSimpleName();
    
    private final VoyagerModel model;
    
    private TableRowSorter<PeopleTableModel> sorter;

    /**
     * Creates new form PobManagerDialog
     */
    public BoardingDialog(
            Frame frame,
            boolean modal,
            VoyagerModel model) {
        super(frame, modal);
        
        // Ensures model is not null
        if (model != null) {
            this.model = model;
        } else {
            throw new IllegalArgumentException(TAG + "Null model received");
        }
        
        initComponents();
        initPresentationLogic();
        readGUIStateFromDomain();
        setVisible(true);
    }
    
    // Constructor when the parent can't be found
    public BoardingDialog(VoyagerModel model) {
        super();
        
        // Ensures model is not null
        if (model != null) {
            this.model = model;
        } else {
            throw new IllegalArgumentException(TAG + "Null model received");
        }
        
        initComponents();
        initPresentationLogic();
        readGUIStateFromDomain();
        setVisible(true);
    }
    
    @Override
    public void onListenedStateChanged() {
        readGUIStateFromDomain();
    }
    
    
    private void initPresentationLogic() {
        model.addStateListener(this);
        

        // Sets people table up
        PeopleTableModel model = new PeopleTableModel();
        peopleTable.setModel(model);
        peopleTable.setDefaultRenderer(Person.class, new CrewMemberRenderer());
        peopleTable.getSelectionModel().addListSelectionListener(new TableHandler());
                
        this.sorter = new TableRowSorter<>(model);
        peopleTable.setRowSorter(sorter);
        
        
        // Sets buttons
        ButtonHandler handler = new ButtonHandler();
        addButton.addActionListener(handler);
        boardButton.addActionListener(handler);
        
        // Sets nameField for searching
        nameField.getDocument().addDocumentListener(new NameFieldHandler());
    }
    
    private void readGUIStateFromDomain() {
        PeopleTableModel tModel = (PeopleTableModel) peopleTable.getModel();
        tModel.loadData();
        sorter = new TableRowSorter<>(tModel);
        peopleTable.setRowSorter(sorter);
    }
    
    
    // Sets the behaviour of the nameField, for searching in the table
    private final class NameFieldHandler implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
            newFilter();
            setButtonsStates();
            selectIfUnique();
        }
        
        @Override
        public void insertUpdate(DocumentEvent e) {
            newFilter();
            setButtonsStates();            
            selectIfUnique();
        }
        
        @Override
        public void removeUpdate(DocumentEvent e) {
            newFilter();
            setButtonsStates();            
            selectIfUnique();
        }
        
        // Adds new regex filter to table sorter
        private void newFilter() {
            RowFilter<PeopleTableModel, Object> rf;
            try {
                rf = RowFilter.regexFilter("(?i)" 
                        + nameField.getText(), 0);  // 0 is the name column
            } catch (java.util.regex.PatternSyntaxException e) {
                // TODO - catch filter exception
                return;
            }
            sorter.setRowFilter(rf);
        }

        private void selectIfUnique() {
            if (peopleTable.getRowCount() == 1) {
                peopleTable.setRowSelectionInterval(0, 0);
            }
        }        
        
    }
    
   
    
    private final class ButtonHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "board" -> {
                    // Gets selected row, sets the Person as boarded,
                    // Passes the Person to a EditBoardedDialog for 
                    // fine-tuning
                    int correctedRow = peopleTable.convertRowIndexToModel(
                            peopleTable.getSelectedRow());
                    
                    // 1 is the ID row
                    Person member = model
                            .getCrewMember((Integer) peopleTable
                            .getModel()
                            .getValueAt(correctedRow, 1));
                    
                    member.setBoarded(true);
                    
                    new EditBoardedDialog(BoardingDialog.this, true,
                            model, member);
                }
                
                case "add" -> {
                    // Calls new dialog to build a Person
                    new EditPersonDialog(BoardingDialog.this, true, model);
                }
                
                default -> {
                    // Request focus on nameField
                    nameField.requestFocus();
                    nameField.selectAll();
                }
            }
        }
        
    }
    
    // Defines buttons' behavior according to number of selected table rows
    private void setButtonsStates() {
          
        switch (peopleTable.getRowCount()) {
            case 0 -> {
                addButton.setEnabled(true);
                boardButton.setEnabled(false);
                break;
            }
            
            case 1 -> {
                addButton.setEnabled(false);
                boardButton.setEnabled(true);
                break;
            }
            
            default -> {
                addButton.setEnabled(false);
                if (peopleTable.getSelectedRowCount() > 0) {
                    boardButton.setEnabled(true);                    
                }
            }
        }
    }  
    
    
    private final class TableHandler implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            setButtonsStates();            
        }        
    }   
    
    
    // Custom table model for showing as a list
    private final class PeopleTableModel extends AbstractTableModel {
        private final String[] columnNames = {"NOME"};
        
        private Object[][] data;       
        
        public PeopleTableModel() {
            loadData();
        }
        
        private void loadData() {
            
            List<Person> list = model.getAllNonBoardedCrewMembers();
            
            int size = list.size();
            data = new Object[size][getColumnCount() + 1];
            for (int i = 0; i < size; i++) {
                data[i][0] = list.get(i).getName();
                data [i][1] = list.get(i).getId();
            }
            
            fireTableDataChanged();
        }
        
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }

        //@Override
        //public Class<?> getColumnClass(int columnIndex) {
        //    return Person.class;
        //}
        
        
    }
    
    /* 
     * Defines a renderer for the list, keeping reference to Person while
     * showing only the name.
     */
    public final class CrewMemberRenderer extends JLabel 
            implements TableCellRenderer {
        
        public CrewMemberRenderer() {
            setOpaque(true);
            setVerticalAlignment(CENTER);
        }
        
        @Override
        public Component getTableCellRendererComponent(JTable table,
                Object value, boolean isSelected, boolean hasFocus,
                int row, int column) {
            Person member = (Person) value;
            
            String name = member.getName();
            setText(name);
            
            if (isSelected) {
                requestFocus();
            }
            
            return this;            
        }
        
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        boardButton = new javax.swing.JButton();
        addButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        peopleTable = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setText("Pessoa a embarcar");
        jLabel1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel1.setFocusable(false);
        jLabel1.setInheritsPopupMenu(false);
        jLabel1.setRequestFocusEnabled(false);
        jLabel1.setVerifyInputWhenFocusTarget(false);
        jLabel1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        nameField.setBackground(new java.awt.Color(255, 255, 255));
        nameField.setColumns(60);
        nameField.setFont(new java.awt.Font("Verdana", 0, 16)); // NOI18N
        nameField.setForeground(new java.awt.Color(0, 0, 0));
        nameField.setToolTipText("Digite aqui para filtrar");

        jLabel2.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 0, 0));
        jLabel2.setText("Pessoas cadastradas");
        jLabel2.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel2.setFocusable(false);
        jLabel2.setInheritsPopupMenu(false);
        jLabel2.setRequestFocusEnabled(false);
        jLabel2.setVerifyInputWhenFocusTarget(false);
        jLabel2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        boardButton.setBackground(new java.awt.Color(51, 204, 0));
        boardButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        boardButton.setForeground(new java.awt.Color(0, 0, 0));
        boardButton.setText("Embarcar");
        boardButton.setActionCommand("board");

        addButton.setBackground(new java.awt.Color(0, 51, 102));
        addButton.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        addButton.setForeground(new java.awt.Color(255, 255, 255));
        addButton.setText("Cadastrar Novo");
        addButton.setActionCommand("add");
        addButton.setEnabled(false);

        peopleTable.setBackground(new java.awt.Color(255, 255, 255));
        peopleTable.setFont(new java.awt.Font("Verdana", 0, 12)); // NOI18N
        peopleTable.setModel(new javax.swing.table.DefaultTableModel(
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
        peopleTable.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(peopleTable);
        peopleTable.setTableHeader(null);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addContainerGap(20, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addGap(18, 18, 18)
                                .addComponent(boardButton)))
                        .addGap(20, 20, 20))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(1, 1, 1)
                .addComponent(nameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(boardButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 273, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    javax.swing.JButton addButton;
    javax.swing.JButton boardButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField nameField;
    private javax.swing.JTable peopleTable;
    // End of variables declaration//GEN-END:variables
}
