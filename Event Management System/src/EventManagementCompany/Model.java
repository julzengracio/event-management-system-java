package EventManagementCompany;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.List;
//import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

public class Model {
    Scanner input = new Scanner(System.in);
    
    private static Model instance = null;
    
    public static Model connect(){ //database connection.
        if (instance == null) {
            instance = new Model();
        }
        return instance;
    }
      
    ArrayList<Event> eventList;
    ArrayList<NewEventLocation> locationList;
    ArrayList<Attendee> attendeeList;
    ArrayList<ManagementStaff> managerList;
    EventTableGateway eventGateway;
    LocationGateway eLocGateway;
    AttendeeGateway attendeeGateway;
    ManagerGateway managerGateway;
    
    public Model() {
        try { 
            Connection connect = DBConnect.connect();
            this.eventGateway = new EventTableGateway(connect);
            this.eLocGateway = new LocationGateway(connect);
            this.attendeeGateway = new AttendeeGateway(connect);
            this.managerGateway = new ManagerGateway(connect);
            
            this.eventList = (ArrayList<Event>) this.eventGateway.getEvents();
            this.locationList = (ArrayList<NewEventLocation>) this.eLocGateway.getEventLocations();
            this.attendeeList = (ArrayList<Attendee>) this.attendeeGateway.getAttendees();
            this.managerList = (ArrayList<ManagementStaff>) this.managerGateway.getManagers();
        } 
        
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }   
    
    public boolean addEvents(Event e) {             
        boolean result = false;
        try { 
            int id = this.eventGateway.insertEvent(e.getEventName(), 
                    e.getEventDescription(), e.getStartDate(),
                    e.getEndDate(), e.getEventCost(), e.getLocationID()
            );
            if (id != -1) {
                e.setEventID(id);
                this.eventList.add(e);
                result = true;
            }
        }                
        catch (SQLException ex) {
            //uncomment code below to see full error details
            //Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("*Location/location ID may not have been created yet");
        }        
        return result;
    }
    
    public boolean removeEvents(Event e) {
        boolean removed = false;

        try {
            removed = this.eventGateway.deleteEvents(e.getEventID());
            if (removed) {
                removed = this.eventList.remove(e);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return removed;
    }
    
    Event findEventByID(int eventIDs) {
        Event e = null;
        int i = 0;
        boolean found = false;
        while (i < this.eventList.size() && !found) {
            e = this.eventList.get(i);
            if (e.getEventID() == eventIDs) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            e = null;
        }
        return e;
    }
    
    boolean updateEventDetails(Event e) {
        boolean updated = false;

        try {
            updated = this.eventGateway.updateEvents(e);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
    
    public List<Event> getEventList() {
        return this.eventList;
    }
    
    public boolean addLocations(NewEventLocation eLoc) {
        boolean result = false;
        try {
            int id = this.eLocGateway.insertLocation(eLoc.getName(), eLoc.getAddress(), 
                   eLoc.getMgr().getFname(), eLoc.getMgr().getLname(), eLoc.getMgr().getEmail(), eLoc.getMgr().getNumber(), eLoc.getMaxCapacity()
            );
            if (id != -1) {
                eLoc.setLocationID(id);
                this.locationList.add(eLoc);
                result = true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean removeLocation(NewEventLocation eLoc) {
        boolean removed = false;

        try {
            removed = this.eLocGateway.deleteLocations(eLoc.getLocationID());
            if (removed) {
                removed = this.locationList.remove(eLoc);
            }
        }
        catch (SQLException ex) {
            //Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Can't Delete location because of relationship with an event.");
        }

        return removed;
    }
    
    NewEventLocation findLocationByID(int locationIDs) {
        NewEventLocation eLoc = null;
        int i = 0;
        boolean found = false;
        while (i < this.locationList.size() && !found) {
            eLoc = this.locationList.get(i);
            if (eLoc.getLocationID() == locationIDs) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            eLoc = null;
        }
        return eLoc;
    }
    
    boolean updateLocationDetails(NewEventLocation eLoc) {
        boolean updated = false;

        try {
            updated = this.eLocGateway.updateLocations(eLoc);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
    
    public List<NewEventLocation> getLocationList() {
        return this.locationList;
    }
    
    public boolean addAttendees(Attendee a) {
        boolean result = false;
        try {
            int id = this.attendeeGateway.insertAttendee(a.getFname(), a.getLname(), 
                    a.getEmail(), a.getNumber(), a.getAmountPaid()
            );
            if (id != -1){
                a.setaID(id);
                this.attendeeList.add(a);
                result = true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public boolean removeAttendees(Attendee a) {
        boolean removed = false;

        try {
            removed = this.attendeeGateway.deleteAttendee(a.getaID());
            if (removed) {
                removed = this.attendeeList.remove(a);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return removed;
    }
    
    Attendee findAttendeeByID(int attendeeIDs) {
        Attendee a = null;
        int i = 0;
        boolean found = false;
        while (i < this.attendeeList.size() && !found) {
            a = this.attendeeList.get(i);
            if (a.getaID() == attendeeIDs) {
                found = true;
            } else {
                i++;
            }
        }
        if (!found) {
            a = null;
        }
        return a;
    }
    
    boolean updateAttendeeDetails(Attendee a) {
        boolean updated = false;

        try {
            updated = this.attendeeGateway.updateAttendee(a);
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }

        return updated;
    }
    
    public List<Attendee> getAttendeeList() {
        return this.attendeeList;
    }

    //sorts the attendee arraylist.
    public List<Attendee> getSortAttendeeList() {
        List<Attendee> sortedList = this.attendeeList;
        Collections.sort(sortedList);
        return sortedList;
    }
    
    public boolean addManagers(ManagementStaff m) {
       boolean result = false;
        try {
            int id = this.managerGateway.insertManager(m.getFname(), m.getLname(), 
                    m.getEmail(), m.getNumber(), m.getSalary()
            );
            if (id != -1){
                m.setmID(id);
                this.managerList.add(m);
                result = true;
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public List<ManagementStaff> getManagerList() {
        return this.managerList;
    }
    
    /*
    public List<ManagementStaff> getManagerList(){
        List result = getLocationList();
        return result;
    }
    */
}