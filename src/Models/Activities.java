package Models;

import java.sql.Time;

/**
 * Created by x on 13-5-2016.
 */
public class Activities
{

    private int id;
    private String name;
    private int minAge;
    private Time startTime;
    private Time endTime;
    private String description;

    public Activities(int id, String name, int minAge, Time startTime, Time endTime, String description) {
        this.id = id;
        this.name = name;
        this.minAge = minAge;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMinAge() {
        return minAge;
    }

    public void setMinAge(int minAge) {
        this.minAge = minAge;
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

    public String toString()
    {
        return name;
    }


}
