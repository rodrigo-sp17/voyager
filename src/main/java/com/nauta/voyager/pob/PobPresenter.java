/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.pob;

import com.nauta.voyager.dialog.BoardingDialog;
import com.nauta.voyager.people.CrewMember;
import com.nauta.voyager.dialog.EditBoardedDialog;
import com.nauta.voyager.Function;
import com.nauta.voyager.util.StateListener;
import com.nauta.voyager.VoyagerModel;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author rodrigo
 */
public class PobPresenter implements StateListener {
    
    private final String TAG = PobPresenter.class.getSimpleName();
    
    final private PobView view;
    final private VoyagerModel model;
    
    // Format of the dates used on the dialog
    private final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");
    
    // Constructor
    public PobPresenter(PobView view, VoyagerModel model) {
        this.view = view;
        this.model = model;        
        initPresentationLogic();
        readGUIStateFromDomain();
    }
    
    @Override
    public void onListenedStateChanged() {
       // Reloads pobTable model
        PobTableModel tModel = (PobTableModel) view.getPobTable().getModel();
        tModel.loadData();
    }    
   
    private void initPresentationLogic() {
        // Sets this class as a listener to VoyagerModel
        model.addStateListener(this);
        
        // Sets crewField with its possible data
        model.getAllCrews().forEach(s -> view.getCrewField().addItem(s));
        
        // Sets up pobTable
        JTable table = view.getPobTable();
        PobTableModel tableModel = new PobTableModel();
        tableModel.addTableModelListener(new TableSizeHandler());
        table.setModel(tableModel);
        TableRowSorter sorter = new TableRowSorter<>(tableModel);
        table.setRowSorter(sorter);
        PobTableHandler handler = new PobTableHandler();        
        table.getSelectionModel().addListSelectionListener(handler);
        table.addMouseListener(handler);
        
        
        // Sets PobDateField
        view.getPobDateField().addFocusListener(new DateFieldHandler());
        
        // Sets handlers for buttons
        ButtonsHandler bHandler = new ButtonsHandler();
        view.getAddMemberButton().addActionListener(bHandler);
        view.getDeleteMemberButton().addActionListener(bHandler);
        view.getPrintPobButton().addActionListener(bHandler);        
    }
    
    private void readGUIStateFromDomain() {
        LocalDate date = model.getLastPob().getDateIssued();
        view.getPobDateField().setText(date.format(view.DATE_FORMATTER));
        
        view.getPobSizeField().setText(
                Integer.toString(view.getPobTable().getModel().getRowCount()));
        
        String currentCrew = model.getLastPob().getCrew();
        view.getCrewField().setSelectedItem(currentCrew);       
    }
    
    private void writeGUIStateToDomain() {
        Pob pob = new Pob(99,
                model.getAllBoardedCrewMembers(),
                LocalDate.parse(view.getPobDateField().getText(), FORMATTER),
                view.getCrewField().getSelectedItem().toString());
        model.savePob(pob);
    }
    
    
    // Event handlers
    private final class DateFieldHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            LocalDate date = LocalDate.parse(view.getPobDateField().getText(),
                    view.DATE_FORMATTER);            
            model.setLastPobDate(date);
        }        
    }
    
    private final class PobTableHandler implements MouseListener,
            ListSelectionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // Opens EditPersonDialog when item clicked twice on the table
            JTable table =(JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);
            if (e.getClickCount() == 2) {
                Integer id = (Integer) table.getModel()
                    .getValueAt(table.convertRowIndexToModel(row),
                            0);
                new EditBoardedDialog(model, model.getCrewMember(id));
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
        public void valueChanged(ListSelectionEvent e) {
            setDeleteMemberButtonState();            
        }
        
    }    
    
    // Updates pobSizeField whenever the PobTable model changes, not the view
    private final class TableSizeHandler implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            int rows = ((TableModel) e.getSource()).getRowCount();
            view.getPobSizeField().setText(Integer.toString(rows));
        }        
    }
    
    private final class ButtonsHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            switch (e.getActionCommand()) {
                case "add" -> {
                    // Assumed the parant of POB View is MonitorFrame
                    new BoardingDialog(model);
                }
                
                case "delete" -> {
                    
                    JTable table = view.getPobTable();
                    int[] rows = table.getSelectedRows();
                    
                    // Promps for confirmation
                    int delete = JOptionPane.showConfirmDialog(
                            view,
                            "Tem certeza que deseja desembarcar o(s)\n" +
                                    rows.length + 
                                    " registro(s) selecionado(s)?",
                            "Desembarcar pessoal",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.WARNING_MESSAGE,
                            null
                    );
                    
                    // If confirmed, unboards personnel
                    if (delete == JOptionPane.OK_OPTION) {
                        for (int row : rows) {
                            // TODO - reduce number of reloading table
                            int cRow = table.convertRowIndexToModel(row);
                            Integer id = (Integer) table.getModel()
                                    .getValueAt(cRow, 0);
                            CrewMember person = model.getCrewMember(id);

                            // Sets the CrewMember instances' boarded as false
                            person.setBoarded(false);

                            // Sends changes for model to update
                            model.updateCrewMember(person);
                        }                        
                    }
                }
                
                case "print" -> {
                    // Shows final chooser for selecting place for new worksheet
                    writeGUIStateToDomain();
                    Pob pob = model.getLastPob();
                    
                    // Opens File Chooser with default name
                    final JFileChooser fc = new JFileChooser();
                    
                    // TODO - extract default to Properties
                    fc.setSelectedFile(new File("D:\\APPS\\POB-" 
                            + pob.getDateIssued().toString() 
                            + ".xls"));
                    
                    // Exports if allowed
                    int returnVal = fc.showSaveDialog(view);                    
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File outputFile = fc.getSelectedFile();                        
                        exportPOBToExcel(pob, outputFile);
                    }
                }
                
                default -> {                    
                }
            }            
        }
        
    }
    
    // Sets deleteMemberButton status according to existance of selected rows
    private void setDeleteMemberButtonState() {
        
        if (view.getPobTable().getSelectedRow() == -1) {            
            view.getDeleteMemberButton().setEnabled(false);            
        } else {
            view.getDeleteMemberButton().setEnabled(true);
        }        
    }
    
    // Exports the POB to the chosen outputFile
    // The template use is POB_std.xls
    private void exportPOBToExcel(Pob pob, File outputFile) {
        // TODO - remove hardcoded file reference
        try (InputStream in = new FileInputStream("POB_std.xls")) {
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            
            // Adds date to date field on template
            Cell dateCell = sheet.getRow(2).getCell(3);
            dateCell.setCellValue(pob.getDateIssued());
            
            // Adds crewmembers to their row            
            List<CrewMember> members = pob.getMembers();
            
            int startingRowIndex = 5; 
            int currentRowIndex = startingRowIndex;
            for (int i = 0; i < members.size(); i++) {
                
                Row currentRow = sheet.getRow(currentRowIndex);
                
                CrewMember member = members.get(i);
                currentRow.getCell(0).setCellValue(member.getCabin());
                currentRow.getCell(1).setCellValue(member.getName());
                currentRow.getCell(2).setCellValue(member.getCompany());
                currentRow.getCell(3).setCellValue(member.getFunctionId());
                currentRow.getCell(4).setCellValue(member.getShift());                
                currentRow.getCell(5).setCellValue(member.getBoardingDate());
                
                // Ensures the formula is correct
                Cell daysOnBoardCell = currentRow.getCell(7);
                daysOnBoardCell.setCellFormula("D3-F"+ (currentRowIndex + 1));
                
                // TODO - set the life raft
                
                currentRow.getCell(8).setCellValue(member.getSispat());                
                
                // Inserts new row if it is not the last item
                if (i != members.size() - 1) {
                    insertFormattedRow(sheet, currentRowIndex);
                }
                
                ++currentRowIndex;
            }
            
            wb.setForceFormulaRecalculation(true);
            
            try (OutputStream out = new FileOutputStream(outputFile)) {
                wb.write(out);
            }
            
        } catch (IOException e) {
            System.err.println(TAG 
                    + "Could not write to Excel" 
                    + e.getMessage());
        }      
        
    }
    
    // Helper method - inserts new formatted row for adding new data to table
    private void insertFormattedRow(final Sheet sheet, final int oldRow) {
        Row sourceRow = sheet.getRow(oldRow);
        
        Row newRow = sheet.createRow(oldRow + 1);
        
        for (int i = 0; i < sourceRow.getLastCellNum(); i++) {
            Cell oldCell = sourceRow.getCell(i);
            
            if (oldCell == null) {
                continue;
            }
            
            Cell newCell = newRow.createCell(i);
            
            newCell.setCellStyle(oldCell.getCellStyle());
            if (oldCell.getCellType() == CellType.FORMULA) {
                newCell.setCellFormula(oldCell.getCellFormula());                                
            }            
        }        
    }
    
    
    
    private final class PobTableModel extends AbstractTableModel {
        private final String[] columnNames = {
            "ID",
            "CABINE",
            "NOME",
            "EMPRESA",
            "FUNÇÃO",
            "HORÁRIO",
            "DATA DE EMBARQUE",
            "BALSA",
            "DIAS A BORDO",
            "SISPAT"
        };
        
        private Object[][] data;
        
        public PobTableModel() {
            loadData();
        }
        
        public void loadData() {
            List<CrewMember> list = model.getAllBoardedCrewMembers();
            List<Function> functions = model.getFunctions();
            
            int size = list.size();            
            data = new Object[size][getColumnCount()];
            for (int i = 0; i < size; i++) {
                data[i][0] = list.get(i).getId();
                data[i][1] = list.get(i).getCabin();
                data[i][2] = list.get(i).getName();
                data[i][3] = list.get(i).getCompany();
                
                int id = list.get(i).getFunctionId();
                String function = "";
                for (Function f : functions) {
                    if (f.getFunctionId() == id) {
                        function = f.getFunctionPrefix() 
                                + " - " 
                                + f.getFunctionDescription();
                    }
                }               
                data[i][4] = function;
                data[i][5] = list.get(i).getShift();
                data[i][6] = list.get(i).getBoardingDate();
                data[i][7] = calculateRaft((String) data[i][1]); // TODO
                data[i][8] = calculateDaysOnBoard(list.get(i).getBoardingDate());
                data[i][9] = list.get(i).getSispat();
            }
            
            fireTableDataChanged();
        }
        
        private long calculateDaysOnBoard(LocalDate date) {
            LocalDate pobDate = model.getLastPob().getDateIssued();
            LocalDate dateBoarded = date;
            return ChronoUnit.DAYS.between(dateBoarded, pobDate);
        }
        
        private String calculateRaft(String cabin) {
            // TODO - Dummy
            return "101";
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
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return data[rowIndex][columnIndex];
        }
        
    }
    
}
