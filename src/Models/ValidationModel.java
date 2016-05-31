package Models;

import Controllers.UserCreateController;
import Controllers.UserLoginController;

import javafx.scene.control.Alert;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by maxjensendk on 19/05/2016.
 */
public class ValidationModel {

    public static void loginValidation(String email, String password)
    {
        try {

            UserCreateController userCreateController = new UserCreateController();

            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "SELECT * FROM users WHERE eMail = ? and password = ?"; // search for user

                preparedStatement = DatabaseConnector.getInstance().getConnection().prepareStatement(query);
                preparedStatement.setString(1, email); // replace this in query
                preparedStatement.setString(2, password);

                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                {
                    if(new UserLoginController().doLogin(email,password));
                    {
                        // Login to next scene
                    }
                }else{
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("ERROR");
                    alert.setHeaderText("Invalid username/password combination!");
                    alert.setContentText("Please enter a valid username/password\nRemember the system is case sensitive.");
                    alert.showAndWait();

                }
            } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void validateBooking()
    {

        /*
        * TODO: Not required by customer
        *    - Brugernavn
        *    - dato
        *    - personer
        *    - tidsrum
        *
         */

    }

    public static void validateSearching()
    {

        /*
        * TODO: Not required by customer
        *       - Booking
        *       - Username
        *       - Date
         */

    }

    public static void validateCreateUser()
    {
        /*
        * TODO: Not required by customer, but already implemented and needs refactored
        *
        * 1) Firstname, Lastname, Email, Address, PhoneNo, Bday
        *
         */

    }

    public static void validateActivities()
    {
        /*
        * TODO:
        *
        *  1) ActivityName, minAge, startTime, endTime
        *
         */
    }

}
