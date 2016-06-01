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

            int userId = booking.getUserID();
            String status = booking.getStatus();
            String description = booking.getDescription();
            Date date = booking.getDate();
            String address = booking.getAddress();
            int id = booking.getID();
            String query =
                    "UPDATE `booking` " +
                            "SET " +
                            "`ID`=?," +
                            "`status`=?," +
                            "`description`=?," +
                            "`Date`=?," +
                            "`address`=?," +
                            "WHERE id = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,userId);
            stmt.setString(2,status);
            stmt.setString(3,description);
            stmt.setDate(4,date);
            stmt.setString(5,address);

            System.out.println(id + " " + userId);
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
            int userId = bookInsert.getUserID();
            String status = bookInsert.getStatus();
            String description = bookInsert.getDescription();
            Date date = bookInsert.getDate();
            String address = bookInsert.getAddress();
            String query =
                    "INSERT INTO `booking`(`ID`, `status`, `description`, `Date`, `address`) " +
                            "VALUES (?,?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1,userId);
            stmt.setString(2,status);
            stmt.setString(3,description);
            stmt.setDate(4,date);
            stmt.setString(5,address);
            stmt.execute();
            System.out.println("inserted booking into table");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
