package Models;

import java.sql.Date;
import java.sql.Time;

public class Booking{
    private int ID;
    private int userID;
    private String userName;
    private Time time;

    public Booking(int ID, int userID, String status, String description, Date date, String address, String userName, Time time) {

        this.ID = ID;
        this.userID = userID;
        this.status = status;
        this.description = description;
        this.date = date;
        this.address = address;
        this.userName = userName;
        this.time = time;
    }

    public Time getTime(){return time;}

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String description;
    private String status;
    private String address;
    private Date date;

    public Booking(int ID, String status, String description, Date date, String address){}
    public Booking(int ID, int userID, String userName, Date date, Time time)
    {
        this.ID = ID;
        this.userID = userID;
        this.userName = userName;
        this.date = date;
        this.time = time;
    }

    public Booking(int ID, Date date, Time time)
    {
        this.ID = ID;
        this.status = status;
        this.description = description;
        this.date = date;
        this.address = address;
        this.userName = userName;
        this.time = time;

    }

    //Overload
    public Booking(int id, int ID, String status, String description, Date date, String address)
    {
        this.ID = ID;
        this.userName = this.userName;
        this.date = date;
    }

    //Overload
    public Booking(int ID, String status, Date date, String address)
    {
        this.address = address;
        this.date = date;
    }

    public String getUserName() {
        return this.userName;
    }

    public int getID(){
        return ID;
    }

    public void setID(int ID){
        this.ID = ID;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
