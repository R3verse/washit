package Models;

/**
 * Gruppeaflevering: Max, Memet & Thomas - Washa
 */
public class User {

    private int ID;
    private String firstName;
    private String lastName;
    private String eMail;

    private String password;
    private String address;
    private int telephoneNumber;

    private String subscriptionType;
    private UserRoleEnum role;

    // ID, fName, lName, eMail, password, addr, subscription, tlfPhNr, role
    public User(int ID, String firstName, String lastName, String eMail, String password, String address, String subscriptionType, int telephoneNumber, UserRoleEnum role)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.address = address;
        this.ID = ID;
        this.telephoneNumber = telephoneNumber;
        this.subscriptionType = subscriptionType;
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEMail() {
        return eMail;
    }

    public String getAddress() {
        return address;
    }

    public int getID() {
        return ID;
    }

    public int getTelephoneNumber() {
        return telephoneNumber;
    }

    public UserRoleEnum getRole() {
        return role;
    }

    public String getUserName(){
        return firstName + " " + lastName;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void seteMail(String eMail){
        this.eMail = eMail;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setTelephoneNumber(int telephoneNumber){
        this.telephoneNumber = telephoneNumber;
    }

    public void setRole(UserRoleEnum role){
        this.role = role;
    }

    @Override
    public String toString()
    {
        //Used in UI Combobox for booking
        return firstName + " " + lastName;
    }

}
