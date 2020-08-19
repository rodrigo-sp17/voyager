/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import java.util.*;
import java.sql.*;


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
    
    // Holds list of CrewMembers for manipulation
    private List<CrewMember> crewMemberList;
    
    // Holds connection data to the database
    private Connection conn;
         
    // Model Constructor
    public CrewMemberModel() {        
        // Connects to database
        conn = DatabaseUtil.getConnection();              
    }
       
    
    // Creates CrewMember with new ID
    public void insertCrewMember(CrewMember member) {        
        
        PreparedStatement insertCM = null;
        
        String insertString = 
                "INSERT INTO \"persons\" ("
                + "NAME,"
                + "COMPANY,"
                + "SISPAT,"
                + "NATIONALITY,"
                + "BIRTHDATE,"
                + "CIR,"
                + "CIREXPDATE"
                + ") VALUES (?, ?, ?, ?, ?, ?, ?)";               
        
        try {
            conn.setAutoCommit(false);
            insertCM = conn.prepareStatement(insertString);
            
            insertCM.setString(1, member.getName());
            //insertCM.setInt(2, 2); // TODO: ADD FUNCTION RECOVERY
            insertCM.setString(2, member.getCompany());
            insertCM.setString(3, member.getSispat());
            insertCM.setString(4, member.getNationality());
            insertCM.setDate(5, java.sql.Date.valueOf(member.getBirthDate()));
            insertCM.setString(6, member.getCir());
            insertCM.setDate(7, java.sql.Date.valueOf(member.getCirExpDate()));            
            
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
            list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" = \"FUNCTIONS\".FUNCTION_ID");            
            return list;
        } catch (SQLException e) {
            System.err.println("Model - getAllCrewMembers() - SQL Error:");
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
                //+ "FUNCTION = ?,"
                + "COMPANY = ?," 
                + "SISPAT = ?,"
                + "NATIONALITY = ?,"
                + "BIRTHDATE = ?,"
                + "CIR = ?,"
                + "CIREXPDATE = ?"                
                + " WHERE PERSONID = ?";
        
        try {
            conn.setAutoCommit(false);
            updateCM = conn.prepareStatement(updateString   );
            
            updateCM.setString(1, updatedMember.getName());
            //insertCM.setInt(2, 2); // TODO: ADD FUNCTION RECOVERY
            updateCM.setString(2, updatedMember.getCompany());
            updateCM.setString(3, updatedMember.getSispat());
            updateCM.setString(4, updatedMember.getNationality());
            updateCM.setDate(5, java.sql.Date.valueOf(updatedMember.getBirthDate()));
            updateCM.setString(6, updatedMember.getCir());
            updateCM.setDate(7, java.sql.Date.valueOf(updatedMember.getCirExpDate()));
            updateCM.setInt(8, updatedMember.getId());
            
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
 
    
    // Sends querys to connected database and returns as list of crew members
    private List<CrewMember> requestQuery(String query) throws SQLException {
        List<CrewMember> list = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            list = parseResult(rs);
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
                c.setFunction(rs.getString("FUNCTION_PREFIX") 
                        + " - " 
                        + rs.getString("FUNCTION_DESCRIPTION"));
                list.add(c);                
            }
        } catch (SQLException e){
            // TODO - do smth
        }
        return list;
    }
}
