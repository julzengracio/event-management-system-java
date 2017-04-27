package EventManagementCompany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

public class LocationGateway {
    
    private static final String LOCATION_TABLE = "locations";
    private static final String LOCATION_ID = "locationID";
    private static final String LOCATION_NAME = "Name";
    private static final String LOCATION_ADDRESS = "Address";
    private static final String L_MANAGER_FNAME = "ManagerFName";
    private static final String L_MANAGER_LNAME = "ManagerLName";
    private static final String L_MANAGER_EMAIL = "ManagerEmail";
    private static final String L_MANAGER_NUMBER = "ManagerNumber";
    private static final String LOCATION_CAP = "MaxCapacity";
    
    
    private Connection mConnection;
    
    public LocationGateway(Connection connect) {
        mConnection = connect;
    }
    
    public int insertLocation(String n, String a, String mFName, String mLName, String mEmail, int mNum, int cap) throws SQLException {
        String query;                       // the SQL query to execute
        PreparedStatement preState;         // prepared statement object to used to execute SQL
        int numRowsAffected;
        int id = -1;
        
        
        // INSERT sql statement that looks for the table name and get the users' input for the values.
        query = "INSERT INTO " + LOCATION_TABLE + " (" +
                LOCATION_NAME      + ", " +
                LOCATION_ADDRESS   + ", " +
                L_MANAGER_FNAME    + ", " +
                L_MANAGER_LNAME    + ", " +
                L_MANAGER_EMAIL   + ", " + 
                L_MANAGER_NUMBER  + ", " +
                LOCATION_CAP       +
                ") VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        // prepared statement object that executes the query. Insert values to the query
        preState = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preState.setString(1, n);
        preState.setString(2, a);
        preState.setString(3, mFName);
        preState.setString(4, mLName);
        preState.setString(5, mEmail);
        preState.setInt(6, mNum);
        preState.setInt(7, cap);
        
        // executes the query and make sure only 1 row will be inserted into the database
        numRowsAffected = preState.executeUpdate();
        if (numRowsAffected == 1) {
            // retrieve the id if a row is inserted
            ResultSet keys = preState.getGeneratedKeys(); 
            keys.next();
            
            id = keys.getInt(1);
        }
        // return the object created, or null if a problem occured
        return id;
    }
    
    public boolean deleteLocations(int id) throws SQLException {
        String query;                       // the SQL query to execute
        PreparedStatement preState;         // prepared statement object to used to execute SQL
        int numRowsAffected;
        
        // the DELETE sql statement: which refers to the ID of the data to be deleted
        query = "DELETE FROM " + LOCATION_TABLE + " WHERE " + LOCATION_ID + " = ?";
        
        // prepared statement object that executes the query. Insert the ID to the query
        preState = mConnection.prepareStatement(query);
        preState.setInt(1, id);
        
        // execute query
        numRowsAffected = preState.executeUpdate();
        
        return (numRowsAffected == 1);
    }
    
    public boolean updateLocations(NewEventLocation eLoc) throws SQLException {
        String query;                       // the SQL query to execute
        PreparedStatement preState;         // prepared statement object to used to execute SQL
        int numRowsAffected;
        
        // UPDATE sql statement that looks for the table name and get the users' input for new values
        query = "UPDATE " + LOCATION_TABLE + " SET " +
                LOCATION_NAME             + " = ?, " +
                LOCATION_ADDRESS          + " = ?, " +
                L_MANAGER_FNAME           + " = ?, " +
                L_MANAGER_LNAME           + " = ?, " +
                L_MANAGER_EMAIL           + " = ?, " +
                L_MANAGER_NUMBER          + " = ?, " +
                LOCATION_CAP              +
                " WHERE " + LOCATION_ID + " = ?";
        
        // prepared statement object that executes the query. Takes the new values that the user input
        preState = mConnection.prepareStatement(query);
        preState.setString(1, eLoc.getName());
        preState.setString(2, eLoc.getAddress());
        preState.setString(3, eLoc.getMgr().getFname());
        preState.setString(4, eLoc.getMgr().getLname());
        preState.setString(5, eLoc.getMgr().getEmail());
        preState.setInt(6, eLoc.getMgr().getNumber());
        preState.setInt(7, eLoc.getMaxCapacity());
        
        // execute the query
        numRowsAffected = preState.executeUpdate();
        
        return (numRowsAffected == 1);
    }
    
    public List<NewEventLocation> getEventLocations() throws SQLException {
        String query;                           // the SQL query to execute
        Statement state;                        // java sql statement object to used to execute SQL
        ResultSet result;                       // java sql result set representing the result of the SQL query
        List<NewEventLocation> eLocations;         // the list containing the lcoation object created for each row
        
        String name, address, mFName, mLName, mEmail;
        int cap, mNum, id;
        
        NewEventLocation eLoc;                     // a EventLocation object created from a row in the result of the query
        
        // execute SELECT sql statement to get the result set.
        query = "SELECT * FROM " + LOCATION_TABLE;
        state = this.mConnection.createStatement();
        result = state.executeQuery(query);
        
        /*
        * Extracts the data from each row and sort it in a EventLocation object
        * which is inserted into an initially empty array list.
        */
        eLocations = new ArrayList<NewEventLocation>();
        while (result.next()) {
            id = result.getInt(LOCATION_ID);
            name = result.getString(LOCATION_NAME);
            address = result.getString(LOCATION_ADDRESS);
            mFName = result.getString(L_MANAGER_FNAME);
            mLName = result.getString(L_MANAGER_LNAME);
            mEmail = result.getString(L_MANAGER_EMAIL);
            mNum = result.getInt(L_MANAGER_NUMBER);   
            cap = result.getInt(LOCATION_CAP);
            
            eLoc = new NewEventLocation(id, name, address, mFName, mLName, mEmail, mNum, cap);
            eLocations.add(eLoc);
        }
        
        // return the list of EventLocations object retrieved
        return eLocations;
    }    

}
