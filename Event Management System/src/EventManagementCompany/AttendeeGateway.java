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

public class AttendeeGateway {
    private static final String ATTENDEE_TABLE = "attendees";
    private static final String ATTENDEE_ID = "attendeeID";
    private static final String ATTENDEE_FNAME = "FName";
    private static final String ATTENDEE_LNAME = "LName";
    private static final String ATTENDEE_EMAIL = "Email";
    private static final String ATTENDEE_MOBILE = "Mobile";
    private static final String ATTENDEE_PAY = "AmountPaid";

    private Connection mConnection;

    public AttendeeGateway(Connection connect) {
        mConnection = connect;
    }

    public int insertAttendee(String aFName, String aLName, String aEmail, int aMobile, double aPay) throws SQLException {

        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;
        int id = -1;        
        
        // INSERT sql statement that looks for the table name and get the users' input for the values. 
        query = "INSERT INTO " + ATTENDEE_TABLE + " (" +
                ATTENDEE_FNAME  + ", " +
                ATTENDEE_LNAME  + ", " +
                ATTENDEE_EMAIL  + ", " +
                ATTENDEE_MOBILE + ", " +
                ATTENDEE_PAY    +
                ") VALUES (?, ?, ?, ?, ?)";

        // prepared statement object that executes the query. Insert values to the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, aFName);
        stmt.setString(2, aLName);
        stmt.setString(3, aEmail);
        stmt.setInt(4, aMobile);
        stmt.setDouble(5, aPay);

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
    
    public boolean deleteAttendee(int id) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;

        // the DELETE sql statement: which refers to the ID of the data to be deleted
        query = "DELETE FROM " + ATTENDEE_TABLE + " WHERE " + ATTENDEE_ID + " = ?";

        // prepared statement object that executes the query. Insert the ID to the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        // execute query
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
    
    public boolean updateAttendee(Attendee a) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // prepared statement object to used to execute SQL
        int numRowsAffected;

        // UPDATE sql statement that looks for the table name and get the users' input for new values
        query = "UPDATE " + ATTENDEE_TABLE + " SET " +
                ATTENDEE_FNAME             + " = ?, " +
                ATTENDEE_LNAME             + " = ?, " +
                ATTENDEE_EMAIL             + " = ?, " +
                ATTENDEE_MOBILE            + " = ?, " +
                ATTENDEE_PAY               + " = ?, " +
                " WHERE " + ATTENDEE_ID + " = ?";

        // prepared statement object that executes the query. Takes the new values that the user input
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, a.getFname());
        stmt.setString(2, a.getLname());
        stmt.setString(3, a.getEmail());
        stmt.setInt(4, a.getNumber());
        stmt.setDouble(5, a.getAmountPaid());
       
        // execute the query
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }

    public List<Attendee> getAttendees() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // java sql statement object to used to execute SQL
        ResultSet rs;                   // java sql result set representing the result of the SQL query
        List<Attendee> atts;           // the list containing the lcoation object created for each row

        String aFName, aLName, aEmail;
        int aMobile, attendeeID;
        double amountPaid;

        Attendee a;                   // a Attendees object created from a row in the result of the query

        // execute SELECT sql statement to get the result set.
        query = "SELECT * FROM " + ATTENDEE_TABLE;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

       /*
        * Extracts the data from each row and sort it in a Attendees object
        * which is inserted into an initially empty array list.
        */
        atts = new ArrayList<Attendee>();
        while (rs.next()) {
            attendeeID = rs.getInt(ATTENDEE_ID);
            aFName = rs.getString(ATTENDEE_FNAME);
            aLName = rs.getString(ATTENDEE_LNAME);
            aEmail = rs.getString(ATTENDEE_EMAIL);
            aMobile = rs.getInt(ATTENDEE_MOBILE);
            amountPaid = rs.getDouble(ATTENDEE_PAY);

            a = new Attendee(attendeeID, aFName, aLName, aEmail, aMobile, amountPaid);
            atts.add(a);
        }
        
        // return the list of Attendees object retrieved
        return atts;
    }
}
