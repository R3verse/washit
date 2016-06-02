package Controllers;

import Models.Activities;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Created by B on 12-5-2016.
 */
public class BookingViewInfoController
{
    public void fillFields(Activities activities, Text status, TextArea description, Text date, Text user, TextArea address, Text time)
    {

        // Status,description,date,user,address,time
        status.setText(activities.getStatus());
        description.setText(String.valueOf(activities.getDescription()));
        date.setText(String.valueOf(activities.getDate()));
        user.setText(activities.getUser());
        address.setText(activities.getAddress());
        time.setText(String.valueOf(activities.getTime()));
    }
}
