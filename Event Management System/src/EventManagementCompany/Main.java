package EventManagementCompany;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

public class Main {      
       
    private static Model model = new Model();//creates a new model for data to be passed and stored to an array list then to the database. 
    private static DBConnect closeConnection = new DBConnect();//creates the database connection.
    
    public static void main(String[] args) throws SQLException, FileNotFoundException {       
        menu();
    }
    
    /*returns a menu to the user to allow him to navigate with the program.
    * connects the model object to the database.
    */
    public static void menu() throws SQLException, FileNotFoundException {
        Scanner input = new Scanner(System.in); 
        
        model = Model.connect();
        
        String choice;
        String choice2;
        /* Case statements for the user to allow them to navigate within the program.
        *  After completing a program run the user will then be able to choose whether to exit the program or go back to the menu.
        */
        System.out.println("Choose an option:" +
                           "\nEnter [ 1 ] for Create options" +
                           "\nEnter [ 2 ] for Delete options" +
                           "\nEnter [ 3 ] for View options" +
                           "\nEnter [ 4 ] for Update options" +
                           "\nEnter [ 5 ] to Generate details from a file" +
                           "\nEnter [ 0 ] to EXIT program");
        choice = input.next();  
        switch (choice.toUpperCase()) {
            
            case "1":
                System.out.println("Choose a sub-option:" + 
                                   "\nEnter [ 1 ] to Create an Event" +
                                   "\nEnter [ 2 ] to Create a Location" +
                                   "\nEnter [ 3 ] to Create an Attendee" +
                                   "\nEnter [ 4 ] to Create a Manager");
                choice2 = input.next();
                switch (choice2.toUpperCase()) {
                    case "1":
                        System.out.println("Creating an event");
                        readEvents(model);
                        options();
                        break;
                    case "2":
                        System.out.println("Creating a location");
                        readLocations(model);
                        options();
                        break;
                    case "3":
                        System.out.println("Creating an attendee");
                        readAttendees(model);
                        options();
                        break;
                    case "4":
                        System.out.println("Creating a Manager");
                        readManagers(model);
                        options();
                        break;
                }
            case "2":
                System.out.println("Choose a sub-option:" + 
                                   "\nEnter [ 1 ] to Delete an Event" +
                                   "\nEnter [ 2 ] to Delete a Location" +
                                   "\nEnter [ 3 ] to Delete an Attendee");
                choice2 = input.next();
                switch (choice2.toUpperCase()) {
                    case "1":
                        deleteEvent(model);
                        options();
                        break;
                    case "2":
                        deleteLocations(model);
                        options();
                        break;
                    case "3":
                        deleteAttendees(model);
                        options();
                        break;
                }
            case "3":
                System.out.println("Choose a sub-option:" + 
                                   "\nEnter [ 1 ] to View Events" +
                                   "\nEnter [ 2 ] to View Locations" +
                                   "\nEnter [ 3 ] to View Attendees" +
                                   "\nEnter [ 4 ] to View Managers" +
                                   "\nEnter [ 5 ] to View Managers OR Attendees");
                choice2 = input.next();
                switch (choice2.toUpperCase()) {
                    case "1":
                        viewEvents(model);
                        options();
                        break;
                    case "2":
                        viewLocations(model);
                        options();
                        break;
                    case "3":
                        viewAttendees(model);
                        optionsWithSort();
                        break;
                    case "4":
                        viewManagers(model);
                        options();
                        break;
                    case "5":
                        display(model);
                        options();
                        break;
                }
            case "4":
                System.out.println("Choose a sub-option:" + 
                                   "\nEnter [ 1 ] to Update an Event" +
                                   "\nEnter [ 2 ] to Update a Location" +
                                   "\nEnter [ 3 ] to Update an Attendee");
                choice2 = input.next();
                switch (choice2.toUpperCase()) {
                    case "1":
                        updateEvents(model);
                        options();
                        break;
                    case "2":
                        updateLocation(model);
                        options();
                        break;
                    case "3":
                        updateAttendee(model);
                        options();
                        break;
                }
            case "5":
                readFile();
                options();
                break;
            case "0":
                System.out.println("End of program: DB Connection closed");
                closeConn();
                System.exit(0);
                break;
        }
    }
    
    //returns a menu to the user after a certain action through the program to allow them to either go back to the menu or end the program.
    public static void options() throws SQLException, FileNotFoundException {
        Scanner input = new Scanner(System.in);
        
        System.out.println(" " +
                           "\nChoose an option: " +
                           "\nEnter [ 1 ] to go back to menu" +
                           "\nEnter [ 2 ] to exit program");
        
        String choice;
        choice = input.next();
        
        switch(choice) {
            case "1":
                menu();
                break;
            case "2":
                System.out.println("End of program: DB Connection closed");
                closeConn();
                System.exit(0);
                break;
        }
    }

    public static void optionsWithSort() throws SQLException, FileNotFoundException {
        Scanner input = new Scanner(System.in);
        
        System.out.println(" " +
                           "\nChoose an option: " +
                           "\nEnter 1 to sort by Ascending order" +
                           "\nEnter 2 to sort by Descending order" +
                           "\nEnter 3 to go back to menu" +
                           "\nEnter 4 to exit program");
        
        String choice;
        choice = input.next();
        
        switch(choice) {
            case "1":
                viewAttendees(model);
                optionsWithSort();
                break;
            case "2":
                viewSortedAttendees(model);
                optionsWithSort();
                break;
            case "3":
                menu();
                break;            
            case "4":
                System.out.println("End of program: DB Connection closed");
                closeConn();
                System.exit(0);
                break;
        }
    }

    //reads in a file for input. User will be ask to enter the name of the file, which should be located in the projects' root folder.
    public static void readFile() throws FileNotFoundException, SQLException  {
        Scanner input = new Scanner(System.in);
        System.out.println("Input File: ");
        String inputFileName = input.next();
        
        File inputFile = new File(inputFileName);//name of the input file.
        Scanner keyboard = new Scanner(inputFile);//object that will hold the details from the input file.
        
        while (keyboard.hasNextLine()){
            String line = keyboard.nextLine();
            if (line.equalsIgnoreCase("A")){ //reads attendees details.
                readAttendeesFromFile(keyboard);
            }
            else if (line.equalsIgnoreCase("L")){ //reads location details.
                readLocationsFromFile(keyboard);
            }
        }
        keyboard.close();
    }
    
    //create an event after details are taken from the user.
    public static void createEvents(Event e) {
        if (model.addEvents(e)){
            System.out.println("Event added to the database.");
        }
        else {
            System.out.println("Event not added to the database.");
        }
    }
    
    //deletes an event, by ID reference from the database, chosen by the user
    public static void deleteEvent(Model eDelete) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of the event to delete: ");
        int eventIDs = input.nextInt();
        Event e;

        e = eDelete.findEventByID(eventIDs); //finds the ID entered by the user, stores the ID to an object
        if (e != null) {
            if (eDelete.removeEvents(e)) {
                System.out.println(" " +
                                   "Event deleted");
            }
            else {
                System.out.println(" " +
                                   "Event not deleted");
            }
        }
        else {
            System.out.println(" " +
                               "Event not found");
        }
    }
    
    //updates an event, by ID reference from the database, chosen by the user
    public static void updateEvents(Model eUpdate) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of the event to edit: ");
        int eventID = input.nextInt();
        Event e;

        e = eUpdate.findEventByID(eventID);
        if (e != null) {
            editEventsDetails(e);
            if (eUpdate.updateEventDetails(e)) {
                System.out.println(" " +
                                   "Event updated");
            }
            else {
                System.out.println(" " +
                                   "Event not updated");
            }
        }
        else {
            System.out.println(" " +
                               "Event not found");
        }
    } 
     
    //reads in data manually inserted from the user.
    public static void readEvents(Model m) {
        Scanner input = new Scanner(System.in);
        String eventName, eventDescription, startDate, endDate;
        double eventCost;
        int locID;
        
        System.out.println("Event Name:");
        eventName = input.nextLine();
        
        System.out.println("Event Description:");
        eventDescription = input.nextLine();
        
        System.out.println("Start Date:");
        startDate = input.nextLine();       
        
        System.out.println("End Date:");
        endDate = input.nextLine();   
        
        System.out.println("Event Cost:");
        eventCost = input.nextDouble();
        
        System.out.println("Enter Location ID:");
        locID = input.nextInt();
        
        Event e = new Event(eventName, eventDescription, startDate, endDate, eventCost, locID);
        createEvents(e);
    }
    
    //when udpating an event this method will be taking new details from the user.
    public static void editEventsDetails(Event e) {
        Scanner input = new Scanner(System.in);
        String eName, eDescription, startDate, endDate;
        double eCost;
        
        System.out.println("Enter new event name");
        eName = input.nextLine();
        
        System.out.println("Enter new description");
        eDescription = input.nextLine();
        
        System.out.println("Enter new start date");
        startDate = input.nextLine();
        
        System.out.println("Enter new end date");
        endDate = input.nextLine();
        
        System.out.println("Enter new cost");
        eCost = input.nextDouble();

        if (eName.length() != 0) {
            e.setEventName(eName);
        }
        if (eDescription.length() != 0) {
            e.setEventDescription(eDescription);
        }
        if (startDate.length() != 0) {
            e.setStartDate(startDate);
        }
        if (endDate.length() != 0) {
            e.setEndDate(endDate);
        }
        if (eCost != 0) {
            e.setEventCost(eCost);
        }
    }
    
    //view events from the database and output them in the console.
    public static void viewEvents(Model eView) {
        List<Event> eventList = eView.getEventList();
        System.out.println();
        if (eventList.isEmpty()) {
            System.out.println("There are no events in the database.");
        }
        else {
            System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                    "EventID", "Event Name", "Event Description", "Start Date", "End Date", "Event Cost"); //formats labels.
            for (Event i : eventList) {
                System.out.printf("%-10s %20s %27s %13s %18s %22.2f\n", //formats details.
                        i.getEventID(),
                        i.getEventName(),
                        i.getEventDescription(),
                        i.getStartDate(),
                        i.getEndDate(),
                        i.getEventCost());
            }
        }
        System.out.println();
    }
    
    //create a location after details are taken from the user.
    public static void createLocations(NewEventLocation eL) {
        if (model.addLocations(eL)){
            System.out.println("Location added to the database.");
        }
        else {
            System.out.println("Location not added to the database.");
        }
    }
    
    //deletes a location, by ID reference from the database, chosen by the user
    public static void deleteLocations(Model eLocDelete) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of the location to delete: ");
        int eLocIDs = input.nextInt();
        NewEventLocation eLoc;
        
        eLoc = eLocDelete.findLocationByID(eLocIDs); //finds the ID entered by the user, stores the ID to an object
        if (eLoc != null) {
            if (eLocDelete.removeLocation(eLoc)) {
                System.out.println("Location deleted");
            }        
            else {
                System.out.println("Location not deleted");
            }
        }
        else {
            System.out.println("Location not found");
        }
    }
    
    //updates a location, by ID reference from the database, chosen by the user
    public static void updateLocation(Model eLocUpdate) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of the location to edit: ");
        int eLocID = input.nextInt();
        NewEventLocation eLoc;

        eLoc = eLocUpdate.findLocationByID(eLocID);
        if (eLoc != null) {
            editLocationDetails(eLoc);
            if (eLocUpdate.updateLocationDetails(eLoc)) {
                System.out.println("Location updated");
            }
            else {
                System.out.println("Location not updated");
            }
        }
        else {
            System.out.println("Location not found");
        }
    } 
    
    //reads in data manually inserted from the user.
    public static void readLocations(Model m) {
        Scanner input = new Scanner(System.in);
        String n, a, mFName, mLName, mEmail;
        int cap, mNum;
        
        System.out.println("Location Name:");
        n = input.nextLine();
        
        System.out.println("Location Address:");
        a = input.nextLine();
        
        System.out.println("Manager First Name:");
        mFName = input.nextLine();   
        
        System.out.println("Manager Last Name:");
        mLName = input.nextLine();   
        
        System.out.println("Manager Email:");
        mEmail = input.nextLine();
        
        System.out.println("Manager Number:");
        mNum = input.nextInt();
        
        System.out.println("Location Capacity:");
        cap = input.nextInt();
        
        NewEventLocation eL = new NewEventLocation(n, a , mFName, mLName, mEmail, mNum, cap);
        createLocations(eL);
    }
    
    //reads is data from a file. (generates location details)
    public static void readLocationsFromFile(Scanner input) {
        String n, a, mFName, mLName, mEmail;
        int cap, mNum;
        
        n = input.nextLine();
        //System.out.println(n);
        
        a = input.nextLine();
        //System.out.println(a);

        mFName = input.nextLine();
        //System.out.println(mFName);
        
        mLName = input.nextLine();
        //System.out.println(mLName);
        
        mEmail = input.nextLine();
        //System.out.println(mEmail);
        
        mNum = input.nextInt();
        //System.out.println(mNum);
        
        cap = input.nextInt();
        //System.out.println(cap);
        
        NewEventLocation eLoc = new NewEventLocation(n, a , mFName, mLName, mEmail, mNum, cap);
        createLocations(eLoc);
    }
    
    //when udpating a location this method will be taking new details from the user.
    public static void editLocationDetails(NewEventLocation eLoc) {
        Scanner input = new Scanner(System.in);
        String n, a, mFName, mLName, mEmail;
        int mNum, cap;
        
        System.out.println("Enter new location name");
        n = input.nextLine();
        
        System.out.println("Enter new location address");
        a = input.nextLine();
        
        System.out.println("Enter new manager first name");
        mFName = input.nextLine();
        
        System.out.println("Enter new manager last name");
        mLName = input.nextLine();
        
        System.out.println("Enter new manager email");
        mEmail = input.nextLine();
        
        System.out.println("Enter new manager number");
        mNum = input.nextInt();
        
        System.out.println("Enter new max capacity");
        cap = input.nextInt();

        if (n.length() != 0) {
            eLoc.setName(n);
        }
        if (a.length() != 0) {
            eLoc.setAddress(a);
        }
        if (mFName.length() != 0) {
            eLoc.getMgr().setFname(mFName);
        }
        if (mLName.length() != 0) {
            eLoc.getMgr().setLname(mLName);
        }
        if (mEmail.length() != 0) {
            eLoc.getMgr().setEmail(mEmail);
        }
        if (mNum != 0) {
            eLoc.getMgr().setNumber(mNum);
        }
        if (cap != 0) {
            eLoc.setMaxCapacity(cap);
        }
    }
    
    //view location details from the database.
    public static void viewLocations(Model eLocView) throws FileNotFoundException {
        System.out.println("Enter [ 1 ] to print in console" +
                           "\nEnter [ 2 ] to print in an output file");
        Scanner input = new Scanner(System.in);
        String choice;
        choice = input.next();        
        List<NewEventLocation> locationList = eLocView.getLocationList();
        
        switch(choice) {
            case"1":
                System.out.println();
                if (locationList.isEmpty()) {
                    System.out.println("There are no locations in the database.");
                }
                else {
                    System.out.printf("%-26s %-21s %-20s %-24s %-30s %-24s %-22s %-20s\n",
                            "ID", "Name", "Address", "Manager First Name", "Manager Last Name", "Manager Email", "Manager Number", "Max Capacity"); //formats labels
                    for (NewEventLocation i : locationList) {
                        System.out.printf("%-10s %20s %24s %31s %23s %26s %25s %20s\n", //format details
                                i.getLocationID(),
                                i.getName(),
                                i.getAddress(),
                                i.getMgr().getFname(),
                                i.getMgr().getLname(),
                                i.getMgr().getEmail(),
                                i.getMgr().getNumber(),
                                i.getMaxCapacity());
                    }
                }
                System.out.println();
                break;
            case"2":
                System.out.println("Output File: ");
                String outputFileName = input.next(); //name for the output file.
                PrintWriter out = new PrintWriter(outputFileName); //the output file created from the user.
                
                if (locationList.isEmpty()) {
                    System.out.println("There are no locations in the database.");
                }
                else {
                    System.out.printf("%-26s %-21s %-20s %-24s %-30s %-24s %-22s %-20s\n",
                            "ID", "Name", "Address", "Manager First Name", "Manager Last Name", "Manager Email", "Manager Number", "Max Capacity"); //formats labels
                    
                    out.println();
                    out.println("---------------------------------------------------------------------------------------------");
                    
                    for (NewEventLocation i : locationList) {
                        System.out.printf("%-10s %20s %24s %31s %23s %26s %25s %20s\n", //format details
                                i.getLocationID(),
                                i.getName(),
                                i.getAddress(),
                                i.getMgr().getFname(),
                                i.getMgr().getLname(),
                                i.getMgr().getEmail(),
                                i.getMgr().getNumber(),
                                i.getMaxCapacity());
                        
                    out.println();
                    }
                }
                out.close();
                break;
        }
            
        
    }
    
    //create an attendee after details are taken from the user.
    public static void createAttendees(Attendee a) {
        if (model.addAttendees(a)){
            System.out.println("Attendee added to the database.");
        }
        else {
            System.out.println("Attendee not added to the database.");
        }
    }
    
    //deletes an attendee, by ID reference from the database, chosen by the user
    public static void deleteAttendees(Model aDelete) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of the attendee to delete: ");
        int attendeeID = input.nextInt();
        Attendee a;

        a = aDelete.findAttendeeByID(attendeeID); //finds the ID entered by the user, stores the ID to an object
        if (a != null) {
            if (aDelete.removeAttendees(a)) {
                System.out.println("Attendee deleted");
            }
            else {
                System.out.println("Attendee not deleted");
            }
        }
        else {
            System.out.println("Attendee not found");
        }
    }
    
    //updates an attendee, by ID reference from the database, chosen by the user
    public static void updateAttendee(Model aUpdate) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the ID of an attendee to edit: ");
        int attendeeID = input.nextInt();
        Attendee a;

        a = aUpdate.findAttendeeByID(attendeeID); //finds the ID entered by the user, stores the ID to an object
        if (a != null) {
            editAttendeeDetails(a);
            if (aUpdate.updateAttendeeDetails(a)) {
                System.out.println("Attendee updated");
            }
            else {
                System.out.println("Attendee not updated");
            }
        }
        else {
            System.out.println("Attendee not found");
        }
    }
    
    //reads is data from a file. (generates attendee details)
    public static void readAttendeesFromFile(Scanner input) {
        String aFName, aLName, aEmail;
        int aMobile;
        double aPay;
        
        aFName = input.nextLine();
        //System.out.println(aFName);

        aLName = input.nextLine();
        //System.out.println(aLName);
        
        aEmail = input.nextLine();     
        //System.out.println(aEmail);
        
        aMobile = input.nextInt();
        //System.out.println(aMobile);
        
        aPay = input.nextDouble();
        //System.out.println(aPay);
        
        Attendee a = new Attendee(aFName, aLName, aEmail, aMobile, aPay);
        createAttendees(a);
    }
    
    //reads in data manually inserted from the user.
    public static void readAttendees(Model m) {
        Scanner input = new Scanner(System.in);
        String aFName, aLName, aEmail;
        int aMobile;
        double aPay;
        
        System.out.println("First Name:");
        aFName = input.nextLine();

        System.out.println("Last Name:");
        aLName = input.nextLine();
        
        System.out.println("Email:");
        aEmail = input.nextLine();     
        
        System.out.println("Mobile:");
        aMobile = input.nextInt();
        
        System.out.println("Amount Paid:");
        aPay = input.nextDouble();
        
        Attendee a = new Attendee(aFName, aLName, aEmail, aMobile, aPay);
        createAttendees(a);
    }
    
    //when udpating an attendee this method will be taking new details from the user.
    public static void editAttendeeDetails(Attendee a) {
        Scanner input = new Scanner(System.in);
        String aFName, aLName, aEmail;
        int aMobile;
        double aAmount;
        
        System.out.println("Enter new first name");
        aFName = input.nextLine();
        
        System.out.println("Enter new last name");
        aLName = input.nextLine();
        
        System.out.println("Enter new email");
        aEmail = input.nextLine();
        
        System.out.println("Enter new mobile");
        aMobile = input.nextInt();
        
        System.out.println("Enter new amount paid");
        aAmount = input.nextDouble();

        if (aFName.length() != 0) {
            a.setFname(aFName);
        }
        if (aLName.length() != 0) {
            a.setLname(aLName);
        }
        if (aEmail.length() != 0) {
            a.setEmail(aEmail);
        }
        if (aMobile != 0) {
            a.setNumber(aMobile);
        }
        if (aMobile != 0) {
            a.setAmountPaid(aMobile);
        }
    }
    
    //view an unsorted list of attendee details from the database. User can choose to either print in the console or in an output file.
    public static void viewAttendees(Model aView) throws FileNotFoundException {
        System.out.println("Enter [ 1 ] to print in console" +
                           "\nEnter [ 2 ] to print in an output file");
        
        List<Attendee> attendeeList = aView.getAttendeeList();
        Scanner input = new Scanner(System.in);
        String choice;
        choice = input.next();
        
        switch(choice) {
            case "1"://prints in console.
                System.out.println();
                if (attendeeList.isEmpty()) {
                    System.out.println("There are no Attendees in the database.");
                }
                else {
                    System.out.printf("%-15s %-20s %-24s %-20s %-20s %-20s\n",
                            "ID", "First Name", "Last Name", "Email", "Mobile", "Amount Paid"); //formats labels
                    for (Attendee i : attendeeList) {
                        System.out.printf("%-10s %15s %19s %20s %21s %25s\n", //format details
                                i.getaID(),
                                i.getFname(),
                                i.getLname(),
                                i.getEmail(),
                                i.getNumber(),
                                i.getAmountPaid());
                    }
                }
                System.out.println();
                break;
            case "2"://prints in an output file.
                System.out.println("Output File: ");
                String outputFileName = input.next(); //name for the output file.
                PrintWriter out = new PrintWriter(outputFileName); //the output file created from the user.
                if (attendeeList.isEmpty()) {
                    out.println("There are no Attendees in the database.");
                }
                else { 
                    out.printf("%-15s %9s %24s %20s %21s\n",
                            "First Name", "Last Name", "Email", "Mobile", "Amount Paid"); //formats labels
                    
                    out.println();
                    out.println("---------------------------------------------------------------------------------------------");
                    
                    for (Attendee i : attendeeList) { 
                        out.printf("%10s %14s %24s %20s %21s\n", //formats details
                                i.getFname(),
                                i.getLname(),
                                i.getEmail(),
                                i.getNumber(),
                                i.getAmountPaid());
                        
                    out.println();
                    }
                }
                System.out.println("Output File generated. Check the root folder");
                out.close();//closes PrintWriter
                break;
        }
    }
    
    //view a sorted list of attendee details from the database. User can choose to either print in the console or in an output file.
    public static void viewSortedAttendees(Model aView) throws FileNotFoundException {
        System.out.println("Enter 1 to view in console" +
                           "\nEnter 2 to view in output file");
        
        List<Attendee> attendeeList = aView.getSortAttendeeList();
        Scanner input = new Scanner(System.in);
        String choice;
        choice = input.next();
        
        switch(choice) {
            case "1":
                System.out.println();
                if (attendeeList.isEmpty()) {
                    System.out.println("There are no Attendees in the database.");
                }
                else {
                    System.out.printf("%-15s %-20s %-24s %-20s %-20s %-20s\n",
                            "ID", "First Name", "Last Name", "Email", "Mobile", "Amount Paid"); //formats labels
                    for (Attendee i : attendeeList) {
                        System.out.printf("%-10s %15s %19s %20s %21s %25s\n", //format details
                                i.getaID(),
                                i.getFname(),
                                i.getLname(),
                                i.getEmail(),
                                i.getNumber(),
                                i.getAmountPaid());
                    }
                }
                System.out.println();
                break;
            case "2":
                System.out.println("Output File: ");
                String outputFileName = input.next(); //name for the output file.
                PrintWriter out = new PrintWriter(outputFileName); //the output file created from the user.
                if (attendeeList.isEmpty()) {
                    out.println("There are no Attendees in the database.");
                }
                else { 
                    out.printf("%-15s %9s %24s %20s %21s\n",
                            "First Name", "Last Name", "Email", "Mobile", "Amount Paid"); //formats labels
                    
                    out.println();
                    out.println("---------------------------------------------------------------------------------------------");
                    
                    for (Attendee i : attendeeList) { 
                        out.printf("%10s %14s %24s %20s %21s\n", //formats details
                                i.getFname(),
                                i.getLname(),
                                i.getEmail(),
                                i.getNumber(),
                                i.getAmountPaid());
                        
                    out.println();
                    }
                }
                out.close();//closes PrintWriter
                break;
                
                
                /*
                System.out.println("Input File: ");
                String inputFileName = input.next();
                System.out.println("Output File: ");
                String outputFileName = input.next();

                File inputFile = new File(inputFileName);
                Scanner keyboard = new Scanner(inputFile);
                PrintWriter out = new PrintWriter(outputFileName);
                //int lineNumber = 1;

                while (keyboard.hasNextLine()){
                    String line = keyboard.nextLine();
                    if (line.equalsIgnoreCase("A")){
                        out.println("List of attendees:" +
                                    "\n" + line);
                    }
                    else{
                        
                    }
                }

                keyboard.close();
                out.close();
                */
                
        }
    }
    
    //displays either the list of attendees or list of managers. Used for polymorphism exercise. Calls the method display() from Personnel class.
    public static void display(Model aList) {
        List<Attendee> attendeeList = aList.getAttendeeList();
        List<ManagementStaff> managerList = aList.getManagerList();
        Scanner input = new Scanner(System.in);
        
        System.out.println("Enter 1 to display Attendees" +
                           "\nEnter 2 to display Managers");
        
        String choice;
        choice = input.next();
        
        switch (choice) {
            case "1"://displays attendee details only
                System.out.println();
                if (attendeeList.isEmpty()) {
                    System.out.println("There are no Attendees in the database.");
                }
                else {
                    System.out.println("Personnel Data: ");
                    for (Personnel i : attendeeList) {
                        System.out.println(i.display());
                    }
                }
                System.out.println();
                break;
            case "2"://displays the Location Manager details only from the NewEventLocation class.
                System.out.println();
                if (managerList.isEmpty()) {
                    System.out.println("There are no Managers in the database.");
                }
                else {
                    System.out.println("Personnel Data: ");
                    for (Personnel i : managerList) {
                        System.out.println(i.display());
                    }
                }
                System.out.println();
                break;
        }     
    }
    
    //create a manager after details are taken from the user.
    public static void createManagers(ManagementStaff m) {
        if (model.addManagers(m)){
            System.out.println("Manager added to the database.");
        }
        else {
            System.out.println("Manager not added to the database.");
        }
    }
    
    //reads in data manually inserted from the user.
    public static void readManagers(Model mgr) {
        Scanner input = new Scanner(System.in);
        String fName, lName, em;
        int num;
        double sal;
        
        System.out.println("First Name:");
        fName = input.nextLine();

        System.out.println("Last Name:");
        lName = input.nextLine();
        
        System.out.println("Email:");
        em = input.nextLine();     
        
        System.out.println("Mobile:");
        num = input.nextInt();
        
        System.out.println("Salary:");
        sal = input.nextDouble();
        
        ManagementStaff m = new ManagementStaff(fName, lName, em, num, sal);
        createManagers(m);
    }
    
    //view location details from the database.
    public static void viewManagers(Model mView) {
        List<ManagementStaff> managerList = mView.getManagerList();
        System.out.println();
        
        for (ManagementStaff i : managerList) {
            System.out.println(i.display());
        }
    }
    
    //closes database connections.
    public static void closeConn() throws SQLException{
        closeConnection.sConnect.close();
    }
    
}
