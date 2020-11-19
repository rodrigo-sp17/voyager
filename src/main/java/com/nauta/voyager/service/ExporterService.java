package com.nauta.voyager.service;

import com.nauta.voyager.util.ServiceFactory;
import com.nauta.voyager.entity.Person;
import com.nauta.voyager.entity.Pob;
import com.nauta.voyager.service.exception.ExportException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;


/**
 * This class is responsible for exporting data to outside files, such as excel
 * 
 * @author Rodrigo
 */
public class ExporterService {
    
    private static final String TAG = ExporterService.class.getSimpleName();
    
    private RaftService raftService;

    public ExporterService() {
        raftService = ServiceFactory.getRaftService();
    }
    
    
    
    // Exports the POB to the chosen outputFile
    // The template use is POB_std.xls
    public void exportPOBToExcel(Pob pob, File outputFile) 
            throws ExportException {
        // TODO - remove hardcoded file reference
        try (InputStream in = getClass().getClassLoader()
                .getResourceAsStream("POB_std.xls")) {
            Workbook wb = WorkbookFactory.create(in);
            Sheet sheet = wb.getSheetAt(0);
            
            // Adds date to date field on template
            Cell dateCell = sheet.getRow(2).getCell(3);
            dateCell.setCellValue(pob.getDateIssued());
            
            // Adds crewmembers to their row            
            List<Person> members = pob.getPeople();
            
            int startingRowIndex = 5; 
            int currentRowIndex = startingRowIndex;
            for (int i = 0; i < members.size(); i++) {
                
                Row currentRow = sheet.getRow(currentRowIndex);
                
                Person member = members.get(i);
                currentRow.getCell(0).setCellValue(member.getBoardingData()
                        .getCabin().toString());
                currentRow.getCell(1).setCellValue(member.getName());
                currentRow.getCell(2).setCellValue(member.getCompany());
                currentRow.getCell(3).setCellValue(member.getFunction()
                        .toString());
                currentRow.getCell(4).setCellValue(member.getBoardingData()
                        .getShift());                
                currentRow.getCell(5).setCellValue(member.getBoardingData()
                        .getBoardingDate());                                
                currentRow.getCell(6).setCellValue(raftService
                        .getRaftByPerson(member));
                
                // Ensures the formula is correct
                Cell daysOnBoardCell = currentRow.getCell(7);
                daysOnBoardCell.setCellFormula("$D$3-F"+ (currentRowIndex + 1));
                
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
            System.err.println(TAG + "Could not write to Excel" 
                    + e.getMessage());
            throw new ExportException("Error exporting Excel");
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
    
    
}
