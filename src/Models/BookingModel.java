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


            /*************************** OLD code - does not work as expected
            statement.executeQuery("SELECT *, b.*, bD.*, ac.name, usr.firstName, usr.lastName FROM bookings b " +
                                   "INNER JOIN activities ac ON b.ID = ac.id " +
                                   "JOIN users usr ON usr.ID " +
                                   "INNER JOIN bookingsdate bD ON bD.bookingDate " +
                                   "JOIN bookinghours"
            );
            */


        /*
           // TODO: Remove duplicates in result - look in the GUI when clicking on bookings
            statement.executeQuery("SELECT *, b.*, bD.*, ac.name, usr.firstName, usr.lastName FROM bookings b " +
                    "INNER JOIN activities ac ON b.ID = ac.id " +
                    "JOIN users usr ON usr.ID " +
                    "INNER JOIN bookingsdate bD ON bD.bookingDate " +
                    "JOIN bookinghours ORDER BY bookingDate"
            );

            */

            statement = con.createStatement();
            statement.executeQuery("SELECT b.*, ac.name, usr.firstName, usr.lastName FROM bookings b " +
                    "INNER JOIN activities ac ON b.activityId = ac.id " +
                    "JOIN users usr ON userId = usr.ID"
            );
            rs = statement.getResultSet();
            while(rs.next()){
                int ID = rs.getInt("ID");
                int aID = rs.getInt("activityId");
                String activityName = rs.getString("name");
                String userName = rs.getString("firstName");
                userName += " " + rs.getString("lastName");
                int userId = rs.getInt("userId");
                int participants = rs.getInt("participants");
                Date date = rs.getDate("Date");
                Time startTime = rs.getTime("start");
                Time endTime = rs.getTime("end");
                listReturn.add(new Booking(ID,
                        aID,
                        userId,
                        activityName,
                        userName,
                        participants,
                        date,
                        startTime,
                        endTime
                ));
            }
        }
        catch(SQLException eSQL)
        {
            System.out.println(eSQL);
        }

        return listReturn;

    }

    public void deleteBooking(int id)
    {
        try{
            Statement st = con.createStatement();
            st.executeUpdate("DELETE FROM bookings WHERE ID = ('"+id+"')");
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
                    "UPDATE `bookings` " +
                            "SET " +
                            "`activityId`=?," +
                            "`userId`=?," +
                            "`date`=?," +
                            "`start`=?," +
                            "`end`=?," +
                            "`participants`=? " +
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
                    "INSERT INTO `bookings`( `activityId`, `userId`, `date`, `start`, `end`, `participants`) " +
                    "VALUES (?,?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,aId);
            stmt.setInt(2,userId);
            stmt.setDate(3,date);
            stmt.setTime(4,startTime);
            stmt.setTime(5,endTime);
            stmt.setInt(6,participants);
            stmt.execute();
            System.out.println("inserted booking into table");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
