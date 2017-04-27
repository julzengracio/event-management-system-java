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

public class EventTableGateway {
    private static final String EVENTS_TABLE = "events";
    private static final String EVENT_ID = "eventID";
    private static final String EVENT_TITLE = "Title";
    private static final String EVENT_DESCRIPTION = "Description";
    private static final String EVENT_START_DATE = "StartDate";
    private static final String EVENT_END_DATE = "EndDate";
    private static final String EVENT_COST = "Cost";
    private static final String LOCATION_ID = "locationID";

    private Connection mConnection;

    public EventTableGateway(Connection connect) {
        mConnection = connect;
    }

    public int insertEvent(String title, String description, String startDate, String endDate, double cost, int locID) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int id = -1;
        
        
        // INSERT sql statement that looks for the table name and get the users' input for the values.  
        query = "INSERT INTO " + EVENTS_TABLE + " (" +
                EVENT_TITLE + ", " +
                EVENT_DESCRIPTION + ", " +
                EVENT_START_DATE + ", " +
                EVENT_END_DATE + ", " +
                EVENT_COST + ", " +
                LOCATION_ID +
                ") VALUES (?, ?, ?, ?, ?, ?)";

        // prepared statement object that executes the query. Insert values to the query
        stmt = mConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, title);
        stmt.setString(2, description);
        stmt.setString(3, startDate);
        stmt.setString(4, endDate);
        stmt.setDouble(5, cost);
        if (locID == -1) {
            stmt.setNull(6, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(6, locID);
        }

        // executes the query and make sure only 1 row will be inserted into the database.
        numRowsAffected = stmt.executeUpdate();
        if (numRowsAffected == 1) {
            // retrieve the id if a row is inserted.
            ResultSet keys = stmt.getGeneratedKeys();
            keys.next();

            id = keys.getInt(1);
        }
        // return the object created, or null if a problem occured.
        return id;
    }
    
    public boolean deleteEvents(int id) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;

        // the delete sql statement: which refers to the ID of the data to be deleted
        query = "DELETE FROM " + EVENTS_TABLE + " WHERE " + EVENT_ID + " = ?";

        // prepared statement object that executes the query. Insert the ID to the query
        stmt = mConnection.prepareStatement(query);
        stmt.setInt(1, id);

        // execute query
        numRowsAffected = stmt.executeUpdate();

        return (numRowsAffected == 1);
    }
    
    public boolean updateEvents(Event e) throws SQLException {
        String query;                   // the SQL query to execute
        PreparedStatement stmt;         // the java.sql.PreparedStatement object used to execute the SQL query
        int numRowsAffected;
        int locID;

        // UPDATE sql statement that looks for the table name and get the users' input for new values.
        query = "UPDATE " + EVENTS_TABLE + " SET " +
                EVENT_TITLE             + " = ?, " +
                EVENT_DESCRIPTION       + " = ?, " +
                EVENT_START_DATE        + " = ?, " +
                EVENT_END_DATE          + " = ?, " +
                EVENT_COST              + " = ?, " +
                LOCATION_ID             + " = ? " +
                " WHERE " + EVENT_ID + " = ?";

        // prepared statement object that executes the query. Takes the new values that the user input.
        stmt = mConnection.prepareStatement(query);
        stmt.setString(1, e.getEventName());
        stmt.setString(2, e.getEventDescription());
        stmt.setString(3, e.getStartDate());
        stmt.setString(4, e.getEndDate());
        stmt.setDouble(5, e.getEventCost());
        stmt.setInt(6, e.getEventID());
        locID = e.getLocationID();
        if (locID == -1) {
            stmt.setNull(7, java.sql.Types.INTEGER);
        }
        else {
            stmt.setInt(7, locID);
        }
        
        
        // execute the query
        numRowsAffected = stmt.executeUpdate();
        
        return (numRowsAffected == 1);
    }

    public List<Event> getEvents() throws SQLException {
        String query;                   // the SQL query to execute
        Statement stmt;                 // the java.sql.Statement object used to execute the SQL query
        ResultSet rs;                   // the java.sql.ResultSet representing the result of SQL query
        List<Event> events;            // the list containing the event object created for each row
                                        

        String eventTitle, eventDescription, startDate, endDate;
        double cost;
        int eventID, locID;

        Event e;                       // an Event object created from a row in the result of the query

        // execute SELECT sql statement to get the result set.
        query = "SELECT * FROM " + EVENTS_TABLE;
        stmt = this.mConnection.createStatement();
        rs = stmt.executeQuery(query);

        /*
        * Extracts the data from each row and sort it in a Event object
        * which is inserted into an initially empty array list.
        */
        events = new ArrayList<Event>();
        while (rs.next()) {
            eventID = rs.getInt(EVENT_ID);
            eventTitle = rs.getString(EVENT_TITLE);
            eventDescription = rs.getString(EVENT_DESCRIPTION);
            startDate = rs.getString(EVENT_START_DATE);
            endDate = rs.getString(EVENT_END_DATE);
            cost = rs.getDouble(EVENT_COST);
            locID = rs.getInt(LOCATION_ID);
            if (rs.wasNull()) {
                locID = -1;
            }
            
            e = new Event(eventID, eventTitle, eventDescription, startDate, endDate, cost, locID);
            events.add(e);
        }
        
        // return the list of Events objects retrieved
        return events;
    }
}
