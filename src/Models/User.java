package Models;

import java.util.Date;

/**
 * Created by lordni on 2/22/16.
 */
public class User {

    private int ID;
    private String firstName;
    private String lastName;
    private String eMail;
    private String address;
    private int telephoneNumber;
    private Date birthday;
    private UserRoleEnum role;

    public User(String firstName, String lastName, String eMail, String address, int ID, int telephoneNumber,UserRoleEnum role, Date birthday)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.address = address;
        this.ID = ID;
        this.telephoneNumber = telephoneNumber;
        this.role = role;
        this.birthday = birthday;
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

    public Date getBirthday() {
        return birthday;
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

    public void setBirthday(Date birthday){
        this.birthday = birthday;
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
