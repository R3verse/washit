package Models;

import java.sql.Date;
import java.sql.Time;

public class Booking{
    private int ID;
    private int activityID;
    private int userID;
    private String activityName;
    private String userName;
    private int participants;
    private Date date;
    private Time startTime;
    private Time endTime;

    public Booking(int ID, String status, String description, String userName, Date date){}
    public Booking(int ID, int activityID, int userID, String activityName, String userName, int participants, Date date, Time startTime, Time endTime)
    {
        this.ID = ID;
        this.activityID = activityID;
        this.userID = userID;
        this.activityName = activityName;
        this.userName = userName;
        this.participants = participants;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Booking(int ID, String status, String description, String userName, Date date, Time startTime, Time endTime)
    {
        this.ID = ID;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    //Overload
    public Booking(int activityID, int userID, Date date)
    {
        this.activityID = activityID;
        this.userID = userID;
        this.date = date;

    }
    //Overload
    public Booking(int ID, String activityName, String userName, Date date, Time startTime, Time endTime)
    {
        this.ID = ID;
        this.activityName = activityName;
        this.userName = userName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    //Overload
    public Booking(int ID, int userId, String status, String activityName, String userName, Date date, Time startTime, Time endTime)
    {
        this.activityName = activityName;
        this.userName = userName;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getActivityName() {
        return this.activityName;
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

    public int getActivityID(){
        return activityID;
    }

    public void setActivityID(int activityID){
        this.activityID = activityID;
    }

    public int getUserID(){
        return userID;
    }

    public void setUserID(int userID){
        this.userID = userID;
    }

    public int getParticipants(){
        return participants;
    }

    public void setParticipants(int participants){
        this.participants = participants;
    }

    public Date getDate(){
        return date;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
