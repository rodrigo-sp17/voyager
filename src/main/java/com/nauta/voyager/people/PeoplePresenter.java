/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.people;

import com.nauta.voyager.VoyagerModel;
import com.nauta.voyager.people.Person;
import com.nauta.voyager.dialog.EditPersonDialog;
import com.nauta.voyager.util.StateListener;
import java.awt.event.*;
import java.awt.Point;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.table.TableModel;
import java.io.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumnModel;

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
public class PeoplePresenter implements StateListener {
    final private PeopleView view;
    final private VoyagerModel model;    
    
    // JTable variables
    private TableRowSorter<CrewTableModel> sorter;
    
    
    public PeoplePresenter(PeopleView view, VoyagerModel model) {
        this.view = view;
        this.model = model;        
        initPresentationLogic();
    }   
       
    private void initPresentationLogic() {
        // UI Models / Handle Events / Swing Actions
        model.addStateListener(this);
        
                
        // Sets crewDBTable Table Model and appearance
        CrewTableModel tModel = new CrewTableModel();
        view.crewDBTable.setModel(tModel);
        this.sorter = new TableRowSorter<>(tModel);
        view.crewDBTable.setRowSorter(sorter);
        setTableRenderingPreferences();        
        
        // Sets searchCrewDBTable for event handling
        CrewDBTableHandler tableHandler = new CrewDBTableHandler();
        view.crewDBTable.addMouseListener(tableHandler);
        view.crewDBTable.getSelectionModel()
                .addListSelectionListener(tableHandler);
        view.searchTextField.getDocument().addDocumentListener(
                new SearchCrewTableHandler());
        CrewViewButtonsHandler buttonsHandler = new CrewViewButtonsHandler();
        view.editButton.addActionListener(buttonsHandler);        
        setEditButtonState();
        view.deleteButton.addActionListener(buttonsHandler);
        setDeleteButtonState();
    }
        
    private void setTableRenderingPreferences() {
        // Sets Center Alighment render for columns
        DefaultTableCellRenderer centerRenderer = 
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        centerRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);

        // Sets Vertical Center alighment render for columns
        DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
        midRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);
        
        TableColumnModel m = view.crewDBTable.getColumnModel();
        
        // Defines columns visual properties
        m.getColumn(0).setCellRenderer(centerRenderer);
        m.getColumn(0).setPreferredWidth(40);
        m.getColumn(0).setMinWidth(4);
        m.getColumn(0).setMaxWidth(100);
        m.getColumn(1).setCellRenderer(midRenderer);
        m.getColumn(1).setPreferredWidth(250);        
        m.getColumn(2).setPreferredWidth(200);        
        
        m.getColumn(4).setCellRenderer(centerRenderer);
        m.getColumn(5).setCellRenderer(centerRenderer);
        m.getColumn(6).setCellRenderer(centerRenderer);        
        m.getColumn(7).setCellRenderer(centerRenderer);
        m.getColumn(8).setCellRenderer(centerRenderer);        
    }   
    
    private void readGUIStateFromDomain() {
        // Reloads CrewDBTable model
        CrewTableModel tModel = (CrewTableModel) view.crewDBTable.getModel();
        tModel.loadData();
        sorter = new TableRowSorter<>(tModel);
        view.crewDBTable.setRowSorter(sorter);                
    }
    
    private void writeGUIStateToDomain() {        
    }
    
    // Reacts to model changes
    @Override
    public void onListenedStateChanged() {
        readGUIStateFromDomain();
    }
    
    // Event Handlers  
    private class SearchCrewTableHandler implements DocumentListener {
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
                rf = RowFilter.regexFilter("(?i)" 
                        + view.searchTextField.getText(),
                        0, 1, 2, 3, 4, 5);
            } catch (java.util.regex.PatternSyntaxException e) {
                // TODO - catch filter exception
                return;
            }
            sorter.setRowFilter(rf);
        }       
    }
    
    private class CrewDBTableHandler implements MouseListener,
            ListSelectionListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Opens EditPersonDialog when item clicked twice on the table
            JTable table =(JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);
            if (e.getClickCount() == 2) {
                Integer id = (Integer) view.crewDBTable.getModel()
                    .getValueAt(view.crewDBTable.convertRowIndexToModel(row),
                            0);
                JFrame mainView = (JFrame) SwingUtilities
                        .getWindowAncestor(view);
                JDialog d = new EditPersonDialog(mainView, true,
                        model, model.getPerson(id));
                d.setVisible(true);
            }            
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
        @Override
        public void valueChanged(ListSelectionEvent s) {
            // When selection changes, sets state of delete button            
            setDeleteButtonState();
        }
    }
    
    private class CrewViewButtonsHandler implements ActionListener {
        // Listens for changes in POB View components, implements action
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "edit" -> {
                    // Instantiates new EditDialog with data from table               
                    JTable table = view.crewDBTable;
                    TableModel tableModel = table.getModel();
                    
                    Integer id = (Integer) tableModel
                            .getValueAt(table.convertRowIndexToModel(0), 0);
                    JFrame mainView = (JFrame) SwingUtilities
                        .getWindowAncestor(view);
                    JDialog d = new EditPersonDialog(mainView, true, model, 
                            model.getPerson(id));
                    d.setVisible(true);                    
                }
                
                case "add" -> {
                    // Instantiates new EditPersonDialog for creation
                    JFrame mainView = (JFrame) SwingUtilities
                        .getWindowAncestor(view);
                    JDialog d = new EditPersonDialog(mainView, true, model);
                    d.setVisible(true);
                }
                
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
                                    .getValueAt(view.crewDBTable
                                            .convertRowIndexToModel(rowIndex),
                                            0);
                            if (model.deletePerson(personId) < 0) {
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
                
                default -> {
                    // Requests focus back on to searchTextField
                    view.searchTextField.requestFocusInWindow();
                    view.searchTextField.selectAll();                   
                }                    
            }       
        }
    }
    
    
    
    // General purpose methods    
    // Sets Edit button status
    private void setEditButtonState() {
    
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
    
    // Sets Delete button status. If something is selected, Delete is enabled.
    private void setDeleteButtonState() {        
        if (view.crewDBTable.getSelectedRow() < 0) {
            view.deleteButton.setEnabled(false);
        } else {
            view.deleteButton.setEnabled(true);
        }       
    }   
    
    /*
     * This inner class implements the crew table model that will be used by
     * the POB Table. 
     * It will update the model whenever the Presenter notifies it.
     */
    private final class CrewTableModel extends AbstractTableModel {
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
    
        // Implements logic to retrieve the objects that will compose the rows 
        // of the table
        public void loadData() {
            List<Person> list = model.getAllPeople();            
            int listSize = list.size();            
            
            this.data = new Object[listSize][getColumnCount()];
            for (int i = 0; i < listSize; i++) {
                this.data[i][0] = list.get(i).getId();
                this.data[i][1] = list.get(i).getName();                               
                this.data[i][2] = list.get(i).getFunction()
                        .getFormalDescription();                
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
    }    
}