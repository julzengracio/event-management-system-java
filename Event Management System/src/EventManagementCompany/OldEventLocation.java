package EventManagementCompany;

import java.util.Scanner;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

//Personnel.java subclass
public class OldEventLocation extends Personnel {    
    
    private int locationID;
    private String name;
    private String address;
    private int maxCapacity;
    private Personnel mgr;
    
    public OldEventLocation(int id, String n, String a,String fn, String ln, String e, int num, int maxCap) {
        super(fn, ln, e, num);
        this.locationID = id;
        this.name = n;
        this.address = a;
        this.maxCapacity = maxCap;
        //mgr = new Personnel (fn, ln, e, num);
    }
    
    public OldEventLocation(String n, String a,String fn, String ln, String e, int num, int maxCap) {
        this(-1, n, a, fn, ln, e, num, maxCap);
    }

    public int getLocationID() {
        return locationID;
    }

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }
    
    
}
