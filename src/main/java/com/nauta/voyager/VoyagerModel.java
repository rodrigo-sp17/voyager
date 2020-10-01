/* License header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.nauta.voyager;

import com.nauta.voyager.pob.Raft;
import com.nauta.voyager.people.Function;
import com.nauta.voyager.people.Person;
import com.nauta.voyager.util.StateNotifier;
import com.nauta.voyager.util.DatabaseUtil;
import com.nauta.voyager.pob.Pob;
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
public final class VoyagerModel extends StateNotifier {
    
    private static final String TAG = VoyagerModel.class.getSimpleName();
    
    // Holds list of CrewMembers for manipulation
    private List<Person> crewMemberList;
    
    // Holds raft rules used to define the raft of boarded people
    private final Map<Object, Raft> raftRules;
    
    // Current loaded POB for manipulation    
    private Pob currentPob;
    
    // Holds connection data to the database
    private final Connection conn;
    
         
    
    // Constructor
    public VoyagerModel() {        
        // Connects to database
        conn = DatabaseUtil.getConnection();
        
        // TODO - remove, its DUMMY
        currentPob = new Pob(1, getAllCrewMembers(), LocalDate.now(), "A");
        
        raftRules = new HashMap<>();
    }
    
    
    public Pob getLastPob() {
        // TODO        
        return currentPob;
    }
    
    public void setLastPobDate(LocalDate date) {
        // DUMMY
        // TODO
        currentPob.setDateIssued(date);        
        fireStateChanged();
    }
    
    public void savePob(final Pob pob) {
        currentPob = pob;
    }    
    
    private final String[] CREWS = {"A", "B", "N/A"};
    
    public List<String> getAllCrews() {
        return new ArrayList<>(Arrays.asList(CREWS));      
    }
    
    private final String[] CABINS = {"401", "402", "403"};
    
    public List<String> getAllCabins() {
        return new ArrayList<>(Arrays.asList(CABINS));
    }
    
    // TODO - remove hardcoded
    private final String[] SHIFTS = {
        "0600-1800",
        "1800-0600",
        "0000-1200",
        "1200-2400",
        "24h",
        "N/A"
    };
    
    public List<String> getAllShifts() {
        return new ArrayList<>(Arrays.asList(SHIFTS));        
    }
    
    /**
     * Retrieves Functions from database, which may be modified by the user.
     * 
     * @return List{@code <Function>} list of Function objects available 
     *                                in database
     */
    public List<Function> getAllFunctions() {
        String query = "SELECT * FROM \"FUNCTIONS\"";
        List<Function> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Function f = new Function(
                        rs.getInt("FUNCTION_ID"),
                        rs.getString("FUNCTION_PREFIX"),
                        rs.getString("FUNCTION_DESCRIPTION"), 0
                );
                list.add(f);
            }
            
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + " Could not get functions from DB" + e);
        }
        
        return list;
    }
    
    public Function getFunctionByIdentifier(String identifier) {
        Function result = getAllFunctions().stream()
                .filter(f -> f.getIdentifier().equals(identifier))
                .findFirst()
                .orElse(null);
        
        return result;        
    }
    
    public Map<Object, Raft> getAllRaftRules() {
        return raftRules;
    }
    
    /**
     * Adds a raft rule. Key is either a Function object or a String cabin.
     * 
     * @param key A string, either a Function object or a String cabin
     * @param raft A Raft type, defining the raft side the member should go to
     *  
     */
    public void addRaftRule(Object key, Raft raft) {
        if (key instanceof Function) {
            raftRules.put(((Function) key).getIdentifier(), raft);        
        } else {
            raftRules.put(key, raft);            
        }   
        
        fireStateChanged();    
    }
    
    public void removeRaftRule(Object key) {      
        if (key instanceof Function) {
            raftRules.remove(((Function) key).getIdentifier());        
        } else {
            raftRules.remove(key);            
        }       
        
        fireStateChanged();
    }
    
    public String getRaft(final Person person) {
        String functionKey = person.getFunction().getIdentifier();
        String cabinKey = person.getCabin();
                   
        Raft result = raftRules.get(functionKey);
        if (result != null) {
            //DEBUG
            System.out.println("Function raft returned");
            return result.textPT();
        } 
        
        result = raftRules.get(cabinKey);
        if (result != null) {
            return result.textPT();
        } else {
            return "N/A";
        }       
    }
    
    
    /**
     * Inserts a CrewMember in the database. The id field in CrewMember is
     * ignored, since the database provided its own identification on INSERT
     * operations.
     * 
     * @param member    the Person instance to add to the database 
     */
    public void insertCrewMember(Person member) {        
        
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
            insertCM.setInt(2, member.getFunction().getFunctionId());
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
    public Person getCrewMember(int id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        
        try {
            List<Person> list = requestQuery("SELECT * FROM \"persons\" "
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
    public List<Person> getAllCrewMembers() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" "
                    + "= \"FUNCTIONS\".\"FUNCTION_ID\"");            
            return list;
        } catch (SQLException e) {
            System.err.println("Model - getAllCrewMembers() - SQL Error:");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    // Returns List of all boarded CrewMembers
    public List<Person> getAllBoardedCrewMembers() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" "
                    + "= \"FUNCTIONS\".\"FUNCTION_ID\" "
                    + "WHERE \"persons\".\"BOARDED\"=true");            
            return list;
        } catch (SQLException e) {
            System.err.println(TAG + e.getMessage());            
        }
        return null;
    }
    
    // Returns List of all non-boarded CrewMembers
    public List<Person> getAllNonBoardedCrewMembers() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM \"persons\" "
                    + "JOIN \"FUNCTIONS\" "
                    + "ON \"persons\".\"FUNCTION\" "
                    + "= \"FUNCTIONS\".\"FUNCTION_ID\" "
                    + "WHERE \"persons\".\"BOARDED\"=false");            
            return list;
        } catch (SQLException e) {
            System.err.println(TAG + e.getMessage());  
        }
        return null;
    }
    
    // Sets all Person of the provided crew as Boarded, and the others as 
    // not boarded
    public void boardCrew(String crew) {
        // Checks if crew parameter exists
        if (!getAllCrews().contains(crew)) {
            throw new IllegalArgumentException("Provided crew does not exist!");
        }        

        // Unboard boarded
        String unboardQuery = "UPDATE \"persons\" "
                + "SET \"BOARDED\"=false "
                + "WHERE \"BOARDED\"=true";
        
        try (Statement stmt = conn.createStatement()) {            
            stmt.executeUpdate(unboardQuery);                    
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + " - Error unboarding - " + e.getMessage());
        }
        
        // Board crew
        PreparedStatement boardStatement = null;
                
        String boardString = "UPDATE \"persons\" "
                + "SET \"BOARDED\"=true "
                + "WHERE \"CREW\" = ?";
                
        
        try {            
            conn.setAutoCommit(false);
            boardStatement = conn.prepareStatement(boardString);
            boardStatement.setString(1, crew);
            boardStatement.executeUpdate();
            conn.commit();
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            System.err.println(TAG + " - Error boarding - " + e.getMessage());
        } finally {
            try {
                if (boardStatement != null) {
                    boardStatement.close();
                    conn.setAutoCommit(true);                
            }
            } catch (SQLException e) {
                System.err.println(TAG + "Error closing statement" 
                        + e.getMessage());
            }
        }       
        
        fireStateChanged();        
    }
    
    // Updates crew member with specified ID
    public int updateCrewMember(Person updatedMember) {
                
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
            updateCM.setInt(2, updatedMember.getFunction().getFunctionId());
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
     * Returns 1 if successful, 0 if not 
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
    private List<Person> requestQuery(String query) throws SQLException {
        List<Person> list = null;
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
    
    // Parses a ResultSet to Crew Member objects, than adds them to a list
    private List<Person> parseResult(ResultSet rs) {
        List<Person> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Person c = new Person();
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
                
                int id = rs.getInt("FUNCTION");
                Function f = getAllFunctions()
                        .stream()
                        .filter(i -> i.getFunctionId() == id)
                        .findFirst()
                        .orElse(null);
                c.setFunction(f);
                
                list.add(c);                
            }
        } catch (SQLException e){
            System.err.println(TAG + " - Could not parse result" 
                    + e.getMessage());
        }
        return list;
    }
}
