package View;

import Controllers.BookingViewInfoController;
import Models.Activities;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by x on 12/05/2016.
 * This class is only intends to show info's about the activity the user has picked from booking.
 */
public class BookingInfoView
{
    private BookingViewInfoController bookingViewInfoController = new BookingViewInfoController();
    public static BookingInfoView infoView = null;

    public static BookingInfoView getInstance()
    {
        if(infoView == null)
        {
            infoView = new BookingInfoView();
        }
        return infoView;
    }

    public void infoStage(Activities activities)
    {
        Stage stage = new Stage();
        stage.setScene(new Scene(info(activities), 600, 400));
        stage.show();
    }

    public GridPane info(Activities activities)
    {
        GridPane gridPane = new GridPane();
        //Name
        Label nameLabel = new Label("Activity Name: ");
        Text activityName = new Text();
        //Age
        Label ageLabel = new Label("Min. Age: ");
        Text activityAge = new Text();
        //Start Time
        Label startTimeLabel = new Label("Start Time: ");
        Text startTime = new Text();
        //Stop Time
        Label stopTimeLabel = new Label("Stop Time: ");
        Text stopTime = new Text();
        //Description
        Label descriptionLabel = new Label("Description: ");
        TextArea descriptionArea = new TextArea();

        //Insert values into textfields
        bookingViewInfoController.fillFields(activities,activityName,activityAge,startTime,stopTime,descriptionArea);

        ////Disabling the area, so you are not able to edit it a a user.
        descriptionArea.setStyle("-fx-background-color: black");
        descriptionArea.setStyle("-fx-font-size: 15");
        descriptionArea.setPrefSize(500, 100);
        descriptionArea.setEditable(false);


        //Grid Customization
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: linear-gradient(white 80%, #dda200 100%)");

        //Setting up the GridPane, so it looks nice n' shiny
        gridPane.add(nameLabel, 0, 0);
        gridPane.add(activityName, 1, 0);
        gridPane.add(ageLabel, 0, 1);
        gridPane.add(activityAge, 1, 1);
        gridPane.add(startTimeLabel, 0, 2);
        gridPane.add(startTime, 1, 2);
        gridPane.add(stopTimeLabel, 0, 3);
        gridPane.add(stopTime, 1, 3);
        gridPane.add(descriptionLabel, 0, 4);
        gridPane.add(descriptionArea, 0, 5, 2, 1);

        return gridPane;
    }
}
