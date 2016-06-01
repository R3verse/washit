package Models;

import java.sql.Date;
import java.sql.Time;

public class Booking{
    private int ID;
    private int userID;
    private String userName;

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

    public Booking(int ID, String status, String description, String userName, Date date, String address){}
    public Booking(int ID, int activityID, int userID, String activityName, String userName, int participants, Date date)
    {
        this.ID = ID;
        this.userID = userID;
        this.userName = userName;
        this.date = date;
    }

    public Booking(int ID, String status, String description, String userName, Date date)
    {
        this.ID = ID;
        this.date = date;

    }
    //Overload
    public Booking(int userID, Date date)
    {
        this.userID = userID;
        this.date = date;

    }
    //Overload
    public Booking(int ID, String userName, Date date)
    {
        this.ID = ID;
        this.userName = userName;
        this.date = date;
    }

    //Overload
    public Booking(int ID, int userId, String status, String activityName, String userName, Date date, Time startTime, Time endTime)
    {
        this.userName = userName;
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
