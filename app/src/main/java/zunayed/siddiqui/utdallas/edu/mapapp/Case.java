package zunayed.siddiqui.utdallas.edu.mapapp;

/***********************************************************************************
 * Case Class: This class models the individual cases by district and dhaka zones. *
 *             An object of this class represents a row in the table of cases.     *
 *                                                                                 *
 * @author Ihfaz Tajwar                                                            *
 ***********************************************************************************/
public class Case {

    private String location;    // Name of location (Districts/Dhaka Zones)
    private int nCase;          // Number of cases

    // Constructors
    public Case(String location, int nCase) {
        this.location = location;
        this.nCase = nCase;
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getnCase() {
        return nCase;
    }

    public void setnCase(int nCase) {
        this.nCase = nCase;
    }
}
