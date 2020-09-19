/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.swing.table.*;

/**
 *
 * @author rodrigo
 */
public class PobPresenter implements StateListener {
    final private MonitorFrame view;
    final private CrewMemberModel model;   
    
    PobPresenter(MonitorFrame view, CrewMemberModel model) {
        this.view = view;
        this.model = model;
        initPresentationLogic();
    }
    
    @Override
    public void onListenedStateChanged() {
        
    }
    
    private void initPresentationLogic() {
        model.addStateListener(this);        
    }
    
    private void readGUIStateFromDomain() {
        
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
            return ChronoUnit.DAYS.between(pobDate, dateBoarded);
        }
        
        private String calculateRaft(String cabin) {
            // TODO
            return null;
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
        
    }
    
}
