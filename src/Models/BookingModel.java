package Models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by maxjensendk on 15/05/2016.
 */
public class BookingModel {

    private Connection con;
    private static BookingModel bookingModel = null;

    public static BookingModel getInstance()
    {
        if(bookingModel == null)
        {
            bookingModel = new BookingModel();
        }
        return bookingModel;
    }

    private BookingModel()
    {
        try
        {
            DatabaseConnector dbConnector = DatabaseConnector.getInstance();
            con = dbConnector.getConnection();
        }
        catch (ClassNotFoundException eCNF)
        {
            eCNF.printStackTrace();
        }
        catch (SQLException eSQL)
        {
            eSQL.printStackTrace();
        }
    }

    public List<Booking> getBookings()
    {
        List<Booking> listReturn = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;

        try
        {

            statement = con.createStatement();
            statement.executeQuery("SELECT b.*, usr.firstName, usr.lastName FROM booking b " +
                    "INNER JOIN users usr ON b.ID = usr.ID"
            );
            rs = statement.getResultSet();
            while(rs.next()){
                int ID = rs.getInt("ID");
                String status = rs.getString("status");
                String description = rs.getString("description");
                String userName = rs.getString("firstName");
                userName += " " + rs.getString("lastName");

                Date date = rs.getDate("Date");

                listReturn.add(new Booking(ID,
                        status,
                        description,
                        userName,
                        date
                ));
            }
        }
        catch(SQLException eSQL)
        {
            System.out.println(eSQL);
            System.out.println("Error is from here ");
        }

        return listReturn;

    }

    public void deleteBooking(int id)
    {
        try{
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM booking WHERE ID = ('"+id+"')");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void updateBooking(Booking booking)
    {
        try {
            int aId = booking.getActivityID();
            int userId = booking.getUserID();
            Date date = booking.getDate();
            Time startTime = booking.getStartTime();
            Time endTime = booking.getEndTime();
            int participants = booking.getParticipants();
            int id = booking.getID();
            String query =
                    "UPDATE `booking` " +
                            "SET " +
                            "`ID`=?," +
                            "`date`=?," +
                            "`start`=?," +
                            "`end`=?," +
                            "WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,aId);
            stmt.setInt(2,userId);
            stmt.setDate(3,date);
            stmt.setTime(4,startTime);
            stmt.setTime(5,endTime);
            stmt.setInt(6,participants);
            stmt.setInt(7,id);

            System.out.println(aId + " " + userId);
            stmt.execute();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void insertNewBooking(Booking bookInsert)
    {
        try
        {
            int aId = bookInsert.getActivityID();
            int userId = bookInsert.getUserID();
            Date date = bookInsert.getDate();
            Time startTime = bookInsert.getStartTime();
            Time endTime = bookInsert.getEndTime();
            int participants = bookInsert.getParticipants();
            String query =
                    "INSERT INTO `booking`(`userId`, `date`, `start`, `end`) " +
                            "VALUES (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,userId);
            stmt.setDate(2,date);
            stmt.setTime(3,startTime);
            stmt.setTime(4,endTime);
            stmt.execute();
            System.out.println("inserted booking into table");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
