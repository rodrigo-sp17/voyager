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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;
import java.sql.*;
import java.time.LocalDate;


/**
 *
 * @author rodrigo
 */
public final class VoyagerModel extends StateNotifier {
    
    private static final String TAG = VoyagerModel.class.getSimpleName();    
        
    // Holds raft rules used to define the raft of boarded people
    private final Map<Object, Raft> raftRules;
    private final Properties raftProperties;
    
    // Current loaded POB for manipulation    
    private Pob currentPob;
    
    // Holds loaded localProperties
    private final Properties localProperties;    
    
    // Holds connection to the database
    private final Connection conn;        
    
    // Constructor
    public VoyagerModel() {        
        // Connects to database
        conn = DatabaseUtil.getConnection();
        
        // Loads localProperties
        localProperties = loadProperties();        
        
        // Loads raftRules
        raftProperties = loadRaftProperties();
        raftRules = loadRaftRules(raftProperties);                  
    }   
    
    // Loads localProperties from classpath
    private Properties loadProperties() {
       
        Properties defaults = new Properties();
        
        try (InputStream in = ClassLoader
                .getSystemResource("default.properties").openStream()) {
            defaults.load(in);            
            System.out.println("Default properties loaded successfully!");
        } catch (IOException f) {
            System.err.println(TAG 
                    + " - Error loading properties! "  
                    + f.getMessage());
        }
        
        Properties result = new Properties(defaults);
        try (InputStream in = new FileInputStream("local.properties")) {                
            result.load(in);
            System.out.println("Local Properties loaded successfully!");            
        } catch (FileNotFoundException e) {
            System.out.println("Could not find local properties. "
                    + "Using defaults...");
        } catch (IOException g) {
            System.err.println(TAG + " - Error loading local properties: " 
                    + g.getMessage());
            throw new RuntimeException("IOException while loading properties."
                    + " Closing application!");
        }        
        return result;
    }
    
    /**        
     * Saves modified localProperties to local.properties. Call this method
     * when closing the application to avoid excessive IO operations.
     */
    public void saveProperties() {
        try (OutputStream out = new FileOutputStream("local.properties")) {
            System.out.println("Saving local properties...");
            localProperties.store(out, "DO NOT MODIFY");
            System.out.println("Properties saved!");
        } catch (IOException e) {
            System.err.println(TAG + " - IO errror saving properties: " 
                    + e.getMessage());
        }
    }  
    
    public String getVessel() {
        return localProperties.getProperty("vessel_name");
    }    
    
    // Loads POB from localProperties 
    private Pob loadPob(final Properties localProperties) {
        int pobId = 1; // Can be any int if not saved to a RDB
        LocalDate date = LocalDate.now();
        String crew = localProperties.getProperty("pob_crew", "A");
        return new Pob(pobId, getAllBoardedPeople(), date, crew);        
    }    
    
    public Pob getPob() {        
        currentPob = loadPob(localProperties);
        return currentPob;
    }
    
    public void setPobDate(final LocalDate date) {        
        currentPob.setDateIssued(date);        
        fireStateChanged();
    }
    
    public void savePob(final Pob pob) {
        currentPob = pob;
        localProperties.setProperty("pob_crew", pob.getCrew());
    }       
    
    /**
     * Returns a List of Strings containing all crews persisted on
     * local.properties. If it is not possible to find any crew, returns null.
     * 
     * @return List{@code <String>} with all crews, or null if none 
     */
    public List<String> getAllCrews() {        
        String value = localProperties.getProperty("crews");
        if (value == null) {
            return null;
        }
        
        String[] crews = value.split(",");
        List<String> result = new ArrayList<>(Arrays.asList(crews));        
        return result;   
    }
    
    /**
     * Returns a List of Strings containing all cabins persisted on
     * local.properties. If it is not possible to find any cabin, returns null.
     * 
     * @return List{@code <String>} with all cabins, or null if none 
     */
    public List<String> getAllCabins() {        
        String value = localProperties.getProperty("cabins");
        if (value == null) {
            return null;
        }
        
        String[] cabins = value.split(",");
        List<String> result = new ArrayList<>(Arrays.asList(cabins));        
        return result;
    }
    
    /**
     * Returns a List of Strings containing all shifts persisted on
     * local.properties. If it is not possible to find any shift, returns null.
     * 
     * @return List{@code <String>} with all shifts, or null if none 
     */
    public List<String> getAllShifts() {        
        String value = localProperties.getProperty("shifts");
        if (value == null) {
            return null;
        }
        
        String[] shifts = value.split(",");
        List<String> result = new ArrayList<>(Arrays.asList(shifts));        
        return result;
    }
    
    /**
     * Retrieves Functions from database, which may be modified by the user.
     * 
     * @return List{@code <Function>} list of Function objects available 
     *                                in database
     */
    public List<Function> getAllFunctions() {
        String query = "SELECT * FROM functions";
        List<Function> list = new ArrayList<>();
        
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Function f = new Function(
                        rs.getInt("FUNCTION_ID"),
                        rs.getString("FUNCTION_PREFIX"),
                        rs.getString("FUNCTION_DESCRIPTION"),
                        rs.getInt("FUNCTION_VARIATION")
                );
                list.add(f);
            }
            
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + " Could not get functions from DB: " + e);
        }
        
        return list;
    }
    
    /**
     * Returns a Function instance corresponding to the supplied identifier. 
     * The identifier is the one returned by Function.getIdentifier().
     * 
     * @param identifier a String as returned from Function.getIdentifier()
     * @return Function instance corresponding to the supplied identifier, or
     *         null if not found
     */
    public Function getFunctionByIdentifier(final String identifier) {
        Function result = getAllFunctions().stream()
                .filter(f -> f.getIdentifier().equals(identifier))
                .findFirst()
                .orElse(null);
        
        return result;        
    }
    
    // Used by constructor to load raftProperties file
    private Properties loadRaftProperties() {
        Properties result = new Properties();
        
        try (InputStream in = new FileInputStream("raft.properties")) {    
            result.load(in);
            System.out.println("Raft Properties loaded successfully!");        
        } catch (IOException g) {
            System.err.println(TAG + " - Error loading local properties: " 
                    + g.getMessage());            
        }        
        return result;
    }
    
    // Used by constructor to parse raftProperties to a Map<Object, Raft>
    private Map<Object, Raft> loadRaftRules(final Properties raftProperties) {
        Map<Object, Raft> result = new HashMap<>();
        
        raftProperties.forEach((k,v) -> {
            if (v.equals("P")) {
                result.put(k, Raft.PORT);               
            } else if (v.equals("S")) {
                result.put(k, Raft.STBD);
            }
        });      
        
        return result;
    }
    
    /**
     * Returns a Map containing all the raft rules contained in localProperties
     * 
     * @return Map{@code <Object, Raft>} containing a Function identifier or 
     *         cabin as key, and a Raft type as value
     */    
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
    
    /**
     * Save Raft Rules as a property file, raft.properties. It parses Raft to
     * a string value which is read by loadRaftRules() method.
     * 
     * Call this method only when raftRules modifications is finished so as to
     * minimize IO operations.
     */
    public void saveRaftRules() {
        // Clears raftProperties for overwriting
        raftProperties.clear();
        
        // Iterates over raftRules MAP, adding strings instead of Raft objects
        raftRules.forEach((k,v) -> {
            if (v == Raft.PORT) {
                raftProperties.setProperty((String)k, "P");                
            } else {
                raftProperties.setProperty((String)k, "S");
            }
        });
        
        try (OutputStream out = new FileOutputStream("raft.properties")) {
            System.out.println("Saving raft properties...");
            raftProperties.store(out, "DO NOT MODIFY");
            System.out.println("Raft properties saved!");
        } catch (IOException e) {
            System.err.println(TAG + " - IO errror saving Raft properties: " 
                    + e.getMessage());
        }
    }
    
    public void removeRaftRule(final Object key) {      
        if (key instanceof Function) {
            raftRules.remove(((Function) key).getIdentifier());        
        } else {
            raftRules.remove(key);            
        }
        
        fireStateChanged();
    }
    
    /**
     * Returns the String text of a Person object's Raft based on raftRules 
     * loaded in the model.
     * 
     * @param person the Person instance to be analyzed through raftRules
     * @return Raft text corresponding to the Raft type of the Person. If the
     *         Raft type cannot be found, returns "N/A"
     */
    public String getRaft(final Person person) {
        String functionKey = person.getFunction().getIdentifier();
        String cabinKey = person.getCabin();
        
        // Checks if Function produces a Raft
        Raft result = raftRules.get(functionKey);
        if (result != null) {            
            return result.textPT();
        } 
        
        // Checks if the Cabin produces a Raft
        result = raftRules.get(cabinKey);
        if (result != null) {
            return result.textPT();
        } else {
            // If neither has produced any Raft, there is no rule
            return "N/A";
        }        
    }    
    
    /**
     * Inserts a Person in the database. The id field in Person is
     * ignored, since the database provided its own identification on INSERT
     * operations.
     * 
     * @param member the Person instance to add to the database 
     */
    public void insertPerson(final Person member) {        
        
        PreparedStatement insertCM = null;
        
        String insertString = 
                "INSERT INTO people ("
                + "PERSON_NAME,"
                + "PERSON_FUNCTION,"
                + "COMPANY,"
                + "NATIONALITY,"
                + "CIR,"
                + "CIR_EXP_DATE,"
                + "SISPAT,"
                + "BIRTH_DATE,"
                + "CREW,"
                + "IS_BOARDED,"
                + "BOARDING_DATE,"
                + "BOARDING_PLACE,"
                + "ARRIVAL_DATE,"
                + "ARRIVAL_PLACE,"
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
            insertCM.setDate(11, java.sql.Date.valueOf(member
                    .getBoardingDate()));
            insertCM.setString(12, member.getBoardingPlace());
            insertCM.setDate(13, java.sql.Date.valueOf(member
                    .getArrivalDate()));
            insertCM.setString(14, member.getArrivalPlace());
            insertCM.setString(15, member.getCabin());
            insertCM.setString(16, member.getShift());
            
            insertCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();     
            
        } catch (SQLException e) {
            System.err.println(TAG + " - Error inserting person: " 
                    + e.getMessage());            
        } finally {
            try {
                if (insertCM != null) {
                    insertCM.close();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println(TAG 
                        + " - Exception while closing commit: " 
                        + e.getMessage());
            }            
        }       
    }
    
    /**
     * Returns a Person instance containing the id of the parameter. It is 
     * assumed the ID is unique.
     * 
     * @param id the unique id as supplied by the database to the Person
     * @return Person instance corresponding to the id
     * 
     * @throws IllegalArgumentException if id is negative
     */
    public Person getPerson(final int id) {
        if (id < 0) {
            throw new IllegalArgumentException();
        }
        
        try {
            List<Person> list = requestQuery("SELECT * FROM people "
                    + "JOIN functions "
                    + "ON people.person_function"
                    + "= functions.function_id WHERE person_id ="
                    + id);
            return list.get(0);
        } catch (SQLException e) {
            System.err.println("Model - getPerson() - SQL Error");
            System.err.println(e.getMessage());
        }
        return null;       
    }
    
    // Returns List of all registered CrewMembers
    public List<Person> getAllPeople() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM people "
                    + "JOIN functions "
                    + "ON people.person_function "
                    + "= functions.function_id");            
            return list;
        } catch (SQLException e) {
            System.err.println("Model - getAllPeople() - SQL Error:");
            System.err.println(e.getMessage());
        }
        return null;
    }
    
    // Returns List of all boarded CrewMembers
    public List<Person> getAllBoardedPeople() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM people "
                    + "JOIN functions "
                    + "ON people.person_function "
                    + "= functions.function_id "
                    + "WHERE people.is_boarded=true");            
            return list;
        } catch (SQLException e) {
            System.err.println(TAG + e.getMessage());            
        }
        return null;
    }
    
    // Returns List of all non-boarded CrewMembers
    public List<Person> getAllNonBoardedPeople() {
        List<Person> list;
        try {
            list = requestQuery("SELECT * FROM people "
                    + "JOIN functions "
                    + "ON people.person_function "
                    + "= functions.function_id "
                    + "WHERE people.is_boarded=true");            
            return list;
        } catch (SQLException e) {
            System.err.println(TAG + e.getMessage());  
        }
        return null;
    }
    
    // Sets all Person of the provided crew as Boarded, and the others as 
    // not boarded
    public void boardCrew(final String crew) {
        // Checks if crew parameter exists
        if (!getAllCrews().contains(crew)) {
            throw new IllegalArgumentException("Provided crew does not exist!");
        }        

        // Unboard boarded
        String unboardQuery = "UPDATE people "
                + "SET is_boarded=false "
                + "WHERE is_boarded=true";
        
        try (Statement stmt = conn.createStatement()) {            
            stmt.executeUpdate(unboardQuery);                    
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + " - Error unboarding - " + e.getMessage());
        }
        
        // Board crew
        PreparedStatement boardStatement = null;
                
        String boardString = "UPDATE people "
                + "SET is_boarded=true "
                + "WHERE crew = ?";                
        
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
    public int updatePerson(final Person updatedMember) {
                
        PreparedStatement updateCM = null;
        
        String updateString = 
                "UPDATE people SET "
                + "person_name = ?,"
                + "person_function = ?,"
                + "company = ?,"
                + "nationality = ?,"
                + "cir = ?,"
                + "CIR_EXP_DATE = ?,"
                + "SISPAT = ?,"
                + "BIRTH_DATE = ?,"
                + "CREW = ?,"
                + "is_boarded = ?,"
                + "BOARDING_DATE = ?,"
                + "BOARDING_PLACE = ?,"
                + "ARRIVAL_DATE = ?,"
                + "ARRIVAL_PLACE = ?,"
                + "CABIN = ?,"
                + "SHIFT = ?"       
                + " WHERE PERSON_ID = ?";
        
        try {
            conn.setAutoCommit(false);
            updateCM = conn.prepareStatement(updateString   );
            
            updateCM.setString(1, updatedMember.getName());
            updateCM.setInt(2, updatedMember.getFunction().getFunctionId());
            updateCM.setString(3, updatedMember.getCompany());
            updateCM.setString(4, updatedMember.getNationality());
            updateCM.setString(5, updatedMember.getCir());
            updateCM.setDate(6, java.sql.Date.valueOf(updatedMember
                    .getCirExpDate()));
            updateCM.setString(7, updatedMember.getSispat());
            updateCM.setDate(8, java.sql.Date.valueOf(updatedMember
                    .getBirthDate()));
            updateCM.setString(9, updatedMember.getCrew());
            updateCM.setBoolean(10, updatedMember.isBoarded());
            updateCM.setDate(11, java.sql.Date.valueOf(updatedMember
                    .getBoardingDate()));
            updateCM.setString(12, updatedMember.getBoardingPlace());
            updateCM.setDate(13, java.sql.Date.valueOf(updatedMember
                    .getArrivalDate()));
            updateCM.setString(14, updatedMember.getArrivalPlace());
            updateCM.setString(15, updatedMember.getCabin());
            updateCM.setString(16, updatedMember.getShift());            
            updateCM.setInt(17, updatedMember.getId());
            
            updateCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();     
            
        } catch (SQLException e) {
            System.err.println("Model - updatePerson() - SQLError:");
            System.err.println(e.getMessage());
        } finally {
            try {
                if (updateCM != null) {
                    updateCM.close();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println(TAG + "- SQLError updating Person: " 
                        + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
            }            
        }             
        return 0;
    }
       
    /**
     * Deletes Person with specified Id
     * 
     * @param id the unique Id from the Person
     * @return 1 if successful, -1 if failed
     */
    public int deletePerson(final int id) {
        
        int result = -1;
        
        PreparedStatement deleteCM = null;
        
        String deleteString = "DELETE FROM people WHERE person_id = ?";
        
        try {
            conn.setAutoCommit(false);
            deleteCM = conn.prepareStatement(deleteString);
            
            deleteCM.setInt(1, id);
            
            result = deleteCM.executeUpdate();
            
            conn.commit();
            
            fireStateChanged();           
            
        } catch (SQLException e) {
            System.err.println("Model - deletePerson() - SQLError:");
            System.err.println(e.getMessage());            
        } finally {
            try {
                if (deleteCM != null) {
                    deleteCM.close();
                    conn.setAutoCommit(true);
                }
            } catch (SQLException e) {
                System.err.println(TAG + "- SQLError deleting Person: " 
                        + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
            }            
        }       
        return result;
    }    
    
     
    
    // Sends querys to connected database and returns as List<Person>
    private List<Person> requestQuery(String query)
            throws SQLException {
        List<Person> list = null;
        try (Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);            
            list = parseResult(rs);            
            stmt.close();
        } catch (SQLException e) {
            System.err.println(TAG + "- SQLError on requestQuery: " 
                        + e.getMessage());
                System.err.println("SQLState: " + e.getSQLState());
        }
        return list;
    }
    
    // Parses a ResultSet to Crew Member objects, than adds them to a list
    private List<Person> parseResult(ResultSet rs) {
        List<Person> list = new ArrayList<>();
        try {
            while (rs.next()) {
                Person c = new Person();
                c.setId(rs.getInt("PERSON_ID"));
                c.setName(rs.getString("PERSON_NAME"));
                c.setCompany(rs.getString("COMPANY"));
                c.setNationality(rs.getString("NATIONALITY"));
                c.setCir(rs.getString("CIR"));
                c.setCirExpDate(rs.getDate("CIR_EXP_DATE").toLocalDate());
                c.setSispat(rs.getString("SISPAT"));
                c.setBirthDate(rs.getDate("BIRTH_DATE").toLocalDate());
                c.setCrew(rs.getString("CREW"));
                c.setBoarded(rs.getBoolean("IS_BOARDED"));
                c.setBoardingDate(rs.getDate("BOARDING_DATE").toLocalDate());
                c.setBoardingPlace(rs.getString("BOARDING_PLACE"));
                c.setArrivalDate(rs.getDate("ARRIVAL_DATE").toLocalDate());
                c.setArrivalPlace(rs.getString("ARRIVAL_PLACE"));
                c.setCabin(rs.getString("CABIN"));
                c.setShift(rs.getString("SHIFT"));
                
                int id = rs.getInt("PERSON_FUNCTION");
                Function f = getAllFunctions()
                        .stream()
                        .filter(i -> i.getFunctionId() == id)
                        .findFirst()
                        .orElse(null);
                c.setFunction(f);
                
                list.add(c);                
            }
        } catch (SQLException e){
            System.err.println(TAG + " - Could not parse result: " 
                    + e.getMessage());
        }
        return list;
    }
}
