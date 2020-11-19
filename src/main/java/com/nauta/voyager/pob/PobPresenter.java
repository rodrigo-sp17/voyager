/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager.pob;

import com.nauta.voyager.util.VoyagerContext;
import com.nauta.voyager.entity.Pob;
import com.nauta.voyager.service.RaftService;
import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.dialog.BoardingDialog;
import com.nauta.voyager.dialog.ChangeCrewDialog;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.dialog.EditBoardedDialog;
import com.nauta.voyager.util.StateListener;
import com.nauta.voyager.dialog.RaftRuleDialog;
import com.nauta.voyager.service.ExporterService;
import com.nauta.voyager.service.PersonService;
import com.nauta.voyager.service.PobService;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.*;

/**
 *
 * @author rodrigo
 */
public class PobPresenter implements StateListener {
    
    private final String TAG = PobPresenter.class.getSimpleName();
    
    private final PobView view;
    
    private final VoyagerContext context;
    
    private final PersonService personService;
    private final ExporterService exporterService;
    private final RaftService raftService;
    private final PobService pobService;
    
    // Format of the dates used on the dialog
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern("dd/MM/yyyy");
    
    // Constructor
    public PobPresenter(PobView view, VoyagerContext context) {
        this.view = view;
        this.context = context;
        
        personService = ServiceFactory.getPersonService();
        pobService = ServiceFactory.getPobService();
        raftService = ServiceFactory.getRaftService();
        exporterService = ServiceFactory.getExporterService();
        
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
        context.addStateListener(this);
        
        // Sets CrewField
        pobService.getAllCrews().forEach(s -> view.getCrewField().addItem(s));
        //view.getCrewField().addItemListener(new CrewFieldHandler());
        
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
        setTableRenderingPreferences();
        
        // Sets PobDateField
        view.getPobDateField().addFocusListener(new DateFieldHandler());
        
        // Sets handlers for buttons
        ButtonsHandler bHandler = new ButtonsHandler();
        view.getAddMemberButton().addActionListener(bHandler);
        view.getDeleteMemberButton().addActionListener(bHandler);
        view.getPrintPobButton().addActionListener(bHandler);
        view.getRaftRuleButton().addActionListener(bHandler);
        view.getChangeCrewButton().addActionListener(bHandler);
    }
    
    private void setTableRenderingPreferences() {
        // Sets Center Alighment render for columns
        DefaultTableCellRenderer centerRenderer = 
                new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
        centerRenderer.setVerticalAlignment(DefaultTableCellRenderer.CENTER);         
        
        TableColumnModel m = view.getPobTable().getColumnModel();
        m.getColumn(0).setPreferredWidth(40);
        m.getColumn(0).setCellRenderer(centerRenderer);
        m.getColumn(1).setPreferredWidth(40);
        m.getColumn(1).setCellRenderer(centerRenderer);
        m.getColumn(2).setPreferredWidth(250);        
        m.getColumn(3).setCellRenderer(centerRenderer);
        m.getColumn(4).setPreferredWidth(220);
        m.getColumn(5).setPreferredWidth(80);
        m.getColumn(5).setCellRenderer(centerRenderer);        
        m.getColumn(6).setCellRenderer(centerRenderer);
        m.getColumn(7).setCellRenderer(centerRenderer);
        m.getColumn(8).setPreferredWidth(80);
        m.getColumn(8).setCellRenderer(centerRenderer);
        m.getColumn(9).setPreferredWidth(80);
        m.getColumn(9).setCellRenderer(centerRenderer);
    }
    
    private void readGUIStateFromDomain() {
        Pob pob = pobService.getLastPob();
        LocalDate date = pob.getDateIssued();
        view.getPobDateField().setText(date.format(view.DATE_FORMATTER));
        
        // Sets total of people on the POB
        view.getPobSizeField().setText(
                Integer.toString(view.getPobTable().getModel().getRowCount()));
                
        String currentCrew = pob.getCrew();
        view.getCrewField().setSelectedItem(currentCrew);       
    }
    
    private void writeGUIStateToDomain() {
        Pob pob = pobService.getLastPob();
        pob.setDateIssued(LocalDate.parse(view.getPobDateField().getText(),
                FORMATTER));
        pob.setPeople(pobService.getAllBoardedPeople());
        pob.setCrew(view.getCrewField().getSelectedItem().toString());
                
        pobService.savePob(pob);
    }
    
    
    // Event handlers
    private final class DateFieldHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
        }

        @Override
        public void focusLost(FocusEvent e) {
            writeGUIStateToDomain();
            context.fireStateChanged();
        }        
    }
    
    private final class CrewFieldHandler implements ItemListener {
        
        @Override
        public void itemStateChanged(ItemEvent e) {
            
            if (e.getStateChange() == ItemEvent.SELECTED) {
                // Prompts if action is intentional
                int option = JOptionPane.showConfirmDialog(view,
                        "Deseja desembarcar os membros atuais e embarcar"
                                + " a turma selecionada?",
                        "Embarcar Nova Turma",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                     
                // If yes, boards the crew members
                if (option == JOptionPane.OK_OPTION) {
                    //model.boardCrew(e.getItem().toString());
                }
            }
        }
        
    }
    
    private final class PobTableHandler implements MouseListener,
            ListSelectionListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // Opens EditBoardedDialog when item clicked twice on the table
            JTable table =(JTable) e.getSource();
            Point point = e.getPoint();
            int row = table.rowAtPoint(point);
            if (e.getClickCount() == 2) {
                Long personId = (Long) table.getModel()
                    .getValueAt(table.convertRowIndexToModel(row),
                            0);
                
                // Gets the parent view
                JFrame topFrame = (JFrame) SwingUtilities
                        .getWindowAncestor(view);
                
                // Instantiates the dialog
                JDialog d = new EditBoardedDialog(topFrame, true, 
                        personService.getPersonById(personId));
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
            JFrame topFrame = (JFrame) SwingUtilities
                .getWindowAncestor(view);
            switch (e.getActionCommand()) {
                case "add":
                    JDialog d = new BoardingDialog(topFrame, true);
                    break;
                    
                
                case "delete":
                    // FIXME - solve multiple selection bug
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
                            Long personId = (Long) table.getModel()
                                    .getValueAt(cRow, 0);
                            Person person = personService
                                    .getPersonById(personId);

                            // Sets the Person instances' boarded as false
                            person.getBoardingData().setBoarded(false);

                            // Sends changes for model to update
                            personService.updatePerson(person);
                        }                        
                    }
                    context.fireStateChanged();
                    break;
                
                
                case "print":
                    // Shows final chooser for selecting place for new worksheet
                    writeGUIStateToDomain();
                    Pob pob = pobService.getLastPob();
                    
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
                        exporterService.exportPOBToExcel(pob, outputFile);
                    }
                    break;
                
                
                case "raft":                    
                    JDialog f = new RaftRuleDialog(topFrame, false);
                    break;
                    
                case "changeCrew":
                    JDialog c = new ChangeCrewDialog(topFrame, true);
                    c.setVisible(true);
                    break;
                
                default:                
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
    
    /*
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
            List<Person> members = pob.getMembers();
            
            int startingRowIndex = 5; 
            int currentRowIndex = startingRowIndex;
            for (int i = 0; i < members.size(); i++) {
                
                Row currentRow = sheet.getRow(currentRowIndex);
                
                Person member = members.get(i);
                currentRow.getCell(0).setCellValue(member.getCabin());
                currentRow.getCell(1).setCellValue(member.getName());
                currentRow.getCell(2).setCellValue(member.getCompany());
                currentRow.getCell(3).setCellValue(member.getFunction()
                        .getFormalDescription());
                currentRow.getCell(4).setCellValue(member.getShift());                
                currentRow.getCell(5).setCellValue(member.getBoardingDate());
                                
                currentRow.getCell(6).setCellValue(model.getRaft(member));
                
                // Ensures the formula is correct
                Cell daysOnBoardCell = currentRow.getCell(7);
                daysOnBoardCell.setCellFormula("D3-F"+ (currentRowIndex + 1));
                
                currentRow.getCell(8).setCellValue(member.getSispat());               
                
                // Inserts new row if it is not the last item
                if (i != members.size() - 1) {
                    insertFormattedRow(sheet, currentRowIndex);
                }
                
                ++currentRowIndex;
            }
            
            // Forces calculation of days on board
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
    */
    
    
    private final class PobTableModel extends AbstractTableModel {
        private final String[] columnNames = {
            "ID",
            "CAM.",
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
            List<Person> list = pobService.getAllBoardedPeople();            
            
            int size = list.size();            
            data = new Object[size][getColumnCount()];
            for (int i = 0; i < size; i++) {
                Person p = list.get(i);
                data[i][0] = p.getPersonId();
                data[i][1] = p.getBoardingData().getCabin();
                data[i][2] = p.getName();
                data[i][3] = p.getCompany();                              
                data[i][4] = p.getFunction();
                data[i][5] = p.getBoardingData().getShift();
                data[i][6] = p.getBoardingData().getBoardingDate();
                data[i][7] = raftService.getRaftByPerson(p);
                data[i][8] = calculateDaysOnBoard(p.getBoardingData()
                        .getBoardingDate());
                data[i][9] = list.get(i).getSispat();
            }
            
            fireTableDataChanged();
        }
        
        private long calculateDaysOnBoard(LocalDate dateBoarded) {
            LocalDate pobDate = pobService.getLastPob().getDateIssued();
            return ChronoUnit.DAYS.between(dateBoarded, pobDate);
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
