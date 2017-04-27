package EventManagementCompany;

/**
 *
 * @author n00145647
 */

//Personnel sub class.
public class ManagementStaff extends Personnel implements EventManagementInterface {
    
    private int mID;
    private double salary;
    
    public ManagementStaff(int id, String fn, String ln, String e, int num, double s) {
        super(fn, ln, e, num);
        salary = s;
    }

    public ManagementStaff(String fn, String ln, String e, int num, double s) {
        this (-1, fn, ln, e, num, s);
    }

    public int getmID() {
        return mID;
    }

    public void setmID(int mID) {
        this.mID = mID;
    }
    
    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
    
    public String display() {
        return (super.display() +
                "\nSalary: " + salary);
    }
    
}
