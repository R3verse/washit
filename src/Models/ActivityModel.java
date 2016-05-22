package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

/**
 * Created by AlexanderFalk on 22/02/2016.
 */
public class ActivityModel
{

    private static ActivityModel activity = null;
    private static Connection con;

    public static ActivityModel getInstance()
    {
        if(activity == null)
        {
            activity = new ActivityModel();
        }
        return activity;
    }

    private ActivityModel()
    {
        DatabaseConnector db = DatabaseConnector.getInstance();
        try
        {
            con = db.getConnection();
            con.createStatement();

        } catch (SQLException e)

        {
            e.printStackTrace();

        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public List<Activities> getActivities()
    {

        ObservableList<Activities> data = FXCollections.observableArrayList();
        try
        {
            String sql = "SELECT * FROM activities";
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int minAge = rs.getInt("minAge");
                Time startTime = rs.getTime("startTime");
                Time endTime = rs.getTime("endTime");
                String description = rs.getString("description");

                data.add(new Activities(id,name,minAge,startTime,endTime,description));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public static void addActivity(String name, int age, String startTime, String endTIme, String description)
    {
        try
        {
            String Insert = "INSERT INTO activities (name, minAge, startTime, endTime,description) VALUES ('" + name + "',  " + age + ",'" + startTime + "', '" + endTIme + "', '" + description +"')";

            PreparedStatement pst = con.prepareStatement(Insert);

            pst.executeUpdate();

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void selectActivity(Activities activities)
    {
        int id = activities.getId();
        ResultSet rs;
        Statement stmt;

        try
        {
            String insert = "SELECT * FROM activities WHERE id = '" + id + "'";
            stmt = con.createStatement();
            stmt.executeQuery("SELECT * FROM activities");
            rs = stmt.getResultSet();

            //if (rs.next());

        } catch (SQLException sql)
        {
            sql.printStackTrace();
        }
    }

    public static void deleteActivity(int id)
    {

        try
        {
            String remove = "DELETE FROM activities WHERE id = " + id;

            PreparedStatement pst = con.prepareStatement(remove);
            pst.executeUpdate();

            System.out.println("Booking Deleted"); //Test

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

    }


    public static void editActivity(Activities activities)
    {

        int id = activities.getId();
        String name = activities.getName();
        int minAge = activities.getMinAge();
        Time startTime = activities.getStartTime();
        Time endTime = activities.getEndTime();
        String description = activities.getDescription();

        System.out.println(name + " " +  minAge + " " + startTime + " " + endTime + " " + description + " " + id);

        String insert = "UPDATE activities SET name = '" + name +
                "', minAge = '" + minAge +
                "', startTime = '" + startTime +
                "', endTime = '" + endTime +
                "', description = '" + description +
                "' WHERE id = " + id;

        //String insert = "UPDATE activities SET name = 'Sut' WHERE id = 2";

        System.out.println(insert);


        try
        {
            Statement statement = con.createStatement();
            statement.executeUpdate(insert);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

}
