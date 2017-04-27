package EventManagementCompany;

import java.util.List;

/**
 *
 * @author n00145647
 */

//super class
public class Personnel implements EventManagementInterface {
    private String fname;
    private String lname;
    private String email;
    private int number;
    
    public Personnel(String fn, String ln, String e, int num){
        fname = fn;
        lname = ln;
        email = e;
        number = num;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
    
    @Override
    public String display() {
        return ("First Name: " + getFname() + 
                "\nLast Name: " + getLname() +
                "\nEmail Address: " + getEmail() +
                "\nMobile Number: " + getNumber());
    }
   
}
