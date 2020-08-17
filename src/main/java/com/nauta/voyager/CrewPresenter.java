/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.awt.CardLayout;
import java.awt.event.*;
import java.awt.Point;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.io.*;

/*
TODO:
- Implement loadProperties correctly
* - Change Edit Person to allow return values (?)
- Update Table Model with all the appropriate columns
- Add custom filter checkboxes below searchTextField
- Regex Filter - Remove hardcoded ints
- Perfect the visuals
*/

/**
 *
 * @author rodrigo
 */
public class CrewPresenter implements StateListener {
    final private MonitorFrame view;
    final private CrewMemberModel model;
    final Properties properties;
    
    // JTable variables
    private TableRowSorter<CrewTableModel> sorter;
    
    
    CrewPresenter(MonitorFrame view, CrewMemberModel model) {
        this.view = view;
        this.model = model;
        this.properties = loadProperties();
        initPresentationLogic();
    }   
    
    /**
     * This method initiates component models, view event handlers
     */    
    private void initPresentationLogic() {
        // UI Models / Handle Events / Swing Actions
        model.addStateListener(this);
        
        // Sets menu buttons for event handling
        MainMenuHandler menuHandler = new MainMenuHandler();
        view.statusTab.addActionListener(menuHandler);        
        view.pobTab.addActionListener(menuHandler);        
        view.databaseTab.addActionListener(menuHandler);        
        view.navTab.addActionListener(menuHandler);
        
        // Sets crewDBTable Table Model and appearance
        CrewTableModel tModel = new CrewTableModel();
        view.crewDBTable.setModel(tModel);
        sorter = new TableRowSorter<>(tModel);
        view.crewDBTable.setRowSorter(sorter);
        view.setCrewDBTableFormat();        
        
        // Sets searchCrewDBTable for event handling
        CrewDBTableHandler tableHandler = new CrewDBTableHandler();
        view.crewDBTable.addMouseListener(tableHandler);
        view.crewDBTable.getSelectionModel().addListSelectionListener(tableHandler);
        view.searchTextField.getDocument().addDocumentListener(
                new SearchCrewTableHandler());
        CrewViewButtonsHandler buttonsHandler = new CrewViewButtonsHandler();
        view.editButton.addActionListener(buttonsHandler);        
        setEditButtonState();
        view.deleteButton.addActionListener(buttonsHandler);
        setDeleteButtonState();
    }
    
    
    private void readGUIStateFromDomain() {
        // Reloads CrewDBTable model
        CrewTableModel tModel = (CrewTableModel) view.crewDBTable.getModel();
        tModel.loadData();
        sorter = new TableRowSorter<>(tModel);
        view.crewDBTable.setRowSorter(sorter);
        //        
    }
    
    private void writeGUIStateToDomain() {        
    }
    
    // Reacts to model changes
    @Override
    public void onListenedStateChanged() {
        readGUIStateFromDomain();
    }
    
    // Event Handlers
    class MainMenuHandler implements ActionListener {
        // Listens for main menu buttons, handles their initial GUI State
        @Override
        public void actionPerformed(ActionEvent e) {
            CardLayout cl = (CardLayout) view.mainPane.getLayout();        
        
            switch (e.getActionCommand()) {
                case "status" -> cl.show(view.mainPane, "statusCard");
                case "nav" -> {                }
                case "pob" -> cl.show(view.mainPane, "pobCard");
                case "database" -> cl.show(view.mainPane, "databaseCard");                        
            }
        }
    }
    
    class SearchCrewTableHandler implements DocumentListener {
        @Override
        public void changedUpdate(DocumentEvent e) {
            newFilter();
            setEditButtonState();
        }
        @Override
        public void insertUpdate(DocumentEvent e) {
            newFilter();
            setEditButtonState();            
        }
        @Override
        public void removeUpdate(DocumentEvent e) {
            newFilter();
            setEditButtonState();
        }
        
        // Adds new regex filter to table sorter
        private void newFilter() {
            RowFilter<CrewTableModel, Object> rf;
            try {
                rf = RowFilter.regexFilter("(?i)" + view.searchTextField.getText(),
                        0, 1, 2, 3, 4, 5);
            } catch (java.util.regex.PatternSyntaxException e) {
                // TODO - catch filter exception
                return;
            }
            sorter.setRowFilter(rf);
        }       
    }
    
    class CrewDBTableHandler implements MouseListener, ListSelectionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Opens EditDialog when item clicked twice on the table
            JTable table =(JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);
            if (e.getClickCount() == 2) {
                //Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                Integer id = (Integer) view.crewDBTable.getModel()
                    .getValueAt(view.crewDBTable.convertRowIndexToModel(row), 0);
                new EditPersonDialog(model.getCrewMember(id), model, true, properties);                
            }           
        }

        @Override
        public void mousePressed(MouseEvent e) {
            return;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            return;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            return;
        }

        @Override
        public void mouseExited(MouseEvent e) {
            return;
        }
        
        @Override
        public void valueChanged(ListSelectionEvent s) {
            // When selection changes, sets state of delete button
            System.out.println("Selection changed");
            setDeleteButtonState();
        }
    }
    
    class CrewViewButtonsHandler implements ActionListener {
        // Listens for changes in POB View components, implements action
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "edit" -> {
                    // Instantiates new EditDialog with data from table               
                    JTable table = view.crewDBTable;
                    TableModel tableModel = table.getModel();
                    //Integer id = (Integer) table.getValueAt(table.getSelectedRow(), 0);
                    Integer id = (Integer) tableModel.getValueAt(table.convertRowIndexToModel(0), 0);
                    new EditPersonDialog(model.getCrewMember(id), model, true, properties);
                }
                case "add" -> // Instantiates new EditDialog with an empty CrewMember
                    new EditPersonDialog(new CrewMember(), model, false, properties);
                case "delete" -> {
                    int[] rows = view.crewDBTable.getSelectedRows();
                    int delete = JOptionPane.showConfirmDialog(
                            view,
                            "Tem certeza que deseja excluir o(s)\n" +
                                    rows.length + 
                                    " registro(s) selecionado(s)?",
                            "Excluir Registros",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null
                    );
                    if (delete == JOptionPane.OK_OPTION) {
                        int counter = 0;
                        for (int rowIndex : rows) {
                            Integer personId = (Integer) view.crewDBTable
                                    .getModel()
                                    .getValueAt(view.crewDBTable.convertRowIndexToModel(rowIndex), 0);
                            if (model.deleteCrewMember(personId) < 0) {
                                JOptionPane.showMessageDialog(view,
                                        "Ops...Não foi possível deletar!",
                                        "Erro de Exclusão",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            } else {
                                ++counter;
                            }
                        }                       
                        JOptionPane.showMessageDialog(view, "Deletado(s) "
                                + counter + " registro(s)!");
                    }
                }
            }       
            // Requests focus back on to searchTextField
            view.searchTextField.requestFocusInWindow();
            view.searchTextField.select(0, view.searchTextField.getText().length());
        }
    }
    
    
    
    // General purpose methods
    private void setEditButtonState() {
        // Sets Edit button status
        switch (view.crewDBTable.getRowCount()) {
            case 0 -> {
                view.editButton.setEnabled(true);
                view.editButton.setText("Adicionar");
                view.editButton.setActionCommand("add");
            }
            case 1 -> {
                view.editButton.setEnabled(true);
                view.editButton.setText("Editar");
                view.editButton.setActionCommand("edit");
                view.crewDBTable.setRowSelectionInterval(0, 0);
            }
            default -> {
                view.editButton.setEnabled(false);
                view.editButton.setText("Editar");
                view.editButton.setActionCommand("edit");
            }
        }
    }
    
    private void setDeleteButtonState() {
        // Sets Delete button status. If something is selected, Delete is enabled.
        if (view.crewDBTable.getSelectedRow() < 0) {
            view.deleteButton.setEnabled(false);
        } else {
            view.deleteButton.setEnabled(true);
        }       
    }

    
    
    private Properties loadProperties() {
        // Loads properties from specified folder. If not, loads default.
        Properties result = new Properties();
        InputStream in = null;
        // Search FOLDER for properties
        try {    
            try {    
                in = this.getClass().getClassLoader().getResourceAsStream("default.properties");
                result.load(in);
                System.out.println("Properties loaded successfully");
            } catch (NullPointerException e) {
                System.err.println("Could not load properties stream" + e);
            } finally {
                if (in != null) {
                    in.close();
                }
            }                    
        } catch (IOException e) {
            System.err.println("IO Error found when loading properties" + e);
        }
        return result;
    }
    
    
    /**
     * This inner class implements the crew table model that will be used by
     * the POB Table. It will update the model whenever the Presenter notifies
     * it.
     */
    final class CrewTableModel extends AbstractTableModel {
        private final String[] columnNames = {
        "ID",
        "NOME",
        "FUNÇÃO",
        "EMPRESA",
        "SISPAT",
        "NACIONALIDADE",
        "DATA DE NASCIMENTO",
        "CIR/ID",
        "VALIDADE CIR/ID"
        };    
        private Object[][] data;
        
        public CrewTableModel() {
            loadData();
        }
    
        // Implements logic to retrieve the objects that will compose the rows of the table
        public void loadData() {
            List<CrewMember> list = model.getList();
            int listSize = list.size();
            this.data = new Object[listSize][getColumnCount()];
            for (int i = 0; i < listSize; i++) {
                this.data[i][0] = list.get(i).getId();
                this.data[i][1] = list.get(i).getName();
                this.data[i][2] = list.get(i).getFunction();
                this.data[i][3] = list.get(i).getCompany();
                this.data[i][4] = list.get(i).getSispat();
                this.data[i][5] = list.get(i).getNationality();
                this.data[i][6] = list.get(i).getBirthDate();
                this.data[i][7] = list.get(i).getCir();
                this.data[i][8] = list.get(i).getCirExpDate();
            }
            fireTableDataChanged();
        }
    
        // Implemented methods from AbstractTable
        @Override
        public int getRowCount() {
            return data.length;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int column) {
            return data[row][column];
        }

        // Only if cell is editable
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            data[rowIndex][columnIndex] = aValue;
            fireTableCellUpdated(rowIndex, columnIndex);
        }

        @Override
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
    }     
    
    
}
