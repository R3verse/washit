package Models;

import java.sql.Date;
import java.sql.Time;

/**
 * Gruppeaflevering: Max, Memet & Thomas
 */
public class Activities
{

    private String status;
    private String description;
    private Date date;

    private int ID;
    private String firstName;
    private String lastName;
    private String user;
    private String address;
    private Time time;


    // Status,description,date,user,address,time
    public Activities(int ID, String status, String description, Date date, String firstName, String lastName, String address, Time time) {
        this.ID = ID;
        this.status = status;
        this.description = description;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.time = time;
    }

    public Activities(int id, String status, String description, Date date, String user, String address, Time time){
        this.ID = id;
        this.status = status;
        this.description = description;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user += firstName + " " + lastName;
        this.user = user;

        this.address = address;
        this.time = time;

    }

    public Activities(String status, String description, Date date, String user, String address, Time time) {
        this.status = status;
        this.description = description;
        this.date = date;
        this.firstName = firstName;
        this.lastName = lastName;
        this.user += firstName + " " + lastName;
        this.user = user;

        this.address = address;
        this.time = time;

    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }


}
