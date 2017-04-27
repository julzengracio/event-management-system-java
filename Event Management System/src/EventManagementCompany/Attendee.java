package EventManagementCompany;

import java.util.Scanner;
import java.util.*;

/*
 * @author n00145647
 * Jullian Engracio
 * Start Date: 19-10-2015
 * Algorithms & Data Structures * 
 */

//Personnel subclass
public class Attendee extends Personnel implements Comparable<Attendee>, EventManagementInterface {    
    Scanner input = new Scanner(System.in);
    
    private int aID;
    private double amountPaid;
    
    public Attendee(int id, String fn, String ln, String e, int num, double pay) {
        super(fn, ln, e, num);
        this.aID = id;
        this.amountPaid = pay;
        
    }
    
    public Attendee(String fname, String lname, String email, int num, double pay){
        this(-1, fname, lname, email, num, pay);
    }

    public int getaID() {
        return aID;
    }

    public void setaID(int aID) {
        this.aID = aID;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    
    @Override
    public String display() {
        return (super.display() +
                "\nAmount paid: " + amountPaid);
    }

    @Override
    public int compareTo(Attendee a) {
        return a.getLname().compareTo(this.getLname());//descending order
        
        
        //int compareID = ((Attendee)a).getaID();
        //For Ascending order
        //return this.aID-compareID;
        
        //For Descending order do like this
        //return compareID - this.aID();
    }
    
    
    
}
