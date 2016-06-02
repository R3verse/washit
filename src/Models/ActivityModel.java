package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.List;

/**
 * Created by x on 12/5/2016.
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
            String sql = "SELECT * FROM booking";
            PreparedStatement statement = con.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next())
            {
                // Status,description,date,user,address,time
                int id = rs.getInt("ID");
                String status = rs.getString("description");
                String desription = rs.getString("description");
                Date date = rs.getDate("date");
                String user = rs.getString("firstName");
                user += rs.getString("lastName");
                String address = rs.getString("address");
                Time time = rs.getTime("time");

                data.add(new Activities(id,status,desription,date,user,address,time));
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return data;
    }

    public static void addActivity(String status, String description, String date, String user, String address, String time)
    {
        try
        {
            // Status,description,date,user,address,time
            String Insert = "INSERT INTO booking (status, description, date, user, address, time ) VALUES ('" + status + "',  " + description + ",'" + date + "', '" + user + "', '" + address + "', '" + time +"')";

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
        int id = activities.getID();
        ResultSet rs;
        Statement stmt;

        try
        {
            String insert = "SELECT * FROM booking WHERE id = '" + id + "'";
            stmt = con.createStatement();
            stmt.executeQuery("SELECT * FROM booking");
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
            String remove = "DELETE FROM booking WHERE id = " + id;

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

        int userId = activities.getID();
        String status = activities.getStatus();
        String description = activities.getDescription();
        Date date = activities.getDate();
        String user = activities.getFirstName();
        user += activities.getLastName();
        Time time = activities.getTime();


        System.out.println(status + " " +  description + " " + date + " " + user + " " + time);

        // Status,description,date,user,address,time
        String insert = "UPDATE activities SET status = '" + status +
                "', description = '" + description +
                "', date = '" + date +
                "', user = '" + user +
                "', time = '" + time +
                "' WHERE id = " + userId;

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
