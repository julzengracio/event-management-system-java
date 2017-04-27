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

public class ManagerGateway {
    private static final String MANAGER_TABLE = "managers";
    private static final String MANAGER_ID = "managerID";
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String EMAIL_ADDRESS = "Email";
    private static final String NUMBER = "Number";
    private static final String SALARY = "Salary";

    private Connection mConnection;

    public ManagerGateway(Connection connect) {
        mConnection = connect;
    }

    public int insertManager(String fName, String lName, String em, int num, double sal) throws SQLException {

        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;
        int id = -1;        
        
        // INSERT sql statement that looks for the table name and get the users' input for the values. 
        query = "INSERT INTO " + MANAGER_TABLE + " (" +
                FIRST_NAME      + ", " +
                LAST_NAME       + ", " +
                EMAIL_ADDRESS   + ", " +
                NUMBER          + ", " +
                SALARY          +
                ") VALUES (?, ?, ?, ?, ?)";

        // prepared statement object that executes the query. Insert values to the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, fName);
        stmt.setString(2, lName);
        stmt.setString(3, em);
        stmt.setInt(4, num);
        stmt.setDouble(5, sal);

        // executes the query and make sure only 1 row will be inserted into the database
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // retrieve the id if a row is inserted
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);
        }
        // return the object created, or null if a problem occured
        return id;
    }
    
    public boolean deleteManager(int id) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;

        // the DELETE sql statement: which refers to the ID of the data to be deleted
        query = "DELETE FROM " + MANAGER_TABLE + " WHERE " + MANAGER_ID + " = ?";

        // prepared statement object that executes the query. Insert the ID to the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        // execute query
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
    
    public boolean updateManager(ManagementStaff m) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;

        // UPDATE sql statement that looks for the table name and get the users' input for new values
        query = "UPDATE " + MANAGER_TABLE + " SET " +
                FIRST_NAME        + " = ?, " +
                LAST_NAME         + " = ?, " +
                EMAIL_ADDRESS     + " = ?, " +
                NUMBER            + " = ?, " +
                SALARY            + " = ?, " +
                " WHERE " + MANAGER_ID + " = ?";

        // prepared statement object that executes the query. Takes the new values that the user input
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, m.getFname());
        stmt.setString(2, m.getLname());
        stmt.setString(3, m.getEmail());
        stmt.setInt(4, m.getNumber());
        stmt.setDouble(5, m.getSalary());
       
        // execute the query
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

    public List<ManagementStaff> getManagers() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // java sql statement object to used to execute SQL
        ResultSet rs;                   // java sql result set representing the result of the SQL query
        List<ManagementStaff> mgr;           // the list containing the lcoation object created for each row

        String fName, lName, em;
        int num, managerID;
        double sal;

        ManagementStaff m;                   // a Attendees object created from a row in the result of the query

        // execute SELECT sql statement to get the result set.
        query = "SELECT * FROM " + MANAGER_TABLE;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

       /*
        * Extracts the data from each row and sort it in a Attendees object
        * which is inserted into an initially empty array list.
        */
        mgr = new ArrayList<ManagementStaff>();
        while (rs.next()) {
            managerID = rs.getInt(MANAGER_ID);
            fName = rs.getString(FIRST_NAME);
            lName = rs.getString(LAST_NAME);
            em = rs.getString(EMAIL_ADDRESS);
            num = rs.getInt(NUMBER);
            sal = rs.getDouble(SALARY);

            m = new ManagementStaff(managerID, fName, lName, em, num, sal);
            mgr.add(m);
        }
        
        // return the list of Attendees object retrieved
        return mgr;
    }
}
