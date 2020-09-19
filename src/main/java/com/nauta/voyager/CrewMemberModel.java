/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.util.*;
import java.sql.*;
import java.time.LocalDate;


/*
TODO:
* - Transform to queries
* - Implement PreparedStatements on all fields to prevent Injection attacks
* - Implement function recovery
* Implement Update
- Check if notifying listeners implementation is adequate
- Implement updateModel method
*/

/**
 *
 * @author rodrigo
 */
public class CrewMemberModel extends StateNotifier {
    
    private static final String TAG = CrewMemberModel.class.getSimpleName();
    
    // Holds list of CrewMembers for manipulation
    private List<CrewMember> crewMemberList;
    
    // Current loaded POB for manipulation
    private Pob currentPob;
    
    // Holds connection data to the database
    private final Connection conn;
         
    // Model Constructor
    public CrewMemberModel() {        
        // Connects to database
        conn = DatabaseUtil.getConnection();              
    }
    
    
    public Pob getLastPob() {
        currentPob = new Pob(1, getAllCrewMembers(), LocalDate.now());
        return currentPob;
    }       
    
    // Creates CrewMember with new ID
    public void insertCrewMember(CrewMember member) {        
        
        PreparedStatement insertCM = null;
        
        String insertString = 
                "INSERT INTO \"persons\" ("
                + "NAME,"
                + "\"FUNCTION\","
                + "COMPANY,"
                + "NATIONALITY,"
                + "CIR,"
                + "CIREXPDATE,"
                + "SISPAT,"
                + "BIRTHDATE,"
                + "CREW,"
                + "BOARDED,"
                + "BOARDINGDATE,"
                + "BOARDINGPLACE,"
                + "ARRIVALDATE,"
                + "ARRIVALPLACE,"
                + "CABIN,"
                + "SHIFT"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";               
        
        try {
            conn.setAutoCommit(false);
            insertCM = conn.prepareStatement(insertString);
            
            insertCM.setString(1, member.getName());
            insertCM.setInt(2, member.getFunctionId());
            insertCM.setString(3, member.getCompany());
            insertCM.setString(4, member.getNationality());
            insertCM.setString(5, member.getCir());
            insertCM.setDate(6, java.sql.Date.valueOf(member.getCirExpDate()));            
            insertCM.setString(7, member.getSispat());
            insertCM.setDate(8, java.sql.Date.valueOf(member.getBirthDate()));
            insertCM.setString(9, member.getCrew());
            insertCM.setBoolean(10, member.isBoarded());
            insertCM.setDate(11, java.sql.Date.valueOf(member.getBoardingDate()));
            insertCM.setString(12, member.getBoardingPlace());
            insertCM.setDate(13, java.sql.Date.valueOf(member.getArrivalDate()));
            insertCM.setString(14, member.getArrivalPlace());
            insertCM.setString(15, member.getCabin());
            insertCM.setString(16, member.getShift());
            
            insertCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();     
            
        } catch (SQLException e) {
            System.err.println("Model - insertCM() - SQLError");
            System.err.println(e.getMessage());
        } finally {
            try {
                if (insertCM != null) {
                insertCM.close();
                conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                // TODO - error handling
            }            
        }       
    }

    // Gets crewmember with specified ID (It is assumed the id is unique!)
    public CrewMember getCrewMember(int id) {
        try {
            List<CrewMember> list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" = \"FUNCTIONS\".FUNCTION_ID WHERE PERSONID="
                    + id);
            return list.get(0);
        } catch (SQLException e) {
            System.err.println("Model - getCrewMember() - SQL Error");
            System.err.println(e.getMessage());
        }
        return null;       
    }
    
    // Returns List of all registered CrewMembers
    public List<CrewMember> getAllCrewMembers() {
        List<CrewMember> list;
        try {
            list = requestQuery("SELECT * FROM \"persons\" JOIN \"FUNCTIONS\" ON \"persons\".\"FUNCTION\" = \"FUNCTIONS\".\"FUNCTION_ID\"");            
            return list;
        } catch (SQLException e) {
            System.err.println("Model - getAllCrewMembers() - SQL Error:");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    // Returns List of all boarded CrewMembers
    public List<CrewMember> getAllBoardedCrewMembers() {
        List<CrewMember> list;
        try {
            list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" = \"FUNCTIONS\".\"FUNCTION_ID\" "
                    + "WHERE \"persons\".\"BOARDED\"=true");            
            return list;
        } catch (SQLException e) {
            System.err.println("Model - getAllBoardedCrewMembers() - SQL Error:");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    // Updates crew member with specified ID
    public int updateCrewMember(int id, CrewMember updatedMember) {
        
        PreparedStatement updateCM = null;
        
        String updateString = 
                "UPDATE \"persons\" SET "
                + "NAME = ?,"
                + "\"FUNCTION\" = ?,"
                + "COMPANY = ?,"
                + "NATIONALITY = ?,"
                + "CIR = ?,"
                + "CIREXPDATE = ?,"
                + "SISPAT = ?,"
                + "BIRTHDATE = ?,"
                + "CREW = ?,"
                + "BOARDED = ?,"
                + "BOARDINGDATE = ?,"
                + "BOARDINGPLACE = ?,"
                + "ARRIVALDATE = ?,"
                + "ARRIVALPLACE = ?,"
                + "CABIN = ?,"
                + "SHIFT = ?"       
                + " WHERE PERSONID = ?";
        
        try {
            conn.setAutoCommit(false);
            updateCM = conn.prepareStatement(updateString   );
            
            updateCM.setString(1, updatedMember.getName());
            updateCM.setInt(2, updatedMember.getFunctionId());
            updateCM.setString(3, updatedMember.getCompany());
            updateCM.setString(4, updatedMember.getNationality());
            updateCM.setString(5, updatedMember.getCir());
            updateCM.setDate(6, java.sql.Date.valueOf(updatedMember.getCirExpDate()));
            updateCM.setString(7, updatedMember.getSispat());
            updateCM.setDate(8, java.sql.Date.valueOf(updatedMember.getBirthDate()));
            updateCM.setString(9, updatedMember.getCrew());
            updateCM.setBoolean(10, updatedMember.isBoarded());
            updateCM.setDate(11, java.sql.Date.valueOf(updatedMember.getBoardingDate()));
            updateCM.setString(12, updatedMember.getBoardingPlace());
            updateCM.setDate(13, java.sql.Date.valueOf(updatedMember.getArrivalDate()));
            updateCM.setString(14, updatedMember.getArrivalPlace());
            updateCM.setString(15, updatedMember.getCabin());
            updateCM.setString(16, updatedMember.getShift());            
            updateCM.setInt(17, updatedMember.getId());
            
            updateCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();     
            
        } catch (SQLException e) {
            System.err.println("Model - updateCM() - SQLError:");
            System.err.println(e.getMessage());
        } finally {
            try {
                if (updateCM != null) {
                updateCM.close();
                conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                // TODO - error handling
            }            
        }             
        return 0;
    }
    
    /* 
     * Deletes crew member with specified ID
     * Returns 1 if successful, 0 if not    *   
    */
    public int deleteCrewMember(int id) {
        
        int result = 0;
        
        PreparedStatement deleteCM = null;
        
        String deleteString = "DELETE FROM \"persons\" WHERE PERSONID = ?";
        
        try {
            conn.setAutoCommit(false);
            deleteCM = conn.prepareStatement(deleteString);
            
            deleteCM.setInt(1, id);
            
            result = deleteCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();           
            
        } catch (SQLException e) {
            System.err.println("Model - deleteCM() - SQLError:");
            System.err.println(e.getMessage());            
        } finally {
            try {
                if (deleteCM != null) {
                deleteCM.close();
                conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                // TODO - error handling
            }            
        }       
        return result;
    }
    
    // Gets functions from database
    public List<Function> getFunctions() {
        String query = "SELECT * FROM \"FUNCTIONS\"";
        List<Function> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Function f = new Function(
                        rs.getInt("FUNCTION_ID"),
                        rs.getString("FUNCTION_PREFIX"),
                        rs.getString("FUNCTION_DESCRIPTION")
                );
                list.add(f);
            }
            
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + " Could not get functions from DB" + e);
        }
        
        return list;
    }
     
    
    // Sends querys to connected database and returns as list of crew members
    private List<CrewMember> requestQuery(String query) throws SQLException {
        List<CrewMember> list = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);            
            list = parseResult(rs);
            System.out.println("List size at CMM: " + list.size());
            stmt.close();
        } catch (SQLException e) {
            // TODO - proper error handling
            System.err.println(e.getMessage());
        }
        return list;
    }
    
    private void sendQuery(String query) {
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException e) {
        // TODO - error handling    
        }    
    }
    
    // Parses a ResultSet to Crew Member objects, than adds them to a list
    private List<CrewMember> parseResult(ResultSet rs) {
        List<CrewMember> list = new ArrayList<>();
        try {
            while (rs.next()) {
                CrewMember c = new CrewMember();
                c.setId(rs.getInt("PERSONID"));
                c.setName(rs.getString("NAME"));
                c.setCompany(rs.getString("COMPANY"));
                c.setNationality(rs.getString("NATIONALITY"));
                c.setCir(rs.getString("CIR"));
                c.setCirExpDate(rs.getDate("CIREXPDATE").toLocalDate());
                c.setSispat(rs.getString("SISPAT"));
                c.setBirthDate(rs.getDate("BIRTHDATE").toLocalDate());
                c.setCrew(rs.getString("CREW"));
                c.setBoarded(rs.getBoolean("BOARDED"));
                c.setBoardingDate(rs.getDate("BOARDINGDATE").toLocalDate());
                c.setBoardingPlace(rs.getString("BOARDINGPLACE"));
                c.setArrivalDate(rs.getDate("ARRIVALDATE").toLocalDate());
                c.setArrivalPlace(rs.getString("ARRIVALPLACE"));
                c.setCabin(rs.getString("CABIN"));
                c.setShift(rs.getString("SHIFT"));
                c.setFunctionId(rs.getInt("FUNCTION"));
                list.add(c);                
            }
        } catch (SQLException e){
            // TODO - do smth
        }
        return list;
    }
}
