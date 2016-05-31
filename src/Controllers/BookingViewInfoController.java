package Controllers;

import Models.Activities;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;

/**
 * Created by B on 12-5-2016.
 */
public class BookingViewInfoController
{
    public void fillFields(Activities activities, Text name, Text minAge, Text startTime, Text endTime, TextArea description)
    {
        name.setText(activities.getName());
        minAge.setText(String.valueOf(activities.getMinAge()));
        startTime.setText(String.valueOf(activities.getStartTime()));
        endTime.setText(String.valueOf(activities.getEndTime()));
        description.setText(activities.getDescription());
    }
}
