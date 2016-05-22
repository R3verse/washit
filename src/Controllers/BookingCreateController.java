package Controllers;

import Models.Booking;
import Models.BookingModel;
import Models.User;
import Models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by B on 25-02-2016.
 */
public class BookingCreateController
{
    public static void search(ComboBox<User> comboUsers, TextField tf)
    {
        List<User> arrUser = UserModel.getInstance().getUserList();
        List<User> arrUseSearch = new ArrayList<>();
        String name = tf.getText() ;

        for (User u: arrUser)
        {
            String fullName = u.getFirstName() + " " + u.getLastName();
            if (name.length() != 0)
            {
                if (fullName.toLowerCase().contains(name.toLowerCase()))
                {
                    arrUseSearch.add(u);
                }
            }
            else
            {
                arrUseSearch.add(u);
            }
        }
        ObservableList<User> userList = FXCollections.observableArrayList(arrUseSearch);
        comboUsers.setItems(userList);
    }

    public static void tryInsert(Booking booking)
    {
        BookingModel.getInstance().insertNewBooking(booking);
    }
    public static void setBookingAlert(String titleText, String headerText)
    {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titleText);
        alert.setContentText("Please fill out all the fields...");
        alert.setHeaderText(headerText);
        alert.show();
    }
}
