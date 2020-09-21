/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.*;
import javax.swing.table.*;

/**
 *
 * @author rodrigo
 */
public class PobPresenter implements StateListener {
    
    final private PobView view;
    final private CrewMemberModel model;    
    
    // Constructor
    PobPresenter(PobView view, CrewMemberModel model) {
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
        model.addStateListener(this);
        
        // Sets up pobTable
        PobTableModel tableModel = new PobTableModel();
        view.getPobTable().setModel(tableModel);
        TableRowSorter sorter = new TableRowSorter<>(tableModel);
        view.getPobTable().setRowSorter(sorter);
        
        // Event handlers
        view.getPobDateField().addFocusListener(new DateFieldHandler());        
    }
    
    private void readGUIStateFromDomain() {
        LocalDate date = model.getLastPob().getDateIssued();
        view.getPobDateField().setText(date.format(view.DATE_FORMATTER));
    }
    
    // Event handlers
    class DateFieldHandler implements FocusListener {

        @Override
        public void focusGained(FocusEvent e) {
            return;
        }

        @Override
        public void focusLost(FocusEvent e) {
            LocalDate date = LocalDate.parse(view.getPobDateField().getText(),
                    view.DATE_FORMATTER);            
            model.setLastPobDate(date);
        }        
    }
    
    
    final class PobTableModel extends AbstractTableModel {
        private final String[] columnNames = {
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
                data[i][0] = list.get(i).getCabin();
                data[i][1] = list.get(i).getName();
                data[i][2] = list.get(i).getCompany();
                
                int id = list.get(i).getFunctionId();
                String function = "";
                for (Function f : functions) {
                    if (f.getFunctionId() == id) {
                        function = f.getFunctionPrefix() 
                                + " - " 
                                + f.getFunctionDescription();
                    }
                }               
                data[i][3] = function;
                data[i][4] = list.get(i).getShift();
                data[i][5] = list.get(i).getBoardingDate();
                data[i][6] = calculateRaft((String) data[i][0]); // TODO
                data[i][7] = calculateDaysOnBoard(list.get(i).getBoardingDate());
                data[i][8] = list.get(i).getSispat();
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
