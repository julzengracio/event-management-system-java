package EventManagementCompany;

import java.util.ArrayList;
import java.util.Scanner;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures 
 */

public class Event {    
    
    private int eventID;
    private String eventTitle;
    private String eventDescription;
    private String startDate;
    private String endDate;
    private double eventCost;
    private int locationID;

    public Event() {
        eventTitle = "18th Birthday";
        eventDescription = "18th Birthday";
        startDate = "10-10-2010";
        endDate = "10-10-2010";
        eventCost = 749.99;
    }
    
    public Event(int eID, String title, String description, String start, String end, double cost, int locID) {        
        this.eventID = eID;
        this.eventTitle = title;
        this.eventDescription = description;
        this.startDate = start;
        this.endDate = end;
        this.eventCost = cost;
        this.locationID = locID;
    }
    
    public Event(String title, String description, String start, String end, double cost, int locID) {
        this(-1, title, description, start, end, cost, locID);
    }
    
    public int getEventID() {
        return eventID;
    }
    
    public void setEventID(int id){
        this.eventID = id;
    }
    
    public String getEventName() {
        return eventTitle;
    }
    
    public void setEventName(String newEventName) {
        this.eventTitle = newEventName;
    }
    
    public String getEventDescription() {
        return eventDescription;
    }
    
    public void setEventDescription(String newEventDescription) {
        this.eventDescription = newEventDescription;
    }
    
    public String getStartDate() {
        return startDate;
    }
            
    public void setStartDate(String newStartDate) {
        this.startDate = newStartDate;
    }
    
    public String getEndDate() {
        return endDate;
    }
    
    public void setEndDate(String newEndDate) {
        this.endDate = newEndDate;
    }
    
    public double getEventCost() {
        return eventCost;
    }
    
    public void setEventCost(double newEventCost) {
        this.eventCost = newEventCost;
    } 
    
    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int newlocationID) {
        this.locationID = locationID;
    }
    
}
